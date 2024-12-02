package com.example.sktestpost.application.facade;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.sktestpost.common.config.security.userdetails.CustomUserDetailsService;
import com.example.sktestpost.domain.Member;
import com.example.sktestpost.domain.Post;
import com.example.sktestpost.infra.adapter.out.jpa.MemberJpaRepository;
import com.example.sktestpost.infra.adapter.out.jpa.PostJpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("post lock facade test")
class PostLockFacadeTest {

	@Autowired
	private PostLockFacade postLockFacade;
	@Autowired
	private PostFacade postFacade;
	@Autowired
	private MemberJpaRepository memberJpaRepository;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private PostJpaRepository postJpaRepository;

	@BeforeEach
	void setUpSecurityContext() {
		Member member = Member.builder()
			.accountId("test")
			.accountPwd("testPWD")
			.name("테스트")
			.role("ROLE_USER")
			.build();
		memberJpaRepository.saveAndFlush(member);

		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		UserDetails userDetails = customUserDetailsService.loadUserByUsername("test");
		Authentication authentication = new UsernamePasswordAuthenticationToken(
			"test", // Principal
			null, // Credentials
			List.of(new SimpleGrantedAuthority("ROLE_USER")) // Authorities
		);
		securityContext.setAuthentication(authentication);
		SecurityContextHolder.setContext(securityContext);
	}

	@Test
	@DisplayName("게시글 조회수 redis lock 적용 테스트")
	void getPostWithLock() throws InterruptedException {
		// given
		Post post = Post.builder()
			.title("test")
			.content("test")
			.member(memberJpaRepository.findById(1L).get())
			.build();
		Post result = postJpaRepository.saveAndFlush(post);

		Long postId = result.getId();

		final int threadCount = 100;
		final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					postLockFacade.getPost(postId);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();

		// then
		Post getPost = postJpaRepository.findById(postId).get();
		System.out.println(getPost.getViewCount());
		assertThat(getPost.getViewCount()).isEqualTo(threadCount);
	}

	@Test
	@DisplayName("게시글 조회수 redis lock 미적용 테스트")
	void getPostWithoutLock() throws InterruptedException {
		// given
		Post post = Post.builder()
			.title("test")
			.content("test")
			.member(memberJpaRepository.findById(1L).get())
			.build();
		Post result = postJpaRepository.saveAndFlush(post);

		Long postId = 1L;

		final int threadCount = 100;
		final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					postFacade.getPost(postId);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();

		// then
		Post getPost = postJpaRepository.findById(postId).get();
		assertThat(getPost.getViewCount()).isNotEqualTo(threadCount);
	}

}
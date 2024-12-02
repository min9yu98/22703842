package com.example.sktestpost.application.facade;

import static com.example.sktestpost.common.constants.Constants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.sktestpost.common.config.security.userdetails.CustomUserDetailsService;
import com.example.sktestpost.common.dto.request.CreatePostReqDTO;
import com.example.sktestpost.common.dto.request.UpdatePostReqDTO;
import com.example.sktestpost.common.dto.response.CreatePostResDTO;
import com.example.sktestpost.common.dto.response.GetPostListResDTO;
import com.example.sktestpost.common.dto.response.GetPostResDTO;
import com.example.sktestpost.common.dto.response.GetSearchPostListResDTO;
import com.example.sktestpost.common.dto.response.UpdatePostResDTO;
import com.example.sktestpost.domain.Member;
import com.example.sktestpost.infra.adapter.out.jpa.MemberJpaRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("post facade test")
class PostFacadeTest {

	@Autowired
	private PostFacade postFacade;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private MemberJpaRepository memberJpaRepository;

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
	@DisplayName("포스트 생성 성공 테스트")
	void createPostTest() {
		// given
		String title = "테스트 제목";
		String content = "테스트 내용";
		CreatePostReqDTO createPostReqDTO = new CreatePostReqDTO();
		createPostReqDTO.setTitle(title);
		createPostReqDTO.setContent(content);

		// when
		CreatePostResDTO result = postFacade.createPost(createPostReqDTO);

		// then
		assertThat(result.getTitle()).isEqualTo(title);
		assertThat(result.getContent()).isEqualTo(content);
	}

	@Test
	@DisplayName("포스트 생성 후 수정 성공 테스트")
	void updatePostTest() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();
		String title = "수정된 제목";
		String content = "수정된 내용";
		UpdatePostReqDTO updatePostReqDTO = new UpdatePostReqDTO();
		updatePostReqDTO.setTitle(title);
		updatePostReqDTO.setContent(content);
		updatePostReqDTO.setPostId(postId);

		// when
		UpdatePostResDTO result = postFacade.updatePost(updatePostReqDTO);

		// then
		assertThat(result.getTitle()).isNotEqualTo(createdPost.getTitle());
		assertThat(result.getContent()).isNotEqualTo(createdPost.getContent());
	}

	@Test
	@DisplayName("포스트 생성 후 삭제 성공 테스트")
	void deletePost() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();
		String writerAccountId = createdPost.getWriterAccountId();

		// when
		postFacade.deletePost(postId);

		// then
		assertThatException().isThrownBy(() -> postFacade.getPost(postId));
	}

	@Test
	@DisplayName("포스트 생성 후 상세 조회 성공 테스트")
	void getPost() {
		// given
		CreatePostResDTO createdPost = createPost();
		Long postId = createdPost.getPostId();

		// when
		GetPostResDTO result1 = postFacade.getPost(postId);
		GetPostResDTO result2 = postFacade.getPost(postId);
		GetPostResDTO result3 = postFacade.getPost(postId);
		GetPostResDTO result = postFacade.getPost(postId);

		// then
		assertThat(result.getPostId()).isEqualTo(postId);
		assertThat(result.getTitle()).isEqualTo(createdPost.getTitle());
		assertThat(result.getContent()).isEqualTo(createdPost.getContent());
		assertThat(result.getCreatedAt()).isEqualTo(createdPost.getCreateAt());
		assertThat(result.getViewCount()).isEqualTo(4);
	}

	@Test
	@DisplayName("포스트 생성 후 목록 조회")
	void getPostList() {
		// given
		CreatePostResDTO createdPost = createPost();

		// when
		Pageable pageable = generatePageable(0);
		GetPostListResDTO result = postFacade.getPostList(pageable);

		// then
		assertThat(result.getPostList().size()).isEqualTo(1);
	}

	@Test
	@DisplayName("포스트 생성 후 검색 조회")
	void getSearchPostList() {
		// given
		CreatePostResDTO createdPost = createPost();
		String keyword = "테";
		String keyword2 = "케";

		// when
		Pageable pageable = generatePageable(0);
		GetSearchPostListResDTO result = postFacade.getSearchPostList(pageable, keyword);
		GetSearchPostListResDTO result2 = postFacade.getSearchPostList(pageable, keyword2);

		// then
		assertThat(result.getPostList().size()).isEqualTo(1);
		assertThat(result2.getPostList().size()).isEqualTo(0);
	}

	private CreatePostResDTO createPost() {
		String title = "테스트 제목";
		String content = "테스트 내용";
		String writerId = "test";
		CreatePostReqDTO createPostReqDTO = new CreatePostReqDTO();
		createPostReqDTO.setTitle(title);
		createPostReqDTO.setContent(content);

		return postFacade.createPost(createPostReqDTO);
	}

	private Pageable generatePageable(int page) {
		return PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, PAGE_SORT_CRITERIA));
	}

}
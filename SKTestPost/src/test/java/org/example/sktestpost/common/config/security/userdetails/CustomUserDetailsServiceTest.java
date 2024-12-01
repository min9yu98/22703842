package org.example.sktestpost.common.config.security.userdetails;

import org.assertj.core.api.Assertions;
import org.example.sktestpost.domain.Member;
import org.example.sktestpost.infra.adapter.out.jpa.MemberJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("userdetails test")
class CustomUserDetailsServiceTest {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private MemberJpaRepository memberJpaRepository;

	@Test
	@DisplayName("UserDetailsService loadUserByUsername 성공 테스트")
	void loadUserByUsername() {
		Member member = Member.builder()
			.accountId("test")
			.accountPwd("testPWD")
			.name("테스트")
			.role("ROLE_USER")
			.build();
		memberJpaRepository.saveAndFlush(member);

		String memberIdInUserDetails = customUserDetailsService.loadUserByUsername("test").getUsername();
		Assertions.assertThat(memberIdInUserDetails).isEqualTo(member.getAccountId());
	}

}
package com.example.sktestpost.common.config.security.userdetails;

import com.example.sktestpost.common.response.error.ErrorCode;
import com.example.sktestpost.common.response.exception.NotFoundException;
import com.example.sktestpost.domain.Member;
import com.example.sktestpost.infra.adapter.out.jpa.MemberJpaRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberJpaRepository memberJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberJpaRepository.findByAccountId(username)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다.", ErrorCode.NOT_FOUND));
		return new CustomUserDetails(member);
	}
}

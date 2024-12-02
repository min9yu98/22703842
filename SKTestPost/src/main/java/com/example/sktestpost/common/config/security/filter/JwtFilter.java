package com.example.sktestpost.common.config.security.filter;

import static com.example.sktestpost.common.constants.JwtConstants.*;
import static jakarta.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.sktestpost.common.config.security.userdetails.CustomUserDetails;
import com.example.sktestpost.common.entity.Refresh;
import com.example.sktestpost.common.response.error.ErrorCode;
import com.example.sktestpost.common.response.exception.IllegalAccessException;
import com.example.sktestpost.common.response.exception.LogoutUserException;
import com.example.sktestpost.common.utils.JwtUtils;
import com.example.sktestpost.domain.Member;
import com.example.sktestpost.infra.adapter.out.jpa.RefreshJpaRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final RefreshJpaRepository refreshJpaRepository;

	public JwtFilter(JwtUtils jwtUtils, RefreshJpaRepository refreshJpaRepository) {
		this.jwtUtils = jwtUtils;
		this.refreshJpaRepository = refreshJpaRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = "";
		if (request.getHeader(AUTHORIZATION) != null) {
			accessToken = request.getHeader(AUTHORIZATION).substring(7);
		} else {
			log.info("Authorization is null or does not start with Bearer");
			filterChain.doFilter(request, response);
			return;
		}

		isAccessToken(response, accessToken);
		isExpiredAccessToken(response, accessToken);

		String memberAccountId = jwtUtils.getMemberAccountId(accessToken);
		String role = jwtUtils.getRole(accessToken);

		isLogoutMember(memberAccountId);

		Member member = Member.builder()
			.accountId(memberAccountId)
			.role(role)
			.build();
		CustomUserDetails customUserDetails = new CustomUserDetails(member);

		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
			customUserDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);
	}

	private void isAccessToken(HttpServletResponse response, String accessToken) throws IOException {
		String tokenType = jwtUtils.getTokenType(accessToken);
		if (!ACCESS_TOKEN.getContent().equals(tokenType)) {
			PrintWriter writer = response.getWriter();
			writer.print("Invalid Access Token");
			response.setStatus(SC_UNAUTHORIZED);
			throw new IllegalAccessException("잘못된 타입의 토큰 입니다.", ErrorCode.IllegalAccess);
		}
	}

	private void isExpiredAccessToken(HttpServletResponse response, String accessToken) throws IOException {
		if (jwtUtils.isExpired(accessToken)) {
			PrintWriter writer = response.getWriter();
			writer.print("Access token expired");
			response.setStatus(SC_UNAUTHORIZED);
			throw new IllegalAccessException("토큰이 만료되었습니다.", ErrorCode.IllegalAccess);
		}
	}

	private void isLogoutMember(String memberAccountId) {
		log.info("logout check");
		Refresh refresh = refreshJpaRepository.findByMemberAccountId(memberAccountId)
			.orElseThrow(() -> new LogoutUserException("Logout User", ErrorCode.LOGOUT_USER));
	}
}

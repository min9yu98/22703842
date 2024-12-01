package org.example.sktestpost.common.config.security.filter;

import static org.example.sktestpost.common.constants.JwtConstants.*;

import java.io.IOException;

import org.example.sktestpost.common.config.security.userdetails.CustomUserDetails;
import org.example.sktestpost.common.utils.JwtUtils;
import org.example.sktestpost.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;

	public JwtFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		if (authorization == null || !authorization.startsWith(BEARER.getContent())) {
			log.info("Authorization is null or does not start with Bearer");
			filterChain.doFilter(request, response);
			return;
		}

		String token = jwtUtils.parsingAccessToken(authorization);
		if (jwtUtils.isExpired(token)) {
			log.info("Token is expired");
			filterChain.doFilter(request, response);
			return;
		}

		String memberAccountId = jwtUtils.getMemberAccountId(token);
		String role = jwtUtils.getRole(token);

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
}

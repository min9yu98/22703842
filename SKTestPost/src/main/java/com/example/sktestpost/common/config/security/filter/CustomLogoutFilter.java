package com.example.sktestpost.common.config.security.filter;

import static com.example.sktestpost.common.constants.JwtConstants.*;
import static jakarta.servlet.http.HttpServletResponse.*;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import com.example.sktestpost.common.entity.Refresh;
import com.example.sktestpost.common.response.error.ErrorCode;
import com.example.sktestpost.common.response.exception.NotFoundException;
import com.example.sktestpost.common.utils.JwtUtils;
import com.example.sktestpost.infra.adapter.out.jpa.RefreshJpaRepository;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

	private final JwtUtils jwtUtils;
	private final RefreshJpaRepository refreshJpaRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws
		IOException,
		ServletException {
		doFilter((HttpServletRequest)request, (HttpServletResponse)response, filterChain);
	}

	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
		IOException,
		ServletException {
		String requestUri = request.getRequestURI();
		if (!requestUri.matches("/members/logout")) {
			filterChain.doFilter(request, response);
			return;
		}

		String requestMethod = request.getMethod();
		if (!requestMethod.equals("POST")) {
			filterChain.doFilter(request, response);
			return;
		}

		String refresh = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(REFRESH_TOKEN.getContent())) {
					refresh = cookie.getValue();
				}
			}
		}

		if (request.getHeader("Authorization") == null) {
			throw new NotFoundException("Token Not Found", ErrorCode.NOT_FOUND);
		}
		String accessToken = jwtUtils.parsingAccessToken(request.getHeader("Authorization"));
		String tokenAccountId = jwtUtils.getMemberAccountId(accessToken);
		Refresh refreshEntity = refreshJpaRepository.findByMemberAccountId(tokenAccountId)
			.orElseThrow(() -> new NotFoundException("Token Not Found", ErrorCode.NOT_FOUND));
		if (refresh == null) {
			refresh = refreshEntity.getRefreshToken();
		}

		try {
			jwtUtils.isExpired(refresh);
		} catch (ExpiredJwtException e) {
			response.setStatus(SC_BAD_REQUEST);
			return;
		}

		String category = jwtUtils.getTokenType(refresh);
		if (!category.equals(REFRESH_TOKEN.getContent())) {
			response.setStatus(SC_BAD_REQUEST);
			return;
		}

		Boolean isExistRefresh = refreshJpaRepository.existsByRefreshToken(refresh);
		if (!isExistRefresh) {
			response.setStatus(SC_BAD_REQUEST);
			return;
		}

		refreshJpaRepository.delete(refreshEntity);

		Cookie cookie = new Cookie(REFRESH_TOKEN.getContent(), null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		response.setStatus(HttpServletResponse.SC_OK);
		log.info("logout success");
	}

}
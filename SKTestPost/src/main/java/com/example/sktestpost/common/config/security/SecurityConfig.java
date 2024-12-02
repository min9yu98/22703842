package com.example.sktestpost.common.config.security;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.sktestpost.common.config.security.filter.JwtFilter;
import com.example.sktestpost.common.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtils jwtUtils;
	private static final String[] AUTH_PERMITTED_LIST = {
		"/swagger-ui/**", "swagger-ui.html/**", "/v3/**", "/members/join", "/members/login"
	};

	@Autowired
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtils jwtUtils) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtils = jwtUtils;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(auth -> auth.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration configuration = new CorsConfiguration();
					configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
					configuration.setAllowedMethods(Collections.singletonList("*"));
					configuration.setAllowCredentials(true);
					configuration.setAllowedHeaders(Collections.singletonList("*"));
					configuration.setMaxAge(3600L);
					configuration.setExposedHeaders(List.of("Authorization", "access-token", "refresh-token"));
					return configuration;
				}
			}))
			.csrf(auth -> auth.disable())
			.formLogin(auth -> auth.disable())
			.httpBasic(auth -> auth.disable())
			.headers(auth -> auth.frameOptions().disable())
			.authorizeHttpRequests(auth -> auth
					.anyRequest().permitAll()
				// .requestMatchers(AUTH_PERMITTED_LIST)
				// .permitAll()
				// .requestMatchers(HttpMethod.GET)
				// .permitAll()
				// .anyRequest()
				// .authenticated()
			)
			.addFilterBefore(new JwtFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	// 비밀번호 암호화 (단방향)
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

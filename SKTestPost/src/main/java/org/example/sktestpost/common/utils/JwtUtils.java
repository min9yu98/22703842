package org.example.sktestpost.common.utils;

import static org.example.sktestpost.common.constants.JwtConstants.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {

	@Value("${spring.jwt.access-token-expiration}")
	private Long accessTokenExpTime;

	@Value("${spring.jwt.refresh-token-expiration}")
	private Long refreshTokenExpTime;

	private SecretKey secretKey;

	public JwtUtils(@Value("${spring.jwt.secret}") String secretKey) {
		this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
	}

	public String generateAccessToken(String accountId, String role) {
		return BEARER + Jwts.builder()
			.claim("accountId", accountId)
			.claim("role", role)
			.claim("tokenType", ACCESS_TOKEN.getContent())
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + accessTokenExpTime))
			.signWith(secretKey)
			.compact();
	}

	public String parsingAccessToken(String token) {
		return token.substring(7);
	}

	public String generateRefreshToken(String accountId, String role) {
		return BEARER + Jwts.builder()
			.claim("accountId", accountId)
			.claim("role", role)
			.claim("tokenType", REFRESH_TOKEN.getContent())
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + refreshTokenExpTime))
			.signWith(secretKey)
			.compact();
	}

	public boolean isExpired(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.getExpiration()
			.before(new Date());
	}

	public String getMemberAccountId(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("accountId", String.class);
	}

	public String getRole(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get("role", String.class);
	}

}

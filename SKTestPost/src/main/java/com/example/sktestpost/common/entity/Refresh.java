package com.example.sktestpost.common.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Getter;

@RedisHash(value = "refreshToken", timeToLive = 86400000L)
@Getter
public class Refresh {

	@Id
	private final String memberAccountId;

	@Indexed
	private final String refreshToken;

	private final String expiration;

	@Builder
	public Refresh(String memberAccountId, String refreshToken, String expiration) {
		this.memberAccountId = memberAccountId;
		this.refreshToken = refreshToken;
		this.expiration = expiration;
	}
}

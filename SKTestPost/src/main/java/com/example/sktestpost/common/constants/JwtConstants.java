package com.example.sktestpost.common.constants;

import lombok.Getter;

@Getter
public enum JwtConstants {

	ACCESS_TOKEN("access-token"),
	REFRESH_TOKEN("refresh-token"),
	BEARER("Bearer "),
	ROLE_USER("ROLE_USER"),
	;

	private String content;

	JwtConstants(String content) {
		this.content = content;
	}
}

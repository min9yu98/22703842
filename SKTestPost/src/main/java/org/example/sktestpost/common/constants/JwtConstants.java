package org.example.sktestpost.common.constants;

import lombok.Getter;

@Getter
public enum JwtConstants {

	ACCESS_TOKEN("access"),
	REFRESH_TOKEN("refresh"),
	BEARER("Bearer "),
	;

	private String content;

	JwtConstants(String content) {
		this.content = content;
	}
}

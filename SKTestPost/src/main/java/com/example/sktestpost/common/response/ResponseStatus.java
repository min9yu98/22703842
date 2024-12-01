package com.example.sktestpost.common.response;

import lombok.Getter;

@Getter
public enum ResponseStatus {

	SUCCESS(1000, "성공"),
	;

	final private int code;
	final private String message;

	private ResponseStatus(final int code, final String message) {
		this.code = code;
		this.message = message;
	}

}

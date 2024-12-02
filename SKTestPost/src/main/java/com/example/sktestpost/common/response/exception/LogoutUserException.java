package com.example.sktestpost.common.response.exception;

import com.example.sktestpost.common.response.error.ErrorCode;

import lombok.Getter;

@Getter
public class LogoutUserException extends RuntimeException {

	private final ErrorCode errorCode;

	public LogoutUserException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

}

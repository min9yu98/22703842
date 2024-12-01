package com.example.sktestpost.common.response.exception;

import com.example.sktestpost.common.response.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IllegalAccessException extends RuntimeException {
	private final ErrorCode errorCode;

	public IllegalAccessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}

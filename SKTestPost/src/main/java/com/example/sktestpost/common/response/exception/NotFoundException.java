package com.example.sktestpost.common.response.exception;

import com.example.sktestpost.common.response.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
	private final ErrorCode errorCode;

	public NotFoundException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}

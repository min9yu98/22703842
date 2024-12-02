package com.example.sktestpost.common.response.exception;

import com.example.sktestpost.common.response.error.ErrorCode;

import lombok.Getter;

@Getter
public class S3Exception extends RuntimeException {
	private final ErrorCode errorCode;

	public S3Exception(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}

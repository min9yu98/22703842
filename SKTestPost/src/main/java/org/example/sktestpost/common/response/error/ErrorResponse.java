package org.example.sktestpost.common.response.error;

import lombok.Getter;

@Getter
public class ErrorResponse extends Error {

	private int status;
	private String message;
	private String code;

	public ErrorResponse(final ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.message = errorCode.getMessage();
		this.code = errorCode.getErrorCode();
	}

}

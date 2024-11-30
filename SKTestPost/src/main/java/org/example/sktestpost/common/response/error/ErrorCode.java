package org.example.sktestpost.common.response.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	;
	final private int status;
	final private String errorCode;
	final private String message;

	private ErrorCode(int status, String errorCode, String message) {
		this.status = status;
		this.errorCode = errorCode;
		this.message = message;
	}

}

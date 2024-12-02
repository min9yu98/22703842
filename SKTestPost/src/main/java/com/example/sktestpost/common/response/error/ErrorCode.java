package com.example.sktestpost.common.response.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
	IllegalAccess(403, "C001", "접근 권한이 없습니다."),
	NOT_FOUND(404, "C002", "해당 리소스를 찾을 수 없습니다."),
	LOGOUT_USER(403, "C003", "로그아웃된 사용자입니다."),
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

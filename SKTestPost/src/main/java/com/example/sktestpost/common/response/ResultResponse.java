package com.example.sktestpost.common.response;

import static com.example.sktestpost.common.response.ResponseStatus.*;

import lombok.Getter;

@Getter
public class ResultResponse {

	private final int code;
	private final String message;
	private final Object data;

	public ResultResponse(Object data) {
		this.code = SUCCESS.getCode();
		this.message = SUCCESS.getMessage();
		this.data = data;
	}

	public ResultResponse(ResponseStatus status) {
		this.code = status.getCode();
		this.message = status.getMessage();
		this.data = null;
	}
}

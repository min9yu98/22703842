package org.example.sktestpost.common.response;

import static org.example.sktestpost.common.response.ResponseStatus.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
public class ResultResponse<T> {

	private final int code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T data;

	public ResultResponse(T data) {
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

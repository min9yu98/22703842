package com.example.sktestpost.common.response.exception.handler;

import com.example.sktestpost.common.response.error.ErrorResponse;
import com.example.sktestpost.common.response.exception.BaseException;
import com.example.sktestpost.common.response.exception.IllegalAccessException;
import com.example.sktestpost.common.response.exception.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
		log.error("handleBaseException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(IllegalAccessException.class)
	public ResponseEntity<ErrorResponse> handleIllegalAccessException(IllegalAccessException ex) {
		log.error("handleIllegalAccessException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
		log.error("handleNotFoundException", ex);
		final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
		return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
	}

}

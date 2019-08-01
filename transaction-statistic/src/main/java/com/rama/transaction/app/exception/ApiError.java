package com.rama.transaction.app.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiError {
	private HttpStatus status;
	private String message;
	private ApiError() {

	}
	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	public ApiError(HttpStatus status,String message) {
		this();
		this.status = status;
		this.message = message;
	}
}

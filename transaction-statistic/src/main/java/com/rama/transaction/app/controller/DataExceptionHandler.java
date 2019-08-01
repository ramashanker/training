package com.rama.transaction.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rama.transaction.app.exception.ApiError;
import com.rama.transaction.app.exception.FutureDateException;
import com.rama.transaction.app.exception.InvalidTransactionJsonException;
import com.rama.transaction.app.exception.OldTransactionException;

@ControllerAdvice
public class DataExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		if (ex.getCause() instanceof InvalidFormatException) {
			return buildResponseEntity(new ApiError(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid json formate exception"));
		} else
			return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler({ IllegalArgumentException.class, InvalidTransactionJsonException.class,NullPointerException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	void handleIllegalArgumentException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(),"Invalid request body");
	}

	@ExceptionHandler({ FutureDateException.class })
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	void handleFutureDateException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Transaction data is in future data");
	}

	@ExceptionHandler(OldTransactionException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void handleOldTransactionException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NO_CONTENT.value(),"Transaction data is older than 60 seconds");
	}

}

package com.rama.transaction.app.exception;

public class InvalidTransactionJsonException extends RuntimeException {
	/**
	 * Exception process over http
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTransactionJsonException() {

	}

	public InvalidTransactionJsonException(final String message) {
		super(message);
	}

	public InvalidTransactionJsonException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidTransactionJsonException(final Throwable cause) {
		super(cause);
	}
}

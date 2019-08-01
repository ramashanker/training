package com.rama.transaction.app.exception;

public class FutureDateException extends RuntimeException {
	/**
	 * Exception process over http
	 */
	private static final long serialVersionUID = 1L;

	public FutureDateException() {

	}

	public FutureDateException(final String message) {
		super(message);
	}

	public FutureDateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FutureDateException(final Throwable cause) {
		super(cause);
	}
}

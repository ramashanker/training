package com.rama.transaction.app.exception;

public class OldTransactionException extends RuntimeException {
	/**
	 * Exception process over http
	 */
	private static final long serialVersionUID = 1L;

	public OldTransactionException() {

	}

	public OldTransactionException(final String message) {
		super(message);
	}

	public OldTransactionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public OldTransactionException(final Throwable cause) {
		super(cause);
	}
}

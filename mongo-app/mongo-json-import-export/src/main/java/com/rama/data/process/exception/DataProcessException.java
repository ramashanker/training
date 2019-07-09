package com.rama.data.process.exception;

public class DataProcessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataProcessException() {
    }

    public DataProcessException(final String message) {
        super(message);
    }

    public DataProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DataProcessException(final Throwable cause) {
        super(cause);
    }

    protected DataProcessException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
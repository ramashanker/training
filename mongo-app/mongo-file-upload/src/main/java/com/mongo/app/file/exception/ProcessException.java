package com.mongo.app.file.exception;

public class ProcessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProcessException() {
    }

    public ProcessException(final String message) {
        super(message);
    }

    public ProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProcessException(final Throwable cause) {
        super(cause);
    }

    protected ProcessException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.globalmaksimum.bpmn.banking.domain;

public abstract class FinansPocException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3549732749145389676L;

	public FinansPocException() {
		super();
	}

	public FinansPocException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public FinansPocException(String message, Throwable cause) {
		super(message, cause);

	}

	public FinansPocException(String message) {
		super(message);

	}

	public FinansPocException(Throwable cause) {
		super(cause);

	}

}

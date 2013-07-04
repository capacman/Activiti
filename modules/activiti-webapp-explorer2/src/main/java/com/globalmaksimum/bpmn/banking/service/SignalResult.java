package com.globalmaksimum.bpmn.banking.service;

public class SignalResult {
	private boolean success;
	private String message;

	public SignalResult(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public SignalResult() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

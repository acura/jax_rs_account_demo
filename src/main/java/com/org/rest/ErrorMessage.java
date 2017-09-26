package com.org.rest;

public class ErrorMessage {

	private String errorMessage;

	public ErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ErrorMessage [errorMessage=" + errorMessage + "]";
	}
	
	
}

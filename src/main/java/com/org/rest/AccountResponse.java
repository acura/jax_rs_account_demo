package com.org.rest;

public class AccountResponse {

	private String response;
	
	public AccountResponse (String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "AccountResponse [response=" + response + "]";
	}
	
}

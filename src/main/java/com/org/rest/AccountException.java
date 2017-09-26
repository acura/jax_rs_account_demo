package com.org.rest;

import java.io.Serializable;

public class AccountException extends RuntimeException implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public AccountException() {
		super();
	}
	
	public AccountException(String msg)   {
		super(msg);
	}
	
	public AccountException(String msg, Exception e)  {
		super(msg, e);
	}
}

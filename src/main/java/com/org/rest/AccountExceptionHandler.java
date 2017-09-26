package com.org.rest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
public class AccountExceptionHandler implements ExceptionMapper<AccountException> {
	
	private static final Logger logger = Logger.getLogger(AccountExceptionHandler.class);
	
	@Override
	public Response toResponse(AccountException exception) {
		logger.error("Exception ", exception);
		return Response.status(Status.BAD_REQUEST)
					   .entity(new ErrorMessage(exception.getMessage()))
					   .type(MediaType.APPLICATION_JSON)
					   .build(); 
	}
}
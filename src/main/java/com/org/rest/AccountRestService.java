package com.org.rest;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.org.model.AccountWS;
import com.org.patch.PATCH;
import com.org.service.AccountService;

@Component
@Path(value = "/v1/accounts/account")
public class AccountRestService {

	private static final Logger logger = Logger.getLogger(AccountRestService.class);
	public static final String ACCOUNT_PARAM_KEY_ID = "accountId";
	
	@Autowired
	private AccountService accountService;
	
	@GET
	@Path("/{accountId}")
	@Produces(MediaType.APPLICATION_JSON)
	public AccountWS getAccount(@PathParam(ACCOUNT_PARAM_KEY_ID) String accountId) {
		logger.debug(String.format("Reteriving Account for %s", accountId));
		return accountService.getAccount(accountId);

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAccount(AccountWS account) {
		accountService.createAccount(account);
		return Response.status(200)
				   .entity(new AccountResponse("Account created with account id "+ account.getAccountId()))
				   .type(MediaType.APPLICATION_JSON)
				   .build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AccountWS> getAccounts(){
		return accountService.getAccounts();
	}
	
	@Path("/{accountId}")
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON )
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAccount(@PathParam(ACCOUNT_PARAM_KEY_ID) String accountId, AccountWS account) {
		accountService.updateAccount(accountId, account);
		return Response.status(200)
					   .entity(new AccountResponse("Account Updated "+ accountId))
					   .type(MediaType.APPLICATION_JSON)
					   .build();
	}
	
}

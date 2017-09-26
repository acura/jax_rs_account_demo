package com.org.service;

import java.util.List;
import com.org.model.AccountWS;

public interface AccountService {

	AccountWS createAccount(AccountWS account);
	AccountWS getAccount(String accountId);
	List<AccountWS> getAccounts();
	AccountWS updateAccount(String accountId, AccountWS account);
	
}

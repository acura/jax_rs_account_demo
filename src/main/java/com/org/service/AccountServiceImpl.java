package com.org.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.org.Util.AccountConversionUtils.*;
import com.org.Util.AccountConversionUtils;
import com.org.model.AccountDTO;
import com.org.model.AccountWS;
import com.org.repo.AccountRepo;
import com.org.rest.AccountException;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = Logger.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Override
	public AccountWS createAccount(AccountWS account) {
		if(account == null || account.getAccountId() == null || account.getAccountId().isEmpty()) {
			logger.debug("Account id can not be null and empty !");
			throw new AccountException("Account validation issues, Please create valid Account Object");
		}
		
		if(accountRepo.getAccountById(account.getAccountId()).isPresent()) {
			throw new AccountException(String.format("Account alreday present with %s ", account.getAccountId()));
		}
		
		return getWS(accountRepo.saveAccount(getDTO(account)));
	}

	@Override
	public AccountWS getAccount(String accountId) {
		validate(accountId);
		return getWS(accountRepo.getAccountById(accountId).get());
	}

	@Override
	public List<AccountWS> getAccounts() {
		return accountRepo.getAllAccounts()
						  .stream()
						  .map(AccountConversionUtils::getWS)
						  .collect(Collectors.toList());
	}

	@Override
	public AccountWS updateAccount(String accountId, AccountWS account) {
		validate(accountId);
		return getWS(accountRepo.updateAccount(accountId, getDTO(account)));
	}
	
	private void validate(String accountId) {
		if(null == accountId || accountId.isEmpty()) {
			logger.debug("Account id can not be null and empty !");
			throw new AccountException("Account can not be null, Please provide valid Account id");
		}
		Optional<AccountDTO> accountDBRecord = accountRepo.getAccountById(accountId);
		if(!accountDBRecord.isPresent()) {
			logger.debug("Account Not found !");
			throw new AccountException("Account Not Found, Please provide valid Account id");
		}
	}

}

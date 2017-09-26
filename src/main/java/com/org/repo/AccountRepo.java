package com.org.repo;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.org.model.AccountDTO;
import io.jsondb.JsonDBTemplate;

@Repository
public class AccountRepo {
	
	@PostConstruct
	private void init() {
		if(!jsonDBTemplate.collectionExists(AccountDTO.class)) {
			jsonDBTemplate.createCollection(AccountDTO.class);
		}
	}
	
	/**
	 * JsonDBTemplate will take care of Account to json Object and Json Object to Account conversion.
	 * Api internally uses jackson library for conversion and stores data on file with high level of concurrency
	 */
	@Autowired
	private JsonDBTemplate jsonDBTemplate;
	
	public Optional<AccountDTO> getAccountById(String accountId) {
		AccountDTO account = jsonDBTemplate.findById(accountId, AccountDTO.class); 
		return account!=null ? Optional.of(account) : Optional.empty();
	}
	
	public AccountDTO saveAccount(AccountDTO account) {
		jsonDBTemplate.insert(account);
		return getAccountById(account.getId()).get();
	}
	
	public AccountDTO updateAccount(String accountId, AccountDTO account) {
		AccountDTO dbRecord = getAccountById(accountId).get();
		dbRecord.setAmount(account.getAmount());
		dbRecord.setId(account.getId());
		dbRecord.setCustomerName(account.getCustomerName());
		dbRecord.setCurrency(account.getCurrency());
		jsonDBTemplate.upsert(dbRecord);
		return dbRecord;
	}
	
	public List<AccountDTO> getAllAccounts() {
		return  jsonDBTemplate.findAll(AccountDTO.class);
	}
}

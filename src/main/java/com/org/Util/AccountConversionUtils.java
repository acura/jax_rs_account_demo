package com.org.Util;

import org.apache.log4j.Logger;
import com.org.model.AccountDTO;
import com.org.model.AccountWS;

public interface AccountConversionUtils {

	Logger logger = Logger.getLogger(AccountConversionUtils.class);
	
	/**
	 * Converts AccountDTO to AccountWS
	 * @param account
	 * @return
	 */
	public static AccountWS getWS(AccountDTO account) {
		AccountWS ws = new AccountWS.AccountBuilder(account.getId(), account.getCustomerName())
				.amount(account.getAmount())
				.currency(account.getCurrency())
				.build();
		logger.debug(String.format("Converted DTO %s to WS %s", account, ws));
		return ws;
	}
	
	/**
	 * Converts AccountWS to AccountDTO
	 * @param account
	 * @return
	 */
	public static AccountDTO getDTO(AccountWS account) {
		AccountDTO dto = new AccountDTO(account.getAccountId(), account.getCustomerName(), 
				account.getCurrency(), account.getAmount());
		logger.debug(String.format("Converted WS %s to DTO %s", account, dto));
		return dto;
	}
}

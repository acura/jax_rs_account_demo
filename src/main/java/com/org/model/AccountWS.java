package com.org.model;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountWS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String accountId;
	private String customerName;
	private String currency;
	private String amount;
	
	private AccountWS() {
		
	}
	
	private AccountWS(AccountBuilder builder) {
		this.accountId    = builder.accountId;
		this.customerName = builder.customerName;
		this.currency     = builder.currency;
		this.amount       = builder.amount;
	}
	
	
	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	@JsonPOJOBuilder
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AccountBuilder {
		private String accountId;
		private String customerName;
		private String currency;
		private String amount = BigDecimal.ZERO.toString();

		public AccountBuilder(String accountId, String name) {
			this.accountId = accountId;
			this.customerName = name;
		}

		public AccountBuilder amount(String amount) {
			this.amount = amount;
			return this;
		}

		public AccountBuilder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public AccountWS build() {
			return new AccountWS(this);
		}
	}
	
}

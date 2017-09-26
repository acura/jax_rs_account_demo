package com.org.model;

import java.io.Serializable;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "accounts", schemaVersion= "1.0")
public class AccountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String customerName;
	private String currency;
	private String amount;

	public AccountDTO() {

	}

	public AccountDTO(String id, String customerName, String currency, String amount) {
		this.id = id;
		this.customerName = customerName;
		this.currency = currency;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "AccountDTO [id=" + id + ", customerName=" + customerName + ", currency=" + currency + ", amount="
				+ amount + "]";
	}

}

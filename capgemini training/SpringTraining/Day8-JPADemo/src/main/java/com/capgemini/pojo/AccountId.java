package com.capgemini.pojo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AccountId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int accountId;
	private int customerId;
	
	public AccountId() {}

	public AccountId(int accountId, int customerId) {
		this.accountId = accountId;
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "AccountId [accountId=" + accountId + ", customerId=" + customerId + "]";
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	
	
}

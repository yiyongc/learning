package com.capgemini.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Account {
	
	@Id
	private AccountId accId;
	private String accountName;
	
	@Temporal(TemporalType.DATE)
	private Date openDate;
	
	private double balance;
	
	public Account() {}

	public Account(AccountId accId, String accountName, Date openDate, double balance) {
		this.accId = accId;
		this.accountName = accountName;
		this.openDate = openDate;
		this.balance = balance;
	}
	

	@Override
	public String toString() {
		return "Account [accId=" + accId + ", accountName=" + accountName + ", openDate=" + openDate + ", balance="
				+ balance + "]";
	}
	

	public AccountId getAccId() {
		return accId;
	}

	public void setAccId(AccountId accId) {
		this.accId = accId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	
	
	
}

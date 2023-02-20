package com.capgemini.service;

import java.util.List;

import com.capgemini.pojo.Account;

public interface IAccountService {

	public void registerAccount(Account acc);
	
	public List<Account> getAllAccounts();
	
	public void deleteAccount(int id);
	
	public Account getAccount(int id);

	public void updateAccount(Account acc);
	
}

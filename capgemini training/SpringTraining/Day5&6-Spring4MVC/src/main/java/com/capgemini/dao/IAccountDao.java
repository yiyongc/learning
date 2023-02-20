package com.capgemini.dao;

import java.util.List;

import com.capgemini.pojo.Account;

public interface IAccountDao {

	public void registerAccount(Account account);

	public List<Account> getAllAccounts();

	public void deleteAccount(int id);

	public Account getAccount(int id);

	public void updateAccount(Account acc);
	
}

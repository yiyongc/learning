package com.capgemini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.dao.IAccountDao;
import com.capgemini.pojo.Account;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

	//Name must be same as repository annotation
	@Autowired
	private IAccountDao accountDao ;
	
	@Override
	public void registerAccount(Account acc) {
		accountDao.registerAccount(acc);
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountDao.getAllAccounts();
	}

	@Override
	public void deleteAccount(int id) {
		accountDao.deleteAccount(id);
	}

	@Override
	public Account getAccount(int id) {
		return accountDao.getAccount(id);
	}

	@Override
	public void updateAccount(Account acc) {
		accountDao.updateAccount(acc);
	}

}

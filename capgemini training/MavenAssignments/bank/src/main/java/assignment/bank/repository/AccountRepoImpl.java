package assignment.bank.repository;

import java.util.ArrayList;
import java.util.List;

import assignment.bank.beans.Account;
import assignment.bank.exceptions.InvalidAccountException;

public class AccountRepoImpl implements IAccountRepo {

	private List<Account> accounts = new ArrayList<>();

	public Account addAccount(Account account){
		if (accounts.contains(account)){
			return null;
		}
		accounts.add(account);

		return account;
	}

	public Account findOne(int accNumber) throws InvalidAccountException {

		for (Account account : accounts) {
			if (account.getAccNumber() == accNumber)
				return account;
		}

		throw new InvalidAccountException();
	}

}

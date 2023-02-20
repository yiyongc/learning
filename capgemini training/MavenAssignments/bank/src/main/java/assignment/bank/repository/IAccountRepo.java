package assignment.bank.repository;

import assignment.bank.beans.Account;
import assignment.bank.exceptions.InvalidAccountCreationException;
import assignment.bank.exceptions.InvalidAccountException;

public interface IAccountRepo {

	public Account addAccount(Account account) throws InvalidAccountCreationException;

	public Account findOne(int accNumber) throws InvalidAccountException;

}

package assignment.bank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import assignment.bank.beans.Account;
import assignment.bank.beans.Customer;
import assignment.bank.exceptions.InsufficientBalanceException;
import assignment.bank.exceptions.InvalidAccountCreationException;
import assignment.bank.exceptions.InvalidAccountException;
import assignment.bank.exceptions.InvalidAmountException;
import assignment.bank.exceptions.WithdrawLimitException;
import assignment.bank.repository.AccountRepoImpl;
import assignment.bank.service.IServiceBank;
import assignment.bank.service.ServiceBankImpl;

public class MoneyWithdrawalTests {

	IServiceBank service = new ServiceBankImpl(new AccountRepoImpl());
	Account normalAccount = null;
	Account richAccount = null;
	
	Logger logger = Logger.getLogger("Money Withdrawal Test");
	
	@Before
	public void setup() {
		try {
			normalAccount = service.createAccount(new Customer("Tom"), 500);
			richAccount = service.createAccount(new Customer("Harry"), 2000);
		} catch (InvalidAccountCreationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test
	public void validWithdrawal() {
		try {
			assertEquals(400, service.withdraw(normalAccount.getAccNumber(), 100).getAccBalance(), 0.01);
		} catch (InsufficientBalanceException | InvalidAccountException | InvalidAmountException
				| WithdrawLimitException | ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test (expected = assignment.bank.exceptions.InsufficientBalanceException.class)
	public void insufficientBalanceForWithdrawal() throws InsufficientBalanceException {
		try {
			service.withdraw(normalAccount.getAccNumber(), 1000);
		} catch (InvalidAccountException | InvalidAmountException | WithdrawLimitException | ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.WithdrawLimitException.class)
	public void withdrawLimitExceeded() throws WithdrawLimitException {
		try {
			service.withdraw(richAccount.getAccNumber(), 500);
			service.withdraw(richAccount.getAccNumber(), 500);
			service.withdraw(richAccount.getAccNumber(), 1);
		} catch (InsufficientBalanceException | InvalidAccountException | InvalidAmountException | ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAccountException.class)
	public void invalidAccountWithdrawal() throws InvalidAccountException {
		try {
			service.withdraw(999, 10);
		} catch (InsufficientBalanceException | InvalidAmountException | WithdrawLimitException | ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAmountException.class)
	public void invalidAmountWithdrawal() throws InvalidAmountException {
		try {
			service.withdraw(normalAccount.getAccNumber(), -10);
		} catch (InsufficientBalanceException | InvalidAccountException | WithdrawLimitException | ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
}

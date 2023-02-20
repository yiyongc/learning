package assignment.bank;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import assignment.bank.beans.Account;
import assignment.bank.beans.Customer;
import assignment.bank.exceptions.InvalidAccountCreationException;
import assignment.bank.exceptions.InvalidAccountException;
import assignment.bank.exceptions.InvalidAmountException;
import assignment.bank.repository.AccountRepoImpl;
import assignment.bank.service.ServiceBankImpl;

public class MoneyDepositTests {

	ServiceBankImpl service = new ServiceBankImpl(new AccountRepoImpl());
	Account account = null;
	
	Logger logger = Logger.getLogger("Money Deposit Test");
	
	@Before
	public void setup() {
		try {
			account = service.createAccount(new Customer("Tom"), 100);
		} catch (InvalidAccountCreationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	@Test
	public void validDeposit() {
		try {
			assertEquals(110, service.deposit(account.getAccNumber(), 10).getAccBalance(), 0.01);
		} catch (InvalidAccountException | InvalidAmountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		} 
	}

	@Test(expected = assignment.bank.exceptions.InvalidAccountException.class)
	public void invalidAccountDeposit() throws InvalidAccountException {
		try {
			service.deposit(999, 10);
		} catch (InvalidAmountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAmountException.class)
	public void invalidAmountDeposit() throws InvalidAmountException {
		try {
			service.deposit(account.getAccNumber(), -3);
		} catch (InvalidAccountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

}

package assignment.bank;

import static org.junit.Assert.*;

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
import assignment.bank.repository.AccountRepoImpl;
import assignment.bank.service.ServiceBankImpl;

public class MoneyTransferTests {

	ServiceBankImpl service = new ServiceBankImpl(new AccountRepoImpl());
	Account acc1 = null;
	Account acc2 = null;
	
	Logger logger = Logger.getLogger("Money Transfer Test");
	
	@Before
	public void setupAccounts() {
		try {
			acc1 = service.createAccount(new Customer("Tom"), 100);
			acc2 = service.createAccount(new Customer("Harry"), 100);
		} catch (InvalidAccountCreationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	@Test
	public void validTransfer() {
		try {
			assertEquals(90, service.fundTransfer(acc1.getAccNumber(), acc2.getAccNumber(), 10).getAccBalance(), 0.01);
		} catch (InvalidAccountException | InsufficientBalanceException | InvalidAmountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InsufficientBalanceException.class)
	public void insufficientBalanceTransfer() throws InsufficientBalanceException {
		try {
			service.fundTransfer(acc1.getAccNumber(), acc2.getAccNumber(), 200);
		} catch (InvalidAccountException | InvalidAmountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAccountException.class)
	public void invalidAccountTransfer() throws InvalidAccountException {
		try {
			service.fundTransfer(acc1.getAccNumber(), 999, 10);
		} catch (InsufficientBalanceException | InvalidAmountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAccountException.class)
	public void invalidAccountTransfer2() throws InvalidAccountException {
		try {
			service.fundTransfer(999, acc2.getAccNumber(), 10);
		} catch (InsufficientBalanceException | InvalidAmountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAmountException.class)
	public void invalidAmountTransfer()	throws InvalidAmountException {
		try {
			service.fundTransfer(acc1.getAccNumber(), acc2.getAccNumber(), -10);
		} catch ( InsufficientBalanceException | InvalidAccountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
}

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
import assignment.bank.repository.AccountRepoImpl;
import assignment.bank.service.IServiceBank;
import assignment.bank.service.ServiceBankImpl;

public class ViewBalanceTests {

	Logger logger = Logger.getLogger("View Balance Test");
	
	IServiceBank service = new ServiceBankImpl(new AccountRepoImpl());
	Account account = null;
	
	@Before
	public void setup() {
		try {
			account = service.createAccount(new Customer("Harry"), 1992);
		} catch (InvalidAccountCreationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test
	public void validViewBalance() {
		try {
			assertEquals(1992, service.showBalance(account.getAccNumber()).getAccBalance(), 0.01);
		} catch (InvalidAccountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAccountException.class)
	public void invalidAccountView() throws InvalidAccountException {
		service.showBalance(999);
	}

}

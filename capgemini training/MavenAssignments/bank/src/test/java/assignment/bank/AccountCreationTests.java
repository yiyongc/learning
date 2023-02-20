package assignment.bank;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import assignment.bank.beans.Customer;
import assignment.bank.exceptions.InvalidAccountCreationException;
import assignment.bank.repository.AccountRepoImpl;
import assignment.bank.service.ServiceBankImpl;

public class AccountCreationTests {

	ServiceBankImpl service = new ServiceBankImpl(new AccountRepoImpl());
	Logger logger = Logger.getLogger("Account Creation Test");
	
	@Test
	public void createValidAccount()  {
		try {
			assertEquals(100, service.createAccount(new Customer("Tom"), 100).getAccBalance(), 0.01);
		} catch (InvalidAccountCreationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.InvalidAccountCreationException.class)
	public void createInvalidAccount() throws InvalidAccountCreationException {
		service.createAccount(new Customer("Tom"), 99);
	}
	
	@Test(expected = assignment.bank.exceptions.InvalidAccountCreationException.class)
	public void createAccountWithoutName() throws InvalidAccountCreationException {
		service.createAccount(new Customer(""), 99);
	}

}

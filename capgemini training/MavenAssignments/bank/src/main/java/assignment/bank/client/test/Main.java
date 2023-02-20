package assignment.bank.client.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.bank.beans.Customer;
import assignment.bank.exceptions.InvalidAccountCreationException;
import assignment.bank.repository.AccountRepoImpl;
import assignment.bank.repository.IAccountRepo;
import assignment.bank.service.IServiceBank;
import assignment.bank.service.ServiceBankImpl;

public class Main {
	
	private Main() {}
	
	public static void main(String[] args) {
		Logger logger = Logger.getLogger("MAIN");
		IAccountRepo accRepo = new AccountRepoImpl();
		IServiceBank service = new ServiceBankImpl(accRepo);

		BankWithdrawRunner runner = new BankWithdrawRunner(service);
		
		try {
			service.createAccount(new Customer("Sagar"), 500);
		} catch (InvalidAccountCreationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
		
		Thread t1 = new Thread(runner);
		Thread t2 = new Thread(runner);
		
		t1.start();
		t2.start();
		
	}
	
}

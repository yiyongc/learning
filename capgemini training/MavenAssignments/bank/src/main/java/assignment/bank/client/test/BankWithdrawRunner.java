package assignment.bank.client.test;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import assignment.bank.exceptions.InsufficientBalanceException;
import assignment.bank.exceptions.InvalidAccountException;
import assignment.bank.exceptions.InvalidAmountException;
import assignment.bank.exceptions.WithdrawLimitException;
import assignment.bank.service.IServiceBank;

public class BankWithdrawRunner implements Runnable {

	IServiceBank service;
	Logger logger = Logger.getLogger("BankWithdrawRunner");
	
	public BankWithdrawRunner(IServiceBank service) {
		this.service = service;
	}
	
	@Override
	public void run() {
		try {
			service.withdraw(1, 300);
			
		} catch (InsufficientBalanceException | InvalidAccountException | InvalidAmountException
				| WithdrawLimitException | ParseException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
}

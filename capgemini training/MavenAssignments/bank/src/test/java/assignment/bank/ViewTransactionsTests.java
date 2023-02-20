package assignment.bank;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import assignment.bank.beans.Account;
import assignment.bank.beans.Customer;
import assignment.bank.beans.Transaction;
import assignment.bank.exceptions.IncorrectDateRangeException;
import assignment.bank.exceptions.InsufficientBalanceException;
import assignment.bank.exceptions.InvalidAccountCreationException;
import assignment.bank.exceptions.InvalidAccountException;
import assignment.bank.exceptions.InvalidAmountException;
import assignment.bank.exceptions.WithdrawLimitException;
import assignment.bank.repository.AccountRepoImpl;
import assignment.bank.service.IServiceBank;
import assignment.bank.service.ServiceBankImpl;
import assignment.bank.utility.UniqueNumberGenerator;

public class ViewTransactionsTests {

	
	IServiceBank service = new ServiceBankImpl(new AccountRepoImpl());
	
	Logger logger = Logger.getLogger("View Transactions Test");
	
	Account account = null;
	
	@Before
	public void setup() {
		try {
			account = service.createAccount(new Customer("Tom"), 1992);
		} catch (InvalidAccountCreationException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	
	@Test
	public void viewLastTenTransactions() {
		// 5 deposits, 6 withdrawals
		for (int i = 0; i < 5; i++)
			try {
				service.deposit(account.getAccNumber(), 10);
			} catch (InvalidAccountException | InvalidAmountException e) {
				logger.log(Level.FINE, e.getMessage(), e);
			}
		for (int j = 0; j < 6; j++)
			try {
				service.withdraw(account.getAccNumber(), 5);
			} catch (InsufficientBalanceException | InvalidAccountException | InvalidAmountException
					| WithdrawLimitException | ParseException e) {
				logger.log(Level.FINE, e.getMessage(), e);
			}

		
		Object clonedArray = ((ArrayList<Transaction>) account.getTransactions()).clone();
		
		@SuppressWarnings("unchecked")
		List<Transaction> clone = (ArrayList<Transaction>) clonedArray;
		
		clone.remove(0);

		try {
			assertEquals(clone, service.printTransactions(account.getAccNumber()));
			assertEquals(10, service.printTransactions(account.getAccNumber()).size());
		} catch (InvalidAccountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	
	
	@Test
	public void viewLessThanTenTransactions() {
		// 5 withdrawals
		for (int i = 0; i < 5; i++)
			try {
				service.withdraw(account.getAccNumber(), 10);
			} catch (InvalidAccountException | InvalidAmountException | InsufficientBalanceException | WithdrawLimitException | ParseException e) {
				logger.log(Level.FINE, e.getMessage(), e);
			}
	
		List<Transaction> transArray = (ArrayList<Transaction>) account.getTransactions();

		try {
			assertEquals(transArray, service.printTransactions(account.getAccNumber()));
			assertEquals(5, service.printTransactions(account.getAccNumber()).size());
		} catch (InvalidAccountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}
	

	@Test(expected = assignment.bank.exceptions.InvalidAccountException.class)
	public void invalidAccountTransactions() throws InvalidAccountException {
		service.printTransactions(999);
	}

	@Test
	public void viewDateRangeTransactions() {
		Calendar c = Calendar.getInstance();
		c.set(2017, 0, 10);
		Date myDate = c.getTime();
		Transaction dummyTransaction = new Transaction(UniqueNumberGenerator.generateUniqueTransNo(), myDate, 0,
				"Dummy transaction", 100);

		account.addTransaction(dummyTransaction);

		for (int i = 0; i < 10; i++)
			try {
				service.deposit(account.getAccNumber(), 10);
			} catch (InvalidAccountException | InvalidAmountException e) {
				logger.log(Level.FINE, e.getMessage(), e);
			}

		List<Transaction> resultantSearch = new ArrayList<>();
		resultantSearch.add(dummyTransaction);

		try {
			assertEquals(resultantSearch, service.printTransactions(account.getAccNumber(), "2017/1/10", "2017/2/10"));
		} catch (IncorrectDateRangeException | InvalidAccountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test(expected = assignment.bank.exceptions.IncorrectDateRangeException.class)
	public void invalidDateRangeTransactions() throws IncorrectDateRangeException {
		try {
			service.printTransactions(account.getAccNumber(), "2017/7/10", "2017/6/10");
		} catch (InvalidAccountException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
	}

	@Test
	public void noTransactionHistory() {
		List<Transaction> emptyHistory = new ArrayList<>();
		
		try {
			assertEquals(emptyHistory, service.printTransactions(account.getAccNumber()));
			assertEquals(emptyHistory, service.printTransactions(account.getAccNumber(), "2017/5/5", "2017/5/5"));
		} catch (InvalidAccountException | IncorrectDateRangeException e) {
			logger.log(Level.FINE, e.getMessage(), e);
		}
		
	}

}

package assignment.bank.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import assignment.bank.beans.Account;
import assignment.bank.beans.Customer;
import assignment.bank.beans.Transaction;
import assignment.bank.exceptions.IncorrectDateRangeException;
import assignment.bank.exceptions.InsufficientBalanceException;
import assignment.bank.exceptions.InvalidAccountCreationException;
import assignment.bank.exceptions.InvalidAccountException;
import assignment.bank.exceptions.InvalidAmountException;
import assignment.bank.exceptions.WithdrawLimitException;
import assignment.bank.repository.IAccountRepo;
import assignment.bank.utility.UniqueNumberGenerator;

public class ServiceBankImpl implements IServiceBank {

	private IAccountRepo accountRepo;

	public ServiceBankImpl(IAccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}

	public Account createAccount(Customer cust, double amount) throws InvalidAccountCreationException {

		String name = cust.getName();
		
		//Input checking
		if (amount < 100)
			throw new InvalidAccountCreationException(amount);
		if (name.trim().length() == 0)
			throw new InvalidAccountCreationException(name);

		//Create account object if amount is valid
		Account acc = new Account(UniqueNumberGenerator.generateUniqueAccNo(), cust, amount);
		
		return accountRepo.addAccount(acc);
		
	}

	public Account showBalance(int accNumber) throws InvalidAccountException {
		if (accNumber <= 0)
			throw new InvalidAccountException();

		return accountRepo.findOne(accNumber);
	}

	public Account fundTransfer(int fromAcc, int toAcc, double amount)
			throws InvalidAccountException, InsufficientBalanceException, InvalidAmountException {
		// input checking
		if (fromAcc <= 0 || toAcc <= 0)
			throw new InvalidAccountException();
		if (amount <= 0)
			throw new InvalidAmountException(amount, "transfer");

		Account accFrom = accountRepo.findOne(fromAcc);
		Account accTo = accountRepo.findOne(toAcc);
		
		synchronized(accFrom) {
			synchronized(accTo) {
				if (accFrom.getAccBalance() < amount)
					throw new InsufficientBalanceException(amount, "transfer");
				else {
					double newBalanceSource = accFrom.getAccBalance() - amount;
					double newBalanceDestination = accTo.getAccBalance() + amount;
					accFrom.setAccBalance(newBalanceSource);
					accTo.setAccBalance(newBalanceDestination);

					Date currDate = new Date();

					Transaction transactionSource = new Transaction(UniqueNumberGenerator.generateUniqueTransNo(), currDate,
							-amount, "Transfer of $" + amount + " to " + toAcc, newBalanceSource);

					accFrom.addTransaction(transactionSource);

					Transaction transactionDest = new Transaction(UniqueNumberGenerator.generateUniqueTransNo(), currDate,
							amount, "Received $" + amount + " from " + fromAcc, newBalanceDestination);

					accTo.addTransaction(transactionDest);

					return accFrom;
				}
			}
		}

	}

	public Account withdraw(int accNum, double amount) throws InsufficientBalanceException, InvalidAccountException,
			InvalidAmountException, WithdrawLimitException, ParseException {
		String withdrawStr = "withdraw";
		
		if (accNum <= 0)
			throw new InvalidAccountException();
		if (amount <= 0)
			throw new InvalidAmountException(amount, withdrawStr);

		Account accountFound = accountRepo.findOne(accNum);
		
		synchronized(accountFound) {
			if (withdrawLimitReached(accountFound, amount))
				throw new WithdrawLimitException(amount);

			if (accountFound.getAccBalance() < amount)
				throw new InsufficientBalanceException(amount, withdrawStr);
			else {
				double newBalance = accountFound.getAccBalance() - amount;
				accountFound.setAccBalance(newBalance);

				Transaction transaction = new Transaction(UniqueNumberGenerator.generateUniqueTransNo(), new Date(),
						-amount, "Withdrawal of $" + amount, newBalance);

				accountFound.addTransaction(transaction);

				return accountFound;
			}

		}

	}
	
	private boolean withdrawLimitReached(Account acc, double amount) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startOfDay = sdf.parse(sdf.format(new Date()));
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endOfDay = sdf2.parse(sdf.format(new Date()) + " 23:59:59");

		double total = 0;
		
		ArrayList<Transaction> transactionHistory = (ArrayList<Transaction>) acc.getTransactions();

		for (Transaction transaction : transactionHistory) {
			Date transDate = transaction.getDate();
			if (transDate.equals(startOfDay) || transDate.equals(endOfDay) || (transDate.before(endOfDay) && transDate.after(startOfDay))) {
				double amt = transaction.getAmount();
				if (amt < 0) // negative is withdrawal
					total += amt;
			}
		}

		return Math.abs(total) + amount > 1000;
	}

	public Account deposit(int accNum, double amount) throws InvalidAccountException, InvalidAmountException {
		if (accNum <= 0)
			throw new InvalidAccountException();
		if (amount <= 0)
			throw new InvalidAmountException(amount, "withdraw");

		Account account = accountRepo.findOne(accNum);
		
		synchronized(account) {
			double newBalance = account.getAccBalance() + amount;
			account.setAccBalance(newBalance);

			Transaction transaction = new Transaction(UniqueNumberGenerator.generateUniqueTransNo(), new Date(),
					amount, "Deposit of $" + amount, newBalance);

			account.addTransaction(transaction);
		}
		
		return account;
	}


	public List<Transaction> printTransactions(int accNum, String dateFrom, String dateTo)
			throws IncorrectDateRangeException, InvalidAccountException {
		if (accNum <= 0)
			throw new InvalidAccountException();

		Account account = accountRepo.findOne(accNum);

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date fromDate = sdf.parse(dateFrom + " 00:00:00");
			Date toDate = sdf.parse(dateTo + " 23:59:59");
			
			if (fromDate.after(toDate))
				throw new IncorrectDateRangeException(fromDate, toDate);

			List<Transaction> searchResult = new ArrayList<>();

			ArrayList<Transaction> transHistory = (ArrayList<Transaction>) account.getTransactions();

			for (Transaction transaction : transHistory) {
				Date transDate = transaction.getDate();
				if (dateWithinRange(transDate, fromDate, toDate))
					searchResult.add(transaction);
			}

			return searchResult;

		} catch (ParseException e) {
			throw new IncorrectDateRangeException();
		}
	}

	private boolean dateWithinRange(Date date, Date fromDate, Date toDate) {
		return date.equals(fromDate) || date.equals(toDate) || (date.after(fromDate) && date.before(toDate));
	}

	
	public List<Transaction> printTransactions(int accNum) throws InvalidAccountException {
		if (accNum <= 0)
			throw new InvalidAccountException();

		Account account = accountRepo.findOne(accNum);

	
		List<Transaction> transHistory = account.getTransactions();
		List<Transaction> searchResult = new ArrayList<>();

		int lengthOfHistory = transHistory.size();
		int startOfSearch;

		if (lengthOfHistory > 10)
			startOfSearch = lengthOfHistory - 10;
		else
			startOfSearch = 0;

		for (int i = 0; i < 10; i++) {
			if (startOfSearch >= lengthOfHistory)
				break;
			searchResult.add(transHistory.get(startOfSearch++));
		}

		return searchResult;
	}

}

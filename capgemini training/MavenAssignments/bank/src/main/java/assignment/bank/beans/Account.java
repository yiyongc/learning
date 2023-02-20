package assignment.bank.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {

	private Customer cust;
	private int accNumber;
	private double accBalance;
	private List<Transaction> transactionHistory;

	public Account(int accNumber, Customer cust, double balance) {
		this.accNumber = accNumber;
		this.cust = cust;
		this.accBalance = balance;
		this.transactionHistory = new ArrayList<>();
	}

	public int getAccNumber() {
		return accNumber;
	}

	public double getAccBalance() {
		return accBalance;
	}

	public Customer getCustomer() {
		return cust;
	}
	
	public void setAccBalance(double newBalance) {
		this.accBalance = newBalance;
	}

	public List<Transaction> getTransactions() {
		return transactionHistory;
	}

	public void addTransaction(Transaction transaction) {
		transactionHistory.add(transaction);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Account))
			return false;

		Account other = (Account) o;

		return this.getAccNumber() == other.getAccNumber();
	}

	@Override
	public int hashCode() {
		return Objects.hash(accNumber);
	}
}

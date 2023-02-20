package assignment.bank.beans;

import java.util.Date;
import java.util.Objects;

public class Transaction {

	private int transID;
	private Date date;
	private double amount;
	private String description;
	private double balance;

	public Transaction(int transID, Date date, double amount, String desc, double balance) {
		this.transID = transID;
		this.date = date;
		this.amount = amount;
		this.description = desc;
		this.balance = balance;
	}

	public int getTransID() {
		return transID;
	}

	public Date getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Transaction))
			return false;

		Transaction other = (Transaction) o;

		boolean result = true;

		if (this.getTransID() != other.getTransID() || this.getDate().compareTo(other.getDate()) != 0
				|| Double.compare(this.getAmount(), other.getAmount()) != 0)
			result = false;

		if (Double.compare(this.getBalance(), other.getBalance()) != 0
				|| !this.getDescription().equals(other.getDescription()))
			result = false;

		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(transID);
	}

}

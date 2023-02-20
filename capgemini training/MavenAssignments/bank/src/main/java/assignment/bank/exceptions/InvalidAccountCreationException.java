package assignment.bank.exceptions;

public class InvalidAccountCreationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAccountCreationException(double amount) {
		super("Unable to create account with $" + amount + " as the minimum required amount is $100.");
	}

	public InvalidAccountCreationException(String name) {
		super("Unable to create account with invalid customer name.");
	}

}

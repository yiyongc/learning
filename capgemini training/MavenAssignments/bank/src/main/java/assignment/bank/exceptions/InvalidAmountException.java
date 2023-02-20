package assignment.bank.exceptions;

public class InvalidAmountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAmountException(double amount, String function) {
		super("Invalid amount entered. Unable to " + function + amount);
	}

}

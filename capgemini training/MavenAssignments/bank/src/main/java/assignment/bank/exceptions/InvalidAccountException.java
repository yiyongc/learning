package assignment.bank.exceptions;

public class InvalidAccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidAccountException() {
		super("Unable to find account number specified for the transaction");
	}

}

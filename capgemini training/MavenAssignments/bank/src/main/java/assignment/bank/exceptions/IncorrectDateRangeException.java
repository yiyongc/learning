package assignment.bank.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IncorrectDateRangeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncorrectDateRangeException() {
		super("Incorrect date format supplied");
	}

	public IncorrectDateRangeException(Date from, Date to) {
		super(messageForException(from, to));
	}

	private static String messageForException(Date from, Date to) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return "Incorrect range supplied. From: " + dateFormat.format(from) + ", To: " + dateFormat.format(to);
	}

}

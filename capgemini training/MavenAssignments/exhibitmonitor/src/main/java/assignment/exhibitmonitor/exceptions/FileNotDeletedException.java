package assignment.exhibitmonitor.exceptions;

public class FileNotDeletedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileNotDeletedException() {
		super("File wasn't deleted");
	}
	
	public FileNotDeletedException(String message) {
		super(message);
	}

}

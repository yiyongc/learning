package assignment.bank.utility;

public class UniqueNumberGenerator {

	private static int accountNo = 1;
	private static int transactionNo = 1;

	private UniqueNumberGenerator() {
	}

	public static int generateUniqueAccNo() {
		return accountNo++;
	}

	public static int generateUniqueTransNo() {
		return transactionNo++;
	}
}

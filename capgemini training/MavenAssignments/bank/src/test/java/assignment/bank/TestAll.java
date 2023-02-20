package assignment.bank;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountCreationTests.class, MoneyDepositTests.class, MoneyWithdrawalTests.class,
		MoneyTransferTests.class, ViewBalanceTests.class, ViewTransactionsTests.class })

public class TestAll {

}

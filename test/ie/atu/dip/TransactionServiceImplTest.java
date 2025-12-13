package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TransactionServiceImplTest {

	private AccountRepository repository;
	private Bank bank;
	private TransactionService transactionService;

	private static final String VALID_ACCOUNT_NAME = "Adriano Gandini";
	private static final String INVALID_ACCOUNT_NAME = "John Connelly";
	private static final double VALID_ACCOUNT_VALUE = 100;
	private static final double VALID_DEPOSIT_VALUE = 200;
	private static final double WITHDRAW_VALUE = 50;
	private static final double WITHDRAW_VALUE_EXCESS = 5000;
	private static final double NEGATIVE_ACCOUNT_VALUE = -100;
	private static final double ZERO = 0.0;
	private static final String NULL_NAME = null;
	private static final String EMPTY_ACCOUNT_NAME = "";

	@BeforeEach
	void setUP() {
		bank = new BankImpl();
		repository = new AccountRepositoryImpl(bank);
		transactionService = new TransactionServiceImpl(repository, bank);

		// Create a test account to be used for most tests
		try {
			repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		} catch (Exception e) {
			System.out.println("setUp failed: " + e.getMessage());

		}

	}

	// DEPOSIT TESTS

	@Test
	@DisplayName("Should successfully deposit valid amount")
	void testDepositValidAmount() throws AccountNotFoundException, InvalidAccountException, InvalidAmountException {

		double initialBalance = transactionService.getBalance(VALID_ACCOUNT_NAME);

		transactionService.deposit(VALID_ACCOUNT_NAME, VALID_DEPOSIT_VALUE);

		assertEquals(initialBalance + VALID_DEPOSIT_VALUE, transactionService.getBalance(VALID_ACCOUNT_NAME));
	}

	@Test
	@DisplayName("Should not deposit for a invalid account")
	void testDespositInvalidAccount() {
		assertThrows(AccountNotFoundException.class, () -> {
			transactionService.deposit(INVALID_ACCOUNT_NAME, VALID_DEPOSIT_VALUE);
		});
	}

	@Test
	@DisplayName("Should not deposit zero")
	void testDepositZeroAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			transactionService.deposit(VALID_ACCOUNT_NAME, ZERO);
		});
	}

	@Test
	@DisplayName("Should not deposit negative amount")
	void testDepositNegativeAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			transactionService.deposit(VALID_ACCOUNT_NAME, NEGATIVE_ACCOUNT_VALUE);
		});
	}
	

	// WITHDRAW TESTS
	
	@Test
	@DisplayName("Should successfully withdraw valid amount")
	void testWithdrawValidAmount() 
			throws AccountNotFoundException, InvalidAccountException, InvalidAmountException {
		
	    double initialBalance = transactionService.getBalance(VALID_ACCOUNT_NAME);
	    
	    transactionService.withdraw(VALID_ACCOUNT_NAME, WITHDRAW_VALUE);
	    
	    assertEquals(initialBalance - WITHDRAW_VALUE, transactionService.getBalance(VALID_ACCOUNT_NAME));
	}

	@Test
	@DisplayName("Should reject withdrawal exceeding balance")
	void testWithdrawExeeding() {
		assertThrows(InvalidAmountException.class, () -> {
			transactionService.withdraw(VALID_ACCOUNT_NAME, WITHDRAW_VALUE_EXCESS);
		});
	}

	@Test
	@DisplayName("Should reject withdrawal of zero amount")
	void testWithdrawZeroAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			transactionService.withdraw(VALID_ACCOUNT_NAME, ZERO);
		});
	}

	@Test
	@DisplayName("Should reject withdrawal on non valid account")
	void testWithdrawInvalidAccount() {
		assertThrows(AccountNotFoundException.class, () -> {
			transactionService.withdraw(INVALID_ACCOUNT_NAME, VALID_DEPOSIT_VALUE);
		});
	}

	@Test
	@DisplayName("Should reject withdrawal of negative values")
	void testWithdrawNegativeValue() {
		assertThrows(InvalidAmountException.class, () -> {
			transactionService.withdraw(VALID_ACCOUNT_NAME, NEGATIVE_ACCOUNT_VALUE);
		});
	}

	// GET BALANCE TESTS

	@Test
	@DisplayName("Should return correct balance for an existing account name")
	void testGetBalanceValidAccount() throws AccountNotFoundException, InvalidAccountException {
		double expectedBalance = transactionService.getBalance(VALID_ACCOUNT_NAME);
		assertEquals(VALID_ACCOUNT_VALUE, expectedBalance);

	}

	@Test
	@DisplayName("Should reject balance for a non existing account name")
	void testGetBalanceInvalidAccount() {
		assertThrows(AccountNotFoundException.class, () -> {
			transactionService.getBalance(INVALID_ACCOUNT_NAME);
		});
	}

	@Test
	@DisplayName("Should reject balance for a null account name")
	void testGetBalanceNullAccount() {
		assertThrows(InvalidAccountException.class, () -> {
			transactionService.getBalance(NULL_NAME);
		});
	}

	@Test
	@DisplayName("Should reject balance for a empty account name")
	void testGetBalanceEmptyAccount() {
		assertThrows(InvalidAccountException.class, () -> {
			transactionService.getBalance(EMPTY_ACCOUNT_NAME);
		});
	}

	// INTEGRATION TESTS

	@ParameterizedTest
	@CsvSource({ "100.0, 50.0,  150.0", // deposit, withdraw, expected final balance
			"200.0, 100.0, 200.0", // (starting balance is 100)
			"500.0, 300.0, 300.0", 
			"50.0,  25.0,  125.0" })
	@DisplayName("Should handle multiple transactions correctly")
	void testMultipleTransactions(double depositAmount, double withdrawAmount, double expectedBalance)
			throws AccountNotFoundException, InvalidAmountException, InvalidAccountException {
		// Account already created with 100.0 in @BeforeEach

		transactionService.deposit(VALID_ACCOUNT_NAME, depositAmount);
		transactionService.withdraw(VALID_ACCOUNT_NAME, withdrawAmount);

		double actualBalance = transactionService.getBalance(VALID_ACCOUNT_NAME);
		assertEquals(expectedBalance, actualBalance);
	}

}

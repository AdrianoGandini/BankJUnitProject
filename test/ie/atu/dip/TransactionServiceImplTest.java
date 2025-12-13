package ie.atu.dip;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionServiceImplTest {

	private AccountRepository repository;
	private Bank bank;
	private TransactionService transactionService;
	
	
	private static final String VALID_ACCOUNT_NAME = "Adriano Gandini";
	private static final String INVALID_ACCOUNT_NAME = "John Connelly";
	private static final double VALID_ACCOUNT_VALUE = 100;
	private static final double VALID_DEPOSIT_VALUE = 200;
	private static final double WITHDRAW_VALUE_EXCESS = 500;
	private static final double NEGATIVE_ACCOUNT_VALUE = -100;
	private static final double ZERO = 0.0;
	
	
	@BeforeEach
	void setUP() {
		bank = new BankImpl();
		repository = new AccountRepositoryImpl(bank);
		transactionService = new TransactionServiceImpl(repository, bank);
		
		
		//Create a test account to be used for most tests 
		try {
			repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		} catch (Exception e) {
			System.out.println("setUp failed: " + e.getMessage());

		} 		
		
	}
	
	//DEPOSIT TESTS
	
	@Test
	@DisplayName("Should successfully deposit valid amount")
	void testDepositValidAmount() 
			throws AccountNotFoundException, InvalidAccountException, InvalidAmountException {
		
		double initialBalance = transactionService.getBalance(VALID_ACCOUNT_NAME);
		
		transactionService.deposit(VALID_ACCOUNT_NAME, VALID_DEPOSIT_VALUE);
		
		assertEquals(initialBalance + VALID_DEPOSIT_VALUE, transactionService.getBalance(VALID_ACCOUNT_NAME));
	}
	
	
	//WITHDRAW TESTS
	
	@Test
	@DisplayName("Should reject withdrawal exceeding balance")
	void testWithdrawExeeding() {
		assertThrows(InvalidAmountException.class, () ->{
			transactionService.withdraw(VALID_ACCOUNT_NAME, WITHDRAW_VALUE_EXCESS);
		});
	}
	
	@Test
	@DisplayName("Should reject withdrawal of zero amount")
	void testWithdrawZeroAmount() {
		assertThrows(InvalidAmountException.class, () ->{
			transactionService.withdraw(VALID_ACCOUNT_NAME, ZERO);
		});
	}
	
	
	@Test
	@DisplayName("Should reject withdrawal on non valid account")
	void testWithdrawInvalidAccount() {
		assertThrows(AccountNotFoundException.class, () ->{
			transactionService.withdraw(INVALID_ACCOUNT_NAME, VALID_DEPOSIT_VALUE);
		});
	}


	@Test
	@DisplayName("Should reject withdrawal of negative values")
	void testWithdrawNegativeValue() {
		assertThrows(InvalidAmountException.class, () ->{
			transactionService.withdraw(VALID_ACCOUNT_NAME, NEGATIVE_ACCOUNT_VALUE);
		});
	}

	
	
	//GET BALANCE TESTS
	
	//"Should return correct balance for a existing account"
	//"Should return an exception for an non-existent account"
	
	
	//INTEGRATION TESTS
	
	//"Should handle multiple transactions correctly")

}

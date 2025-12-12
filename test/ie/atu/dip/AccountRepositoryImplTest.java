package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountRepositoryImplTest {
	
	private static final double ZERO = 0.0;
	private static final double NEGATIVE_ACCOUNT_VALUE = -100;
	private static final double VALID_ACCOUNT_VALUE = 100;
	private static final String VALID_ACCOUNT_NAME = "Adriano Gandini";
	private static final String EMPTY_ACCOUNT_NAME = "";
	private static final String NULL_NAME = null;
	private static final String INVALID_ACCOUNT_NAME = "John Connelly";

	private AccountRepository repository;
	private Bank bank;
	
	@BeforeEach
	void setUp() {
		bank = new BankImpl();
		repository = new AccountRepositoryImpl(bank);
	}
	
	@Test
	void testAddAccount_ZeroAmount_ShouldThrowException() {
		
		assertThrows(InvalidAmountException.class, () -> {
			repository.addAccount(VALID_ACCOUNT_NAME, ZERO);
		});		
	}
	
	@Test
	void testAddAccount_NegativeAmount_ShouldThrowException() {
		
		assertThrows(InvalidAmountException.class, () -> {
			repository.addAccount(VALID_ACCOUNT_NAME, NEGATIVE_ACCOUNT_VALUE);
		});		
	}
	
	@Test
	void testAddAccount_EmptyName_ShouldThrowException() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.addAccount(EMPTY_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	void testAddAccount_NullName_ShouldThrowException() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.addAccount(NULL_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	void testAddAccount_DuplicateAccount_ShouldThrowException() 
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {
		
		repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		
		assertThrows(InvalidAccountException.class, () -> {
			repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	void testFindAccount_ExistingAccount_ShouldReturnAccount() 
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {
		
		repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		
		Account result = repository.findAccount(VALID_ACCOUNT_NAME);
		
		assertNotNull(result);
		assertEquals(VALID_ACCOUNT_NAME, result.getAccountHolder());
		assertEquals(VALID_ACCOUNT_VALUE, result.getBalance());		
	}
	
	@Test
	void testFindAccount_NonExistentAccount_ShouldThrowException() 
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {
		
		repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		
		assertThrows(AccountNotFoundException.class, ()->{
			repository.findAccount(INVALID_ACCOUNT_NAME);
		});
	}
	
	
	@Test
	void testFindAccount_EmptyName_ShouldThrowException() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.findAccount(EMPTY_ACCOUNT_NAME);
		});
	}
	
	@Test
	void testFindAccount_NullName_ShouldThrowException() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.findAccount(NULL_NAME);
		});
	}
	
	@Test
	void testAddAccount_BankDeposits_ShouldIncreaseDeposits() 
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {
		
		double initialBankTotal = bank.getTotalDeposits();  // Should be 0
	    
	    repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
	    
	    double expectedBankTotal = initialBankTotal + VALID_ACCOUNT_VALUE;
	    double actualBankTotal = bank.getTotalDeposits();
	    
	    assertEquals(expectedBankTotal, actualBankTotal);
		
		 
	}
	
}

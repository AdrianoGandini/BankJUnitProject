package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
	@DisplayName("Should throw exception for zero amount")
	void testAddAccountZeroAmount() {

		assertThrows(InvalidAmountException.class, () -> {
			repository.addAccount(VALID_ACCOUNT_NAME, ZERO);
		});
	}
	
	@Test
	@DisplayName("Should throw exception for negative amount")
	void testAddAccountNegativeAmount() {

		assertThrows(InvalidAmountException.class, () -> {
			repository.addAccount(VALID_ACCOUNT_NAME, NEGATIVE_ACCOUNT_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should throw exception for empty account name")
	void testAddAccountEmptyName() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.addAccount(EMPTY_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should throw exception for null account name")
	void testAddAccountNullName() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.addAccount(NULL_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should throw exception for duplicate account")
	void testAddAccountDuplicateAccount()
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {

		repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);

		assertThrows(InvalidAccountException.class, () -> {
			repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should return account for existing account")
	void testFindAccountExistingAccount()
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {

		repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);

		Account result = repository.findAccount(VALID_ACCOUNT_NAME);

		assertNotNull(result);
		assertEquals(VALID_ACCOUNT_NAME, result.getAccountHolder());
		assertEquals(VALID_ACCOUNT_VALUE, result.getBalance());
	}
	
	@Test
	@DisplayName("Should throw exception for non-existent account")
	void testFindAccountNonExistentAccount()
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {

		repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);

		assertThrows(AccountNotFoundException.class, ()->{
			repository.findAccount(INVALID_ACCOUNT_NAME);
		});
	}
	
	
	@Test
	@DisplayName("Should throw exception for empty name in find")
	void testFindAccountEmptyName() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.findAccount(EMPTY_ACCOUNT_NAME);
		});
	}
	
	@Test
	@DisplayName("Should throw exception for null name in find")
	void testFindAccountNullName() {
		assertThrows(InvalidAccountException.class, () -> {
			repository.findAccount(NULL_NAME);
		});
	}
	
	@Test
	@DisplayName("Should increase bank deposits when adding account")
	void testAddAccountBankDeposits()
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {

		double initialBankTotal = bank.getTotalDeposits();  // Should be 0

	    repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);

	    double expectedBankTotal = initialBankTotal + VALID_ACCOUNT_VALUE;
	    double actualBankTotal = bank.getTotalDeposits();

	    assertEquals(expectedBankTotal, actualBankTotal);


	}
	
}

package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(value = 2, unit = TimeUnit.SECONDS) 
class AccountTest {

	private static final String VALID_ACCOUNT_NAME = "Adriano Gandini";
	private static final double VALID_VALUE = 100;
	private static final double VALID_DEPOSIT_VALUE = 200;

	private static final double WITHDRAW_VALUE = 50;
	private static final double WITHDRAW_VALUE_EXCESS = 500;

	private static final double NEGATIVE_VALUE = -100;
	private static final double ZERO = 0.0;

	private static final String NULL_NAME = null;
	private static final String EMPTY_ACCOUNT_NAME = "";

	private static final double VALID_LOAN_VALUE = 100;
	private static final double INVALID_REPAY_VALUE = 1000;
	private static final double PARTIAL_REPAY_VALUE = 50;

	private Account account;

	@BeforeEach
	void setUp() throws InvalidAccountException, InvalidAmountException {
		account = new Account(VALID_ACCOUNT_NAME, VALID_VALUE);
	}

	// Constructor validation tests

	@Test
	@DisplayName("Should throw InvalidAccountException when account holder is null")
	void testNullAccountHolder() {
		assertThrows(InvalidAccountException.class, () -> {
			new Account(NULL_NAME, VALID_VALUE);
		});
	}

	@Test
	@DisplayName("Should throw InvalidAccountException when account holder is empty")
	void testEmptyAccountHolder() {
		assertThrows(InvalidAccountException.class, () -> {
			new Account(EMPTY_ACCOUNT_NAME, VALID_VALUE);
		});
	}

	@Test
	@DisplayName("Should throw InvalidAmountException when balance is negative")
	void testNegativeBalance() {
		assertThrows(InvalidAmountException.class, () -> {
			new Account(VALID_ACCOUNT_NAME, NEGATIVE_VALUE);
		});
	}

	@Test
	@DisplayName("Should create valid account successfully")
	void testValidAccount() throws InvalidAccountException, InvalidAmountException {
		// Account with valid name and value created by setUP()

		assertNotNull(account);
		assertEquals(VALID_ACCOUNT_NAME, account.getAccountHolder());
		assertEquals(VALID_VALUE, account.getBalance());
	}

	// Business method tests

	@Test
	@DisplayName("Should reject deposit for negative amount")
	void testDepositNegativeAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			account.deposit(NEGATIVE_VALUE);
		});
	}

	@Test
	@DisplayName("Should reject deposit for zero amount")
	void testDepositZeroAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			account.deposit(ZERO);
		});
	}

	@Test
	@DisplayName("Should successfully deposit valid amount")
	void testDepositValidAmount() throws InvalidAccountException, InvalidAmountException {
		double initialValue = account.getBalance();
		account.deposit(VALID_DEPOSIT_VALUE);
		double updatedValue = account.getBalance();

		assertEquals(initialValue + VALID_DEPOSIT_VALUE, updatedValue);
	}

	@Test
	@DisplayName("Should reject withdrawal exceeding balance")
	void testWithdrawInsufficientFunds() {
		assertThrows(InvalidAmountException.class, () -> {
			account.withdraw(WITHDRAW_VALUE_EXCESS);
		});
	}

	@Test
	@DisplayName("Should reject withdrawal of zero amount")
	void testWithdrawZeroAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			account.withdraw(ZERO);
		});
	}

	@Test
	@DisplayName("Should reject withdrawal of negative amount")
	void testWithdrawNegativeAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			account.withdraw(NEGATIVE_VALUE);
		});
	}

	@Test
	@DisplayName("Should successfully withdraw valid amount")
	void testWithdrawValidAmount() throws InvalidAmountException {
		double initialValue = account.getBalance();
		account.withdraw(WITHDRAW_VALUE);
		double updatedValue = account.getBalance();

		assertEquals(initialValue - WITHDRAW_VALUE, updatedValue);
	}

	@Test
	@DisplayName("Should reject loan approval for negative amount")
	void testApproveLoanNegativeAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			account.approveLoan(NEGATIVE_VALUE);
		});
	}

	@Test
	@DisplayName("Should reject loan approval for zero amount")
	void testApproveLoanZeroAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			account.approveLoan(ZERO);
		});
	}

	@Test
	@DisplayName("Should successfully approve loan for valid amount")
	void testApproveValidAmount() throws InvalidAmountException {
		double initialLoanValue = account.getLoan();

		account.approveLoan(VALID_LOAN_VALUE);
		assertEquals(initialLoanValue + VALID_LOAN_VALUE, account.getLoan());
	}
	
	@Test
	@DisplayName("Should reject repayment for negative amount")
	void testRepayLoanNegativeAmount() {
		assertThrows(InvalidAmountException.class, ()-> {
			account.repayLoan(NEGATIVE_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should reject repayment for zero amount")
	void testRepayLoanZeroAmount() {
		assertThrows(InvalidAmountException.class, ()-> {
			account.repayLoan(ZERO);
		});
	}
	
	@Test
	@DisplayName("Should reject repayment for amount greater than the loan")
	void testRepayLoanInvalidAmount() {
		assertThrows(InvalidAmountException.class, ()-> {
			account.repayLoan(INVALID_REPAY_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should successfully pay loan for valid amount")
	void testReapyLoanValidAmount() throws InvalidAmountException {
		account.approveLoan(VALID_LOAN_VALUE);  
	    
	    account.repayLoan(PARTIAL_REPAY_VALUE);               
	    
	    assertEquals(PARTIAL_REPAY_VALUE, account.getLoan());
	}

}

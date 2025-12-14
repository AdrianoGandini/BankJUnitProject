package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LoanServiceImplTest {
	
	private AccountRepository repository;
	private Bank bank;
	private LoanService loanService;
	
	private static final String VALID_ACCOUNT_NAME = "Adriano Gandini";
	private static final String INVALID_ACCOUNT_NAME = "John Connelly";
	private static final double VALID_ACCOUNT_VALUE = 1000;
	
	private static final double VALID_LOAN_VALUE = 200;
	private static final double HIGH_LOAN_VALUE = 10000;
	private static final double VALID_LOAN_PAY = 100;
	private static final double NEGATIVE_LOAN_VALUE = -100;
	
	private static final double ZERO = 0.0;
	private static final String NULL_NAME = null;
	private static final String EMPTY_ACCOUNT_NAME = "";

	
	
	@BeforeAll
	static void setUpBeforeClass() {
		System.out.println("Starting LoanServiceImpl tests.");
	}
		
	@BeforeEach
	void setUp() throws InvalidAccountException, AccountNotFoundException, InvalidAmountException {
		bank = new BankImpl();
		repository = new AccountRepositoryImpl(bank);
		loanService = new LoanServiceImpl(repository, bank);
		
		repository.addAccount(VALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
	}
	
	@AfterAll
	static void afterClass() {

		System.out.println("LoanServiceImpl tests completed.");
	}

	//GET LOAN TESTS
	@Nested
	@DisplayName("getLoan()")
	class GetLoanTests {
		@Test
		@DisplayName("Should return zero loan for new account")
		void testGetLoanNewAccount() throws AccountNotFoundException, InvalidAccountException {
			double result = loanService.getLoan(VALID_ACCOUNT_NAME);
			assertEquals(ZERO, result);
		}
		
		@Test
		@DisplayName("Should throw InvalidAccountException for null account")
		void testGetLoanNullAccount() {
			assertThrows(InvalidAccountException.class, () -> {
				loanService.getLoan(NULL_NAME);
			});
		}
		
		@Test
		@DisplayName("Should throw InvalidAccountException for empty account")
		void testGetLoanEmptyAccount() {
			assertThrows(InvalidAccountException.class, () -> {
				loanService.getLoan(EMPTY_ACCOUNT_NAME );
			});
		}
		
		@Test
		@DisplayName("Should throw AccountNotFoundException for empty account")
		void testGetLoanInvalidAccount() {
			assertThrows(AccountNotFoundException.class, () -> {
				loanService.getLoan(INVALID_ACCOUNT_NAME);
			});
		}
		
		@Test
		@DisplayName("Should return loan ballance for valid account")
		void testGetLoanValidAccount() throws AccountNotFoundException, InvalidAccountException, InvalidAmountException {

			loanService.approveLoan(VALID_ACCOUNT_NAME, VALID_LOAN_VALUE);
			double loanBallance = loanService.getLoan(VALID_ACCOUNT_NAME);
			
			assertEquals(VALID_LOAN_VALUE, loanBallance);
		}
	}
	
	
	//REPAYLOAN TEST
	
	@Test
	@DisplayName("Should repay loan ballance for valid account")
	void testRepayLoanValidAccount() throws AccountNotFoundException, InvalidAmountException, InvalidAccountException {
		//Approve a loan
		loanService.approveLoan(VALID_ACCOUNT_NAME, VALID_LOAN_VALUE);
		double initialLoanBallance = loanService.getLoan(VALID_ACCOUNT_NAME);
		//Repay partially
		loanService.repayLoan(VALID_ACCOUNT_NAME, VALID_LOAN_PAY);
		double afterPayBallance = loanService.getLoan(VALID_ACCOUNT_NAME);
		
		assertEquals(initialLoanBallance - VALID_LOAN_PAY, afterPayBallance);
	}
	
	@Test
	@DisplayName("Should throw InvalidAmountException for negative payment")
	void testRepayLoanNegativeAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			loanService.repayLoan(VALID_ACCOUNT_NAME, NEGATIVE_LOAN_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should throw InvalidAmountException for zero payment")
	void testRepayLoanZeroAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			loanService.repayLoan(VALID_ACCOUNT_NAME, ZERO);
		});
	}
	
	@Test
	@DisplayName("Should throw InvalidAmountException for amount grater than loan ballance")
	void testRepayLoanInvalidAmount() {
		assertThrows(InvalidAmountException.class, () -> {
			//Loan balance is started with 0 			
			loanService.repayLoan(VALID_ACCOUNT_NAME, VALID_LOAN_PAY);
		});
	}
	
	
	
	//APPROVELOAN TEST
	
	@Test
	@DisplayName("Should return InvalidAmountException for value greater than the bank total deposits amount")
	void testApproveLoanHighValue() {
		
		assertThrows(InvalidAmountException.class, () -> {
			loanService.approveLoan(VALID_ACCOUNT_NAME, HIGH_LOAN_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should return InvalidAmountException for negative amount")
	void testApproveLoanNegativeAmount() {
		assertThrows(InvalidAmountException.class, () ->{
			loanService.approveLoan(VALID_ACCOUNT_NAME, NEGATIVE_LOAN_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should return InvalidAmountException for zero amount")
	void testApproveLoanZeroAmount() {
		assertThrows(InvalidAmountException.class, () ->{
			loanService.approveLoan(VALID_ACCOUNT_NAME, ZERO);
		});
	}
	
	@Test
	@DisplayName("Should return AccountNotFoundException for non valid account")
	void testApproveLoanInvalidAccount() {
		assertThrows(AccountNotFoundException.class, () ->{
			loanService.approveLoan(INVALID_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should throw InvalidAccountException for empty account")
	void testApproveLoanEmptyAccount() {
		assertThrows(InvalidAccountException.class, () ->{
			loanService.approveLoan(EMPTY_ACCOUNT_NAME, VALID_ACCOUNT_VALUE);
		});
	}
	
	@Test
	@DisplayName("Should throw InvalidAccountException for null account")
	void testApproveLoanNullAccount() {
		assertThrows(InvalidAccountException.class, () ->{
			loanService.approveLoan(NULL_NAME, VALID_ACCOUNT_VALUE);
		});
	}

}

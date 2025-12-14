package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class BankImplTest {
	
	
	private static final double INITIAL_DEPOSIT = 100.0;
	private static final double WITHDRAWAL_AMOUNT = 50.0;
	private static final double INSUFFICIENT_WITHDRAWAL = 200.0;
	private static final double SMALL_AMOUNT_1 = 0.1;
	private static final double SMALL_AMOUNT_2 = 0.2;
	private static final double EXPECTED_SUM = 0.3;
	private static final double NEGATIVE_AMOUNT = -100.0;
	private static final double NEGATIVE_WITHDRAWAL = -50.0;
	private static final double ZERO = 0.0;
	private static final double DELTA = 0.0001; 
	
	private BankImpl bank;
	
	@BeforeEach
	void setUp() {
		bank = new BankImpl();
	}

	@Test
	@DisplayName("Should have zero initial total deposits")
	void testGetTotalDepositsInitialValue() {
		assertEquals(ZERO, bank.getTotalDeposits());
	}
	
	@Test
	@DisplayName("Should increase total when adding positive amount")
	void testAddToTotalPositiveAmount() throws InvalidAmountException {

		bank.addToTotal(INITIAL_DEPOSIT);

		double result = bank.getTotalDeposits();
		double expected = INITIAL_DEPOSIT;

		assertEquals(expected, result);

	}
	
	@Test
	@DisplayName("Should throw InvalidAmountException when adding negative amount")
	void testAddToTotalNegativeAmount() throws InvalidAmountException {
	    assertThrows(InvalidAmountException.class, () -> {
	    	bank.addToTotal(NEGATIVE_AMOUNT);
	    });

	}
	
	@Test
	@DisplayName("Should throw InvalidAmountException when adding zero amount")
	void testAddToTotalZeroAmount() throws InvalidAmountException {
		assertThrows(InvalidAmountException.class, () -> {
	    	bank.addToTotal(ZERO);
	    });
	}
	
	@Test
	@DisplayName("Should decrease total when subtracting available amount")
	void testSubtractFromTotalAvailableAmount() throws InvalidAmountException {
		bank.addToTotal(INITIAL_DEPOSIT);

		bank.subtractFromTotal(WITHDRAWAL_AMOUNT);


		double result = bank.getTotalDeposits();
		double expected = INITIAL_DEPOSIT - WITHDRAWAL_AMOUNT;

		assertEquals(expected, result);

	}
	
	@Test
	@DisplayName("Should throw InvalidAmountException when subtracting negative amount")
	void testSubtractFromTotalNegativeAmount() throws InvalidAmountException {
		bank.addToTotal(INITIAL_DEPOSIT);

		assertThrows(InvalidAmountException.class, () -> {
			bank.subtractFromTotal(NEGATIVE_WITHDRAWAL);
		});

	}
	
		
	@Test
	@DisplayName("Should throw InvalidAmountException when balance is insufficient")
	void testSubtractFromTotalInsufficientBalance() throws InvalidAmountException {
	    // Arrange
	    bank.addToTotal(INITIAL_DEPOSIT);

	    // Act & Assert
	    assertThrows(InvalidAmountException.class, () -> {
	    	bank.subtractFromTotal(INSUFFICIENT_WITHDRAWAL);
	    });

	}
	
	@Test
	@DisplayName("Should throw InvalidAmountException when subtracting zero amount")
	void testSubtractFromTotalZeroAmount() throws InvalidAmountException {
	    // Arrange
	    bank.addToTotal(INITIAL_DEPOSIT);

	    // Act & Assert
	    assertThrows(InvalidAmountException.class, () -> {
	    	bank.subtractFromTotal(ZERO);
	    });

	}
	
	
	@Test
	@DisplayName("Should handle floating point precision correctly")
	void testAddToTotalSmallAmount() throws InvalidAmountException {
	    // Testing floating-point precision with 0.1 + 0.2
	    bank.addToTotal(SMALL_AMOUNT_1);
	    bank.addToTotal(SMALL_AMOUNT_2);

	    assertEquals(EXPECTED_SUM, bank.getTotalDeposits(), DELTA);
	}

	
	

}

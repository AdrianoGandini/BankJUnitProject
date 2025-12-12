package ie.atu.dip;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
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
	void testGetTotalDeposits_InitialValue_ShouldBeZero() {
		assertEquals(ZERO, bank.getTotalDeposits());
	}
	
	@Test
	void testAddToTotal_AddPositiveAmount_ShouldIncreaseTotal() throws InvalidAmountException {
		
		bank.addToTotal(INITIAL_DEPOSIT);
		
		double result = bank.getTotalDeposits();
		double expected = INITIAL_DEPOSIT;
		
		assertEquals(expected, result);
				
	}
	
	@Test
	void testAddToTotal_AddNegativeAmount_ShouldThrowException() throws InvalidAmountException {
	    assertThrows(InvalidAmountException.class, () -> {
	    	bank.addToTotal(NEGATIVE_AMOUNT);
	    });
		
	}
	
	@Test
	void testAddToTotal_AddZeroAmount_ShouldThrowException() throws InvalidAmountException {
		assertThrows(InvalidAmountException.class, () -> {
	    	bank.addToTotal(ZERO);
	    });
	}
	
	@Test
	void testSubtractFromTotal_SubtractAvailableAmount_ShouldDecreaseTotal() throws InvalidAmountException {
		bank.addToTotal(INITIAL_DEPOSIT);

		bank.subtractFromTotal(WITHDRAWAL_AMOUNT);
		

		double result = bank.getTotalDeposits();
		double expected = INITIAL_DEPOSIT - WITHDRAWAL_AMOUNT;
		
		assertEquals(expected, result);
		
	}
	
	@Test
	void testSubtractFromTotal_SubtractNegativeAmount_ShouldDecreaseTotal() throws InvalidAmountException {
		bank.addToTotal(INITIAL_DEPOSIT);
			
		assertThrows(InvalidAmountException.class, () -> {
			bank.subtractFromTotal(NEGATIVE_WITHDRAWAL);
		});	
		
	}
	
		
	@Test
	void testSubtractFromTotal_InsufficientBalance_ShouldThrowException() throws InvalidAmountException {
	    // Arrange
	    bank.addToTotal(INITIAL_DEPOSIT);
	    	    
	    // Act & Assert
	    assertThrows(InvalidAmountException.class, () -> {
	    	bank.subtractFromTotal(INSUFFICIENT_WITHDRAWAL);
	    });
	    
	}
	
	@Test
	void testSubtractFromTotal_SubtractZeroAmount_ShouldThrowException() throws InvalidAmountException {
	    // Arrange
	    bank.addToTotal(INITIAL_DEPOSIT);
	    	    
	    // Act & Assert
	    assertThrows(InvalidAmountException.class, () -> {
	    	bank.subtractFromTotal(ZERO);
	    });
	    
	}
	
	
	@Test
	void testAddToTotal_AddVerySmallAmount_ShouldHandleFloatingPointPrecision() throws InvalidAmountException {
	    // Testing floating-point precision with 0.1 + 0.2
	    bank.addToTotal(SMALL_AMOUNT_1);
	    bank.addToTotal(SMALL_AMOUNT_2);
	    
	    assertEquals(EXPECTED_SUM, bank.getTotalDeposits(), DELTA);
	}

	
	

}

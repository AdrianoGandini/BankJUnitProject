package ie.atu.dip;

public class BankImpl implements Bank {

	// Tracks total deposits in the bank instance
	private  double totalDeposits; 

	public BankImpl() {
		this.totalDeposits = 0;
	}
	/**
	 * Gets the total deposits available in the bank.
	 * 
	 * @return The total deposits.
	 */
	public double getTotalDeposits() {
		return totalDeposits;

	}
	
	
	public void addToTotal(double amount) throws InvalidAmountException {
		
		//Check if amount is positive
		if( amount <= 0) {
			throw new InvalidAmountException("The amount is not valid");
		}
		
		totalDeposits += amount;
	}

	public void subtractFromTotal(double amount) throws InvalidAmountException {
		
		//Check if the balance is sufficient.
		if (amount > totalDeposits || amount <= 0) {
			throw new InvalidAmountException("Insufficient Bank balance or invalid withdrawal amount.");			
				
		}
		totalDeposits -= amount;
	}

	

}

package ie.atu.dip;

public interface Bank {
	
	
	double getTotalDeposits();
	
	void addToTotal(double amount) throws InvalidAmountException;
	
	void subtractFromTotal(double amount) throws InvalidAmountException;

}

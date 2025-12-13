package ie.atu.dip;

public class Account {

	private String accountHolder; // Name of the account holder
	private double balance; // Current account balance
	private double loan; // Outstanding loan amount

	// Constructor to create a new account
	public Account(String accountHolder, double balance) {
		this.accountHolder = accountHolder;
		this.balance = balance;
		this.loan = 0;
	}

	// Getter for the account holder's name
	public String getAccountHolder() {
		return accountHolder;
	}

	// Getter for the account balance
	public double getBalance() {
		return balance;
	}

	// Getter for the loan amount
	public double getLoan() {
		return loan;
	}

	// Method to deposit money into the account
	public void deposit(double amount) throws InvalidAmountException {
		if (amount <= 0) {
			throw new InvalidAmountException("The amount is not valid");
		}
		balance += amount;
	}

	// Method to withdraw money from the account (only if balance is sufficient)
	public void withdraw(double amount) throws InvalidAmountException {
	    if (amount <= 0) {
	        throw new InvalidAmountException("The amount is not valid");
	    }
	    if (amount > balance) {
	        throw new InvalidAmountException("Insufficient funds");
	    }
	    balance -= amount;
	}

	// Method to approve a loan for the account
	public void approveLoan(double amount) {
		loan += amount;
	}

	// Method to repay a part of the loan (only if amount <= loan)
	public boolean repayLoan(double amount) {
		if (amount > loan)
			return false; // Repayment exceeds loan
		loan -= amount;
		return true;
	}
}

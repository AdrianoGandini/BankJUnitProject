package ie.atu.dip;

public class Account {

	private String accountHolder; // Name of the account holder
	private double balance; // Current account balance
	private double loan; // Outstanding loan amount

	// Constructor to create a new account
	public Account(String accountHolder, double balance) throws InvalidAccountException, InvalidAmountException {
		
		if (accountHolder == null || accountHolder.trim().isEmpty()) {
	        throw new InvalidAccountException("Account holder name cannot be null or empty");
	    }
	    if (balance < 0) {  // Maybe also validate initial balance?
	        throw new InvalidAmountException("Initial balance cannot be negative");
	    }
	    
		this.accountHolder = accountHolder;
		this.balance = balance;
		this.loan = 0;
	}

	// Getter for the account holder's name
	public String getAccountHolder(){
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
	public void approveLoan(double amount) throws InvalidAmountException {
		if (amount <= 0) {
	        throw new InvalidAmountException("Loan amount must be positive");
	    }
	    loan += amount;
	}

	// Method to repay a part of the loan (only if amount <= loan)
	public void repayLoan(double amount) throws InvalidAmountException {
		if (amount > loan || amount <= 0)
			throw new InvalidAmountException("Repayment loan amount not valid");
		loan -= amount;
	}
}

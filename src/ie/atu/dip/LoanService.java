package ie.atu.dip;

public interface LoanService {
	
	/**
	 * Gets the loan amount of a specific account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @return The loan amount if the account exists, otherwise null.
	 * @throws AccountNotFoundException if account is not found.
	 */
	Double getLoan(String accountHolder) throws AccountNotFoundException, InvalidAccountException ;
	
	
	/**
	 * Repays a part of the loan for an account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param amount        The repayment amount.
	 * @return True if the repayment is successful, otherwise false.
	 * @throws AccountNotFoundException if account is not found.
	 */
	boolean repayLoan(String accountHolder, double amount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException  ;
	
	
	/**
	 * Approves a loan for an account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param loanAmount    The loan amount.
	 * @return True if the loan is approved, otherwise false.
	 * @throws AccountNotFoundException if account is not found.
	 */
	boolean approveLoan(String accountHolder, double loanAmount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException ;
}

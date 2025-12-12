package ie.atu.dip;

/**
 * Transactions services interface to provide basic transactions functionalities.
 */

public interface TransactionService {

	
	/**
	 * Deposits money into an account.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param amount        The deposit amount.
	 * @return True if the deposit is successful, otherwise false.
	 * @throws AccountNotFoundException if account is not found.
	 */
	boolean deposit(String accountHolder, double amount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException  ;
	
	
	/**
	 * Withdraws money from an account.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @param amount        The withdrawal amount.
	 * @return True if the withdrawal is successful, otherwise false.
	 * @throws AccountNotFoundException if account is not found.
	 */
	boolean withdraw(String accountHolder, double amount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException ;
	
	
	/**
	 * Gets the balance of a specific account holder.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @return The balance if the account exists, otherwise null.
	 * @throws AccountNotFoundException if account is not found.
	 */
	Double getBalance(String accountHolder) throws AccountNotFoundException, InvalidAccountException ;
}

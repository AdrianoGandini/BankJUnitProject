package ie.atu.dip;

/**
* Repository interface for managing account storage and retrieval operations.
*/
public interface AccountRepository {
	
	/**
	 * Helper method to find an account by account holder's name.
	 * 
	 * @param accountHolder The name of the account holder.
	 * @return The Account object if found, otherwise null.
	 * @throws AccountNotFoundException if account is not found.
	 * @throws InvalidAccountException 
	 */
	Account findAccount(String accountHolder) throws AccountNotFoundException, InvalidAccountException;
	
	
	
	/**
	 * Adds a new account with an initial deposit.
	 * 
	 * @param accountHolder  The name of the new account holder.
	 * @param initialDeposit The initial deposit amount.
	 * @throws InvalidAccountException if account data is invalid.
	 */
	void addAccount(String accountHolder, double initialDeposit) 
			throws InvalidAccountException, AccountNotFoundException, InvalidAmountException;

}

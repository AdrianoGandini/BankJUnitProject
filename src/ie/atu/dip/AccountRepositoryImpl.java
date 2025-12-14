package ie.atu.dip;

import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

	// List to store all accounts in the banking application
	private List<Account> accounts;
	private Bank bank;

	public AccountRepositoryImpl(Bank bank) {
		accounts = new ArrayList<>();
		this.bank = bank;
	}

	@Override
	public Account findAccount(String accountHolder) throws AccountNotFoundException, InvalidAccountException {

		validateAccountEntry(accountHolder);

		for (Account account : accounts) {
			if (account.getAccountHolder().equals(accountHolder)) {
				return account;
			}
		}
		throw new AccountNotFoundException(accountHolder);
	}

	@Override
	public void addAccount(String accountHolder, double initialDeposit)
			throws InvalidAccountException, InvalidAmountException {

		validateAccountEntry(accountHolder);

		if (initialDeposit <= 0) {
			throw new InvalidAmountException("Deposit cannot be negative");
		}

		// Check for duplicates
		try {
			findAccount(accountHolder);
			throw new InvalidAccountException("Account " + accountHolder + " already exists");
		} catch (AccountNotFoundException e) {
			accounts.add(new Account(accountHolder, initialDeposit));
			bank.addToTotal(initialDeposit);
		}
	}

	/**
	 * Validates that the account holder name is not null or empty.
	 * 
	 * @param accountHolder The account holder name to validate
	 * @throws InvalidAccountException if the name is null or empty
	 */
	private void validateAccountEntry(String accountHolder) throws InvalidAccountException {

		if (accountHolder == null || accountHolder.trim().isEmpty()) {
			throw new InvalidAccountException("Account holder name cannot be null or empty");
		}
	}
}

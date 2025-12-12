package ie.atu.dip;

public class TransactionServiceImpl implements TransactionService {


	private AccountRepository manager;
	private Bank bank;

	public TransactionServiceImpl(AccountRepository manager, Bank bank) {
		this.manager = manager;
		this.bank = bank;

	}

	@Override
	public boolean deposit(String accountHolder, double amount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException  {
		Account account = manager.findAccount(accountHolder);
		if (account == null || amount <= 0)
			return false;
		account.deposit(amount);
		bank.addToTotal(amount);
		return true;
	}

	@Override
	public boolean withdraw(String accountHolder, double amount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException {
		Account account = manager.findAccount(accountHolder) ;
		if (account == null || amount <= 0)
			return false;
		if (account.withdraw(amount)) {
			bank.subtractFromTotal(amount);
			return true;
		}
		return false;
	}

	@Override
	public Double getBalance(String accountHolder) throws AccountNotFoundException, InvalidAccountException {
		Account account = manager.findAccount(accountHolder);
		return account != null ? account.getBalance() : null;
	}

}

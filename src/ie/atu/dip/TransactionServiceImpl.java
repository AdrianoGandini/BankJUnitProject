package ie.atu.dip;

public class TransactionServiceImpl implements TransactionService {


	private AccountRepository repository;
	private Bank bank;

	public TransactionServiceImpl(AccountRepository manager, Bank bank) {
		this.repository = manager;
		this.bank = bank;

	}

	@Override
	public Double deposit(String accountHolder, double amount) 
			throws AccountNotFoundException, InvalidAmountException, InvalidAccountException  {
		
		Account account = repository.findAccount(accountHolder);
		account.deposit(amount); 
		bank.addToTotal(amount);
		return account.getBalance();
	}

	@Override
	public Double withdraw(String accountHolder, double amount) 
			throws AccountNotFoundException, InvalidAmountException, InvalidAccountException {
		
		Account account = repository.findAccount(accountHolder);
		account.withdraw(amount);
		bank.subtractFromTotal(amount);
		return account.getBalance();
	}

	@Override
	public Double getBalance(String accountHolder) 
			throws AccountNotFoundException, InvalidAccountException {
		
		Account account = repository.findAccount(accountHolder);
		return  account.getBalance();
	}

}

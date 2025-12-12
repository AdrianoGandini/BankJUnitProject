package ie.atu.dip;

public class LoanServiceImpl implements LoanService {

	private AccountRepository manager;
	private Bank bank;

	public LoanServiceImpl(AccountRepository manager, Bank bank) {
		this.manager = manager;
		this.bank = bank;
	}

	@Override
	public Double getLoan(String accountHolder) throws AccountNotFoundException, InvalidAccountException {
		Account account = manager.findAccount(accountHolder) ;
		return account != null ? account.getLoan() : null;
	}

	@Override
	public boolean repayLoan(String accountHolder, double amount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException  {
		Account account = manager.findAccount(accountHolder);
		if (account == null || amount <= 0)
			return false;
		if (account.repayLoan(amount)) {
			bank.addToTotal(amount);
			return true;
		}
		return false;
	}

	@Override
	public boolean approveLoan(String accountHolder, double loanAmount) throws AccountNotFoundException, InvalidAmountException, InvalidAccountException {
		Account account = manager.findAccount(accountHolder);
		if (account == null || loanAmount > bank.getTotalDeposits())
			return false;
		account.approveLoan(loanAmount);
		bank.subtractFromTotal(loanAmount);
		return true;
	}
}

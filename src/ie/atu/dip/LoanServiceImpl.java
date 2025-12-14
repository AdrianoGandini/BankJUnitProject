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

		Account account = manager.findAccount(accountHolder);
		return account != null ? account.getLoan() : null;
	}

	@Override
	public void repayLoan(String accountHolder, double amount)
			throws AccountNotFoundException, InvalidAmountException, InvalidAccountException {

		Account account = manager.findAccount(accountHolder);
		account.repayLoan(amount);
		bank.addToTotal(amount);

	}

	@Override
	public void approveLoan(String accountHolder, double loanAmount)
			throws AccountNotFoundException, InvalidAmountException, InvalidAccountException {

		Account account = manager.findAccount(accountHolder);
		account.approveLoan(loanAmount);
		bank.subtractFromTotal(loanAmount);
	}
}

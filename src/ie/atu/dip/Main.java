package ie.atu.dip;

public class Main {
	
	public static void main(String[] args) {
		
		BankImpl bank = new BankImpl();
		AccountRepositoryImpl manager = new AccountRepositoryImpl(bank);
		LoanServiceImpl loan = new LoanServiceImpl(manager, bank);
		TransactionServiceImpl transacion = new TransactionServiceImpl(manager, bank);
		
		
	}
}

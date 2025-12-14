package ie.atu.dip;

public class Main {
	
	public static void main(String[] args) {
		
		BankImpl bank = new BankImpl();
		AccountRepositoryImpl manager = new AccountRepositoryImpl(bank);
		LoanServiceImpl loan = new LoanServiceImpl(manager, bank);
		TransactionServiceImpl transacion = new TransactionServiceImpl(manager, bank);
		
		
		
		
		try {
			
			manager.addAccount("Alice", 1000);
			manager.addAccount("Bob", 500);
			
			//Test deposits
			System.out.println("Depositing 200 to Alice.Alice's balance: " + transacion.deposit("Alice", 200)); // Should return 1200
		    System.out.println("Alice's balance: " + transacion.getBalance("Alice")); // Should be 1200
		    
		    // Test withdrawals
		    System.out.println("Withdrawing 300 from Bob: " + transacion.withdraw("Bob", 300)); // Should return true
		    System.out.println("Bob's balance: " + transacion.getBalance("Bob")); // Should be 200

		    // Test loan approval
		    //System.out.println("Approving a loan of 400 for Alice: " + loan.approveLoan("Alice", 400)); // Should return true
		    System.out.println("Alice's loan: " + loan.getLoan("Alice")); // Should be 400

		    // Test loan repayment
		    //System.out.println("Repaying 200 of Alice's loan: " + loan.repayLoan("Alice", 200)); // Should return true
		    System.out.println("Alice's remaining loan: " + loan.getLoan("Alice")); // Should be 200

		    // Check total deposits in the bank
		    System.out.println("Total deposits in the bank: " + bank.getTotalDeposits());
	
			
		} catch (AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}catch (InvalidAccountException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	    

	}
}

package ie.atu.dip;

public class AccountNotFoundException extends Exception {

	public AccountNotFoundException(String accountHolder) {
        super("Account not found for: " + accountHolder);
    }

}

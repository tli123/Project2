public class CheckingAccount extends Account {

    private static final double MIN_BALANCE = 50;
    private static final double INTEREST_RATE = 0;

    public CheckingAccount(int account_id, int pin, double balance) {
	super(account_id, pin, balance);
    }

    private double calcPenalty() {
	return getBalance() > 5 ? 5 : 0.1 * getBalance();
    }

    public String formatReceipt() {
	return String.format("%-12s", "Checking") + formatReceipt2();
    }

    public String toString() {
	return "c " + formatFile();
    }
}

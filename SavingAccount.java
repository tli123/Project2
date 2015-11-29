public class SavingAccount extends Account {

    private static final double MIN_BALANCE = 200;
    private static final double INTEREST_RATE = 0.005 / 12;

    public SavingAccount(int account_id, int pin, double balance) {
	super(account_id, pin, balance);
    }

    private double calcPenalty() {
	return getBalance() > 10 ? 10 : 0.1 * getBalance();
    }

    public String formatReceipt() {
	return String.format("%-12s", "Saving") + formatReceipt2();
    }

    public String toString() {
	return "s " + formatFile();
    }
}

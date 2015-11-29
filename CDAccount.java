public class CDAccount extends Account {

    private static final double MIN_BALANCE = 500;
    private static final double INTEREST_RATE = 0.05 / 12;

    public CDAccount(int account_id, int pin, double balance) throws NegativeBalanceException {
	super(account_id, pin, balance);
	if (balance < MIN_BALANCE) throw new NegativeBalanceException();
    }

    public synchronized void withdraw(double amount) {
    }

    public String formatReceipt() {
	return String.format("%-12s", "CD") + formatReceipt2();
    }

    public String toString() {
	return "d " + formatFile();
    }
}

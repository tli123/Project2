public class CDAccount extends Account {

    public CDAccount(int account_id, int pin, double balance) throws NegativeBalanceException {
	super(account_id, pin, balance);
	MIN_BALANCE = 500;
	INTEREST_RATE = 0.05 / 12;
	if (balance < MIN_BALANCE) throw new NegativeBalanceException();
    }

    public synchronized void withdraw(double amount) {
    }

    public String formatReceipt() {
	return String.format("%-12s", "CD") + formatReceipt2();
    }

    public String toString() {
	return "x " + formatFile();
    }
}

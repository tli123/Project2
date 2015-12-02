public class SavingAccount extends Account {

    public SavingAccount(int account_id, int pin, double balance) {
	super(account_id, pin, balance);
	MIN_BALANCE = 200;
	INTEREST_RATE = 0.005 / 12;
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

    public Object[] returnSelf() {
	return new Object[]{"Saving Account", new Integer(getID()), new Double(getBalance())};
    }
}

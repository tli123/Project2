public class CheckingAccount extends Account {

    public CheckingAccount(int account_id, int pin, double balance) {
	super(account_id, pin, balance);
	MIN_BALANCE = 50;
	INTEREST_RATE = 0;
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

    public Object[] returnSelf() {
	return new Object[]{"Checking Account", new Integer(getID()), new Double(getBalance())};
    }
}

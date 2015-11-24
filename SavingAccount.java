public class SavingAccount extends Account {

    private static final double MIN_BALANCE = 200;
    private static final double INTEREST_RATE = 0.005 / 12;

    public SavingAccount(int account_id, int pin, int balance) {
	super(account_id, pin, balance);
    }

    private double calcPenalty() {
	return balance > 10 ? 10 : 0.1 * balance;
    }

    public String formatRecipt() {
	return String.format("%12s", "Saving") + super();
    }

    public String toString() {
	return "s " + super();
    }
}

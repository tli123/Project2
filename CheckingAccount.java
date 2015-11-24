public class CheckingAccount extends Account {

    private static final double MIN_BALANCE = 50;
    private static final double INTEREST_RATE = 0;

    public SavingAccount(int account_id, int pin, int balance) {
	super(account_id, pin, balance);
    }

    private double calcPenalty() {
	return balance > 5 ? 5 : 0.1 * balance;
    }

    public String formatRecipt() {
	return String.format("%12s", "Checking") + super();
    }

    public String toString() {
	return "c " + super();
    }
}

public class CDAccount extends Account {

    private static final double MIN_BALANCE = 500;
    private static final double INTEREST_RATE = 0.05 / 12;

    public CDAccount(int account_id, int pin, int balance) throws NegativeBalaceException {
	super(account_id, pin, balance);
	if (balance < MIN_BALANCE) throw new NegativeBalaceException("FUCK NO");
    }

    public synchronized void withdraw(double amount) {
    }

    public String formatRecipt() {
	return String.format("%12s", "CD") + super();
    }

    public String toString() {
	return "c " + super();
    }
}

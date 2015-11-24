public class CDAccount extends Account {

    private static final double MIN_BALANCE = 500;
    private static final double INTEREST_RATE = 0.05 / 12;

    public CDAccount(int account_id, int pin, int balance) {
	super(account_id, pin, balance);
    }

    public synchronized void withdraw(double amount){
    }

    public String toString() {
	return "D " + super();
    }
}

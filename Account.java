public abstract class Account {

    private static final double MIN_BALANCE;
    private static final double INTEREST_RATE;

    private int account_id;
    private int pin;
    private double balance;

    public Account(int account_id, int pin, int balance) {
	setID(account_id);
	setPin(pin);
	setBalance(balance);
    }

    public synchronized void withdraw(double amount){
    }

    public synchronized void desposit(double amount){
    }

    public int getPin() {
	return pin;
    }

    public int getID() {
	return account_id;
    }

    public int getBalance() {
	return balance;
    }

    public synchronized void modBalance(double amount) {
    }

}

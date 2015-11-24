public class Account {

    private static final double MIN_BALANCE = 0;
    private static final double INTEREST_RATE = 0;

    private int account_id;
    private int pin;
    private double balance;
    private boolean penalty_p;
    private boolean open_p;

    public Account(int account_id, int pin, int balance) {
        this.account_id = account_id;
	this.pin = pin;
	this.balance = balance;
	if(balance < MIN_BALANCE) penalty_p = true;
	this.open_p = false;
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

    public synchronized void withdraw(double amount){
	modBalance(-1*amount);
    }

    public synchronized void desposit(double amount){
	modBalance(amount);
    }

    private double calcPenalty() {
	return 0;
    }

    public synchronized void applyMonthly(){
	double total_extra = balance*INTEREST_RATE;
	if (penalty_p) total_extra += calcPenalty();
	modBalance(total_extra);
    }

    public synchronized void modBalance(double amount) throws NegativeBalanceException {
	double new_balance = balance + amount;
	if(new_balance < 0) throw new NegativeBalanceException();
	if(new_balance < MIN_BALANCE) penalty_p = true;
	else penalty_p = false;
	balance = new_balance;
    }

    public String formatRecipt() {
	String.format("    %d    %10s", account_id, String.format("%.2d", balance));
    }

    public String toString() {
	return String.format("%d %d %d\n", account_id, pin, balance);
    }

    
}

public class Account {

    public double MIN_BALANCE;
    public double INTEREST_RATE;

    private int account_id;
    private int pin;
    private double balance;
    private boolean penalty_p;
    private boolean open_p;

    public Account(int account_id, int pin, double balance) {
        this.account_id = account_id;
	this.pin = pin;
	this.balance = balance;
	if(balance < MIN_BALANCE) penalty_p = true;
	this.open_p = false;
    }

    public synchronized void setOpen(boolean b) {
	open_p = b;
    }

    public synchronized boolean getOpen() {
	return open_p;
    }

    public int getPin() {
	return pin;
    }

    public int getID() {
	return account_id;
    }

    public double getBalance() {
	return balance;
    }

    public synchronized void withdraw(double amount) throws NegativeBalanceException {
	modBalance(-1*amount);
    }

    public synchronized void deposit(double amount) throws NegativeBalanceException {
	modBalance(amount);
    } 

    private double calcPenalty() {
	return 0;
    }

    public synchronized double applyMonthly() throws NegativeBalanceException {
	double total_extra = balance*INTEREST_RATE;
	System.out.println(MIN_BALANCE);
	if (penalty_p) total_extra += calcPenalty();
	modBalance(total_extra);
	return total_extra;
    }

    public synchronized void modBalance(double amount) throws NegativeBalanceException {
	double new_balance = balance + amount;
	if(new_balance < 0) throw new NegativeBalanceException();
	if(new_balance < MIN_BALANCE) penalty_p = true;
	else penalty_p = false;
	balance = new_balance;
    }

    public String formatReceipt() {
	return "";
    }

    public String formatReceipt2() {
	return String.format("    %d    $%10s", account_id, String.format("%.2f", balance));
    }

    public String formatFile() {
	return String.format("%d %d %s\n", account_id, pin, String.format("%.2f", balance));
    }
}

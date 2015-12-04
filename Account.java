/**
 * Account.java
 *
 * This is the superclass of the possible accounts in the bank.
 *
 * File:
 *	$Id: Account.java,v 1.0 2015/12/02 16:43:23 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: Account.java,v $
 *	Initial revision
 *
 */

/**
 * The account class.
 *
 * @author Ziwei Ye
 * @author Tommy Li
 */

public abstract class Account {

    /**
     * The minimum balance.
     */
    public double MIN_BALANCE;

    /**
     * The interest rate of the account.
     */
    public double INTEREST_RATE;

    /**
     * The account ID of the account.
     */
    private int account_id;

    /**
     * The pin of this account.
     */
    private int pin;

    /**
     * The balance of the account.
     */
    private double balance;

    /**
     * Checks whether the account has penalties from being
     * under minimum balance.
     */
    private boolean penalty_p;

    /**
     * Sets the account up.
     * @param account_id - The account id.
     * @param pin - The pin of the account.
     * @param balance - The balance of the account.
     */
    public Account(int account_id, int pin, double balance) {
        this.account_id = account_id;
	this.pin = pin;
	this.balance = balance;
    }

    /**
     * Gets the pin of the account.
     * @return The pin of the account.
     */
    public int getPin() {
	return pin;
    }

    /**
     * Gets the ID of the account.
     * @return The ID of the account.
     */
    public int getID() {
	return account_id;
    }

    /**
     * Gets the balance of the account.
     * @return The balance of the account.
     */
    public double getBalance() {
	return balance;
    }

    /**
     * Set penalty predicate.
     */
    public void setP(boolean a) {
	penalty_p = a;
    }

    /**
     * Withdraws money from the account.
     * @param amount - The amount to be withdrawn.
     * @return True if the withdrawal is successful, false otherwise.
     * @exception NegativeBalanceException - Thrown when a withdrawal
     *                                       makes a balance negative.
     */
    public synchronized boolean withdraw(double amount) throws NegativeBalanceException {
	modBalance(-1*amount);
	return true;
    }

    /**
     * Deposits money from the account.
     * @param amount - The amount to be withdrawn.
     * @exception NegativeBalanceException - Thrown when a deposit
     *                                       makes a balance negative.
     * **NOTE**: The exception is only needed because modBalance requires it.
     */
    public synchronized void deposit(double amount) throws NegativeBalanceException {
	modBalance(amount);
    } 

    /**
     * Calculates the penalty for the account. Overriden in subclass. 
     * @return The penalty for the account.
     */

    abstract double calcPenalty();

    /**
     * Applies the monthly interest or penalty on this account.
     * @return The amount that is applied.
     * @exception NegativeBalanceException - Thrown when a penalty
     *                                       makes a balance negative.
     */
    public synchronized double applyMonthly() throws NegativeBalanceException {
	double total_extra = balance*INTEREST_RATE;
	if (penalty_p) {
	    total_extra += (-1 * calcPenalty());
	}
	modBalance(total_extra);
	return total_extra;
    }

    /**
     * Applies changes money wise to accounts.
     * @param amount - The amount the account balance is changed by.
     * @exception NegativeBalanceException - Thrown when a balance change
     *                                       makes a balance negative.
     */
    public synchronized void modBalance(double amount) throws NegativeBalanceException {
	double new_balance = balance + amount;
	if(new_balance < 0) throw new NegativeBalanceException();
	if(new_balance < MIN_BALANCE) penalty_p = true;
	else penalty_p = false;
	balance = new_balance;
    }

    /**
     * Used to format the string for the GUI. Overriden in subclass.
     * @return The string used in the GUI.
     */
    public String formatReceipt() {
	return "";
    }

    /**
     * Used to format the string to be displayed in the terminal.
     * @return The string used in the terminal.
     */
    public String formatReceipt2() {
	return String.format("    %d    $%10s", account_id, String.format("%.2f", balance));
    }

    /**
     * Used to format the string written to the file.
     * @return The string used in the file.
     */
    public String formatFile() {
	return String.format("%d %d %s\n", account_id, pin, String.format("%.2f", balance));
    }

    /**
     * Used to format the string in the GUI for a cleaner look.
     * @return The list representation of the object.
     */
    public Object[] returnSelf() {
	return new Object[3];
    }
}

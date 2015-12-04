/**
 * CDAccount.java
 *
 * This is a subclass of account, holds CD accounts.
 *
 *
 * File:
 *	$Id: CDAccount.java,v 1.0 2015/12/2 01:23:22 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: CDAccount.java,v $
 *	Initial revision
 *
 */

/**
 * The CD Account.
 *
 * @author Tommy Li
 * @auther Ziwei Ye
 */

public class CDAccount extends Account {

    /**
     * Sets the account up.
     * @param account_id - The account id.
     * @param pin - The pin of the account.
     * @param balance - The balance of the account.
     * @exception NegativeBalanceException - Thrown when the balance
     *                                       becomes negative.
     */
    public CDAccount(int account_id, int pin, double balance) throws NegativeBalanceException {
	super(account_id, pin, balance);
	MIN_BALANCE = 500;
	INTEREST_RATE = 0.05 / 12;
	if (balance < MIN_BALANCE) throw new NegativeBalanceException();
    }

    @Override
    public double calcPenalty() {
	return 0;
    }

    /**
     * Withdraws money from the account.
     * @param amount - The amount to be withdrawn.
     * @return True if the withdrawal is successful, false otherwise.
     * @exception NegativeBalanceException - Thrown when a withdrawal
     *                                       makes a balance negative.
     */
    public synchronized boolean withdraw(double amount) {
	return false;
    }

    /**
     * Used to format the string for the GUI.
     * @return The string used in the GUI.
     */
    public String formatReceipt() {
	return String.format("%-12s", "CD") + formatReceipt2();
    }

    /**
     * Used to format the string written to the file.
     * @return The string used in the file.
     */
    public String toString() {
	return "c " + formatFile();
    }

    /**
     * Used to format the string in the GUI for a cleaner look.
     * @return The list representation of the object.
     */
    public Object[] returnSelf() {
	return new Object[]{"CD Account", new Integer(getID()), new Double(getBalance())};
    }
}

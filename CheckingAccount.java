/**
 * CheckingAccount.java
 *
 * This is a subclass of account, holds Checking accounts.
 *
 *
 * File:
 *	$Id: CheckingAccount.java,v 1.0 2015/12/2 01:23:22 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: CheckingAccount.java,v $
 *	Initial revision
 *
 */

/**
 * The Checking Account.
 *
 * @author Tommy Li
 * @auther Ziwei Ye
 */

public class CheckingAccount extends Account {

    /**
     * Sets the account up.
     * @param account_id - The account id.
     * @param pin - The pin of the account.
     * @param balance - The balance of the account.
     */
    public CheckingAccount(int account_id, int pin, double balance) {
	super(account_id, pin, balance);
	MIN_BALANCE = 50;
	INTEREST_RATE = 0;
    }

    /**
     * Calculates the penalty for the account. 
     * @return The penalty for the account.
     */
    private double calcPenalty() {
	return getBalance() > 5 ? 5 : 0.1 * getBalance();
    }

    /**
     * Used to format the string for the GUI.
     * @return The string used in the GUI.
     */
    public String formatReceipt() {
	return String.format("%-12s", "Checking") + formatReceipt2();
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
	return new Object[]{"Checking Account", new Integer(getID()), new Double(getBalance())};
    }
}

/**
 * SavingAccount.java
 *
 * This is a subclass of account, holds saving accounts.
 *
 *
 * File:
 *	$Id: SavingAccount.java,v 1.0 2015/12/2 01:23:22 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: SavingAccount.java,v $
 *	Initial revision
 *
 */

/**
 * The Saving Account.
 *
 * @author Tommy Li
 * @auther Ziwei Ye
 */

public class SavingAccount extends Account {

    /**
     * Sets the account up.
     * @param account_id - The account id.
     * @param pin - The pin of the account.
     * @param balance - The balance of the account.
     */
    public SavingAccount(int account_id, int pin, double balance) {
	super(account_id, pin, balance);
	MIN_BALANCE = 200;
	INTEREST_RATE = 0.005 / 12;
	if(balance < MIN_BALANCE) setP(true);
    }

    /**
     * Calculates the penalty for the account. 
     * @return The penalty for the account.
     */
    @Override
    public double calcPenalty() {
	return getBalance() > 10 ? 10 : 0.1 * getBalance();
    }

    /**
     * Used to format the string for the GUI.
     * @return The string used in the GUI.
     */
    public String formatReceipt() {
	return String.format("%-12s", "Saving") + formatReceipt2();
    }

    /**
     * Used to format the string written to the file.
     * @return The string used in the file.
     */
    public String toString() {
	return "s " + formatFile();
    }

    /**
     * Used to format the string in the GUI for a cleaner look.
     * @return The list representation of the object.
     */
    public Object[] returnSelf() {
	return new Object[]{"Saving Account", new Integer(getID()), new Double(getBalance())};
    }
}

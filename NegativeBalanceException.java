/**
 * NegativeBalanceException.java
 *
 * Exception that is thrown when an account goes under min balance for CD,
 * or under 0 for Checking and Savings.
 *
 * File:
 *	$Id: NegativeBalanceException.java,v 1.0 2015/12/02 14:12:26 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: NegativeBalanceException.java,v $
 *	Initial revision
 *
 */

/**
 * The Exception thrown when the accounts go under min balance.
 *
 * @author Tommy Li
 * @author Ziwei Ye
 */

public class NegativeBalanceException extends Exception {

    /**
     * Constructor for the exception.
     */
    public NegativeBalanceException() {
	super();
    }

}

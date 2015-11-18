/**
 * Bank.java
 *
 * This is the main class that holds the bank and all its transactions, 
 * implemented with Swing 
 *
 * File:
 *	$Id: Bank.java,v 1.0 2015/11/xx 00:00:00 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: Bank.java,v $
 *	Initial revision
 *
 */

/**
 * The main bank class.
 *
 * @author Tommy Li
 * @author Ye Ziwei
 */

public class Bank {

    private HashTable<int, Account> accounts = new HashTable();

    private void openData(String filename) {
    }

    public static void main(String[] args) {
	if(args.length < 1) { System.out.println(""); return; }
	Bank bank = new Bank();
	bank.openData(args[0]);
	if(args.length == 2) { bank.runBatch(args[1]); return; }
	
    }

}


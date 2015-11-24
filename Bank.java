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

import java.util.Observable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.lang.String;

public class Bank extends Observable {

    /**
     * All of the accounts that are in the bank.
     */
    private ArrayList<Account> accounts;

    /**
     * The scanner that reads the bankFile.
     */
    private Scanner sc;

    public Bank(String filename) {
	accounts = new ArrayList<Account>();
	sc = new Scanner(new File(filename));
	while (sc.hasNext()) {
	    String accountInfo = sc.next();
	    String[] accountData = accountInfo.split(" ");
	    Account acc = null;
	    if (accountData[1].equals("c")) {
		acc = new CDAccount(accountData[0], accountData[2], accountData[3]);
	    } else if (accountData[1].equals("s")) {
		acc = new SavingAccount(accountData[0], accountData[2], accountData[3]);
	    } else if (accountData[1].equals("x")) {
		acc = new CheckingAccount(accountData[0], accountData[2], accountData[3]);
	    }
	    accounts.add(acc);
	}
    }


    public static void main(String[] args) {
	if (args.length < 1 || args.length > 2) {
	    System.out.println("Usage: java Bank bankFile [batchFile]");
	    System.exit(0);
	} 
        Bank bank = new Bank(args[0]);
	if (args.length == 1) {
	    //open gui
	} else if (args.length == 2) {
	    //open batchmode
	}
    }

}


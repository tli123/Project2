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
	    if (accountData[0].equals("c")) {
		acc = new CDAccount(accountData[1], accountData[2], accountData[3]);
	    } else if (accountData[0].equals("s")) {
		acc = new SavingAccount(accountData[1], accountData[2], accountData[3]);
	    } else if (accountData[0].equals("x")) {
		acc = new CheckingAccount(accountData[1], accountData[2], accountData[3]);
	    }
	    accounts.add(acc);
	}
    }

    public void batchMode(String filename) {
	System.out.println("==========   Initial Bank Data ==================\n\n" + toString());
	sc = new Scanner(new File(filename));
	while (sc.hasNext()) {
	    String command = sc.next();
	    String[] commandData = command.split(" ");
	    switch(commandData[0]){
	    case "o":
		open(commandData[1], Integer.parseInt(commandData[2]), Integer.parseInt(commandData[3]), Integer.parseInt(commandData[2]));
	    case "c":
		close(Integer.parseInt(commandData[1]));
	    case "w":
		withdraw(Integer.parseInt(commandData[1]), Integer.parseInt(commandData[2]));
	    case "d":
		deposit(Integer.parseInt(commandData[1]), Integer.parseInt(commandData[2]));
	    case "a":
		applyInterest();
	    }
	}
	System.out.println("==========   Final Bank Data ==================\n\n" + toString());
    }

    private boolean contains(int id) {
	for (Account accs : accounts) {
	    if (accs.getID() == id) {
		return true;
	    }
	}
	return false;
    }

    public boolean open(String type, int id, int pin, int balance) {
	if (!contains(id)) {
	    Account acc = null;
	    switch(type) {
	    case "x":
		if (balance < 0) {
		    return false;
		}
                acc = new CheckingAccount(id, pin, balance);
            case "s":
		if (balance < 0) {
		    return false;
		}
		acc = new SavingAccount(id, pin, balance);
	    case "c":
		if (balance < 500) {
		    return false;
		}
		acc = new CDAccount(id, pin, balance);
	    }
	    acc.setOpen(true);
	    account.add(acc);
	} else {
	    return false;
	}
    }

    public boolean close(int id) {
	if (!contains(id)) {
	    return false;
	} else {
	    for (Account acc : accounts) {
		if (acc.getID() == id) {
		    acc.setOpen(false);
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean deposit(int id, int amount) {
	if (contains(id)) {
	    for (Account acc : accounts) {
		if (acc.getID() == id) {
		    if (acc.getOpen()) {
			acc.modBalance(amount);
			return true;
		    } else {
			return false;
		    }
		}
	    }
	}
	return false;
    }

    public void withdraw(int id, int amount) {
	if (contains(id)) {
	    for (Account acc : accounts) {
		if (acc.getID() == id) {
		    if (acc.getOpen()) {
			acc.withdraw(amount);
			return true;
		    } else {
			return false;
		    }
		}
	    }
	}
	return false;
    }

    public void applyInterest() {
	
	for (Account acc : accounts) {
	    acc.applyMonthly();
	}
    }

    public String toString(String type) {
	String data += "Account Type    Account Balance\n------------    ------- -----------\n";
	for (Account acc : accounts) {
	    data += acc.formatReceipt() + "\n";
	}
	return data + "===============================================\n";
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
	    bank.batchMode();
	}
    }

}


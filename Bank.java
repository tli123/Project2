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

    private String formatOCStatus(int d, String c, String a, double bal) {
	String meth = (c.equals("o")) ? "Open:" : "Closed:";
	if (c.equals("c")) a = " ";
        return String.format("%d    %s   %s   %s: Success   $%-10s\n", d, c, a, meth, String.format("%.2d", bal));
    }

    private String formatOCStatus(int d, String c, String a) {
	String meth = (c.equals("o")) ? "Open:" : "Closed:";
	if (c.equals("c")) a = " ";
        return String.format("%d    %s   %s   %s:Failed\n", d, c, a, meth);
    }

    private String formatWDStatus(int d, String c, String a, double bal) {
        return String.format("%d    %s       $    %.2d     $%-10s\n", d, c, a, String.format("%.2d", bal));
    }

    private String formatWDStatus(int d, String c, String a) {
	return String.format("%d    %s       $    %.2d     Failed\n", d, c, a);
    }

    public void batchMode(String filename) {
	String s = "==========   Initial Bank Data ==================\n\n" + toString();

	sc = new Scanner(new File(filename));
	while (sc.hasNext()) {
	    String command = sc.next();
	    String[] commandData = command.split(" ");
	    switch(commandData[0]){
	    case "o":
		s+=open(commandData[1], Integer.parseInt(commandData[2]), Integer.parseInt(commandData[3]), Double.parseDouble(commandData[4]));
	    case "c":
		s+=close(Integer.parseInt(commandData[1]));
	    case "w":
		s+=withdraw(Integer.parseInt(commandData[1]), Double.parseDouble(commandData[2]));
	    case "d":
		s+=deposit(Integer.parseInt(commandData[1]), Double.parseDouble(commandData[2]));
	    case "a":
		s+=applyInterest();
	    }
	}
	s+= ("==========   Final Bank Data ==================\n\n" + toString() + "===============================================\n");
	System.out.println(s);
    }

    private boolean contains(int id) {
	for (Account accs : accounts) {
	    if (accs.getID() == id) {
		return true;
	    }
	}
	return false;
    }

    public String open(String type, int id, int pin, double balance) {

	if (!contains(id)) {
	    try {
		Account acc = null;
		switch(type) {
		case "x":
		    acc = new CheckingAccount(id, pin, balance);
		case "s":
		    acc = new SavingAccount(id, pin, balance);
		case "c":
		    acc = new CDAccount(id, pin, balance);
		}
		acc.setOpen(true);
		account.add(acc);
		formatOCStatus(pin, "o", type, bal);
	    } catch (NegativeBalanceException e) {
		return formatOCStatus(pin, "o", type);
	    }
	}		
	else {
	    return formatOCStatus(pin, "o", type);
	}
    }

    public String close(int id) {
	if (!contains(id)) {
	    return formatOCStatus(pin, "c", "");
	} else {
	    for (Account acc : accounts) {
		if (acc.getID() == id) {
		    acc.setOpen(false);
		    return formatOCStatus(pin, "c", type, acc.getBalance());
		}
	    }
	}
        return formatOCStatus(pin, "c", "");
    }

    public String deposit(int id, double amount) {
	if (contains(id)) {
	    for (Account acc : accounts) {
		if (acc.getID() == id) {
		    if (acc.getOpen()) {
			acc.deposit(amount);
			return formatWDStatus(acc.getPin(), "d", amount, acc.getBalance());
		    } else {
			return formatWDStatus(acc.getPin(), "d", amount);
		    }
		}
	    }
	}
	return formatWDStatus(acc.getPin(), "d", amount);
    }

    public String withdraw(int id, double amount) {
	if (contains(id)) {
	    for (Account acc : accounts) {
		if (acc.getID() == id) {
		    if (acc.getOpen()) {
			acc.withdraw(amount);
			return formatWDStatus(acc.getPin(), "w", amount, acc.getBalance());
		    } else {
			return formatWDStatus(acc.getPin(), "w", amount);
		    }
		}
	    }
	}
	return formatWDStatus(acc.getPin(), "w", amount);
    }

    public void applyInterest() {
	String s = "============== Interest Report ==============\nAccount Adjustment      New Balance\n------- -----------     -----------\n";
	for (Account acc : accounts) {
	    String.format("%d    $%10s     $%10s\n", acc.getPin(), acc.applyMonthly(). acc.getBalace());
	}
	return s + "=============================================";
    }

    public String toString(String type) {
	String data += "Account Type    Account Balance\n------------    ------- -----------\n";
	for (Account acc : accounts) {
	    data += acc.formatReceipt() + "\n";
	}
	return data;
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


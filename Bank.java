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
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Enumeration;

public class Bank extends Observable {

    /**
     * All of the accounts that are in the bank.
     */
    private Hashtable<Integer,Account> accounts;

    /**
     * The scanner that reads the bankFile.
     */
    private Scanner sc;

    public Bank(String filename) throws NegativeBalanceException, FileNotFoundException {
	accounts = new Hashtable<Integer, Account>();
	sc = new Scanner(new File(filename));
	while (sc.hasNext()) {
	    String accountInfo = sc.next();
	    String[] accountData = accountInfo.split(" ");
	    int newId = Integer.parseInt(accountData[1]);
	    int newPin = Integer.parseInt(accountData[2]);
	    double newBal = Double.parseDouble(accountData[3]);
		
	    Account acc = null;
	    switch(accountData[0]) {
	    case "c":
		acc = new CDAccount(newId, newPin, newBal);
		break;
	    case "s":
		acc = new SavingAccount(newId, newPin, newBal);	
		break;
	    case "x":
		acc = new CheckingAccount(newId, newPin, newBal);
		break;
	    }
	    accounts.put(newId, acc);
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

    private String formatWDStatus(int d, String c, double a, double bal) {
        return String.format("%d    %s       $    %-10s     $%-10s\n", d, c,  String.format("%.2d", a), String.format("%.2d", bal));
    }

    private String formatWDStatus(int d, String c, double a) {
	return String.format("%d    %s       $    %-10s     Failed\n", d, c, String.format("%.2d", a));
    }

    public void batchMode(String filename) throws NegativeBalanceException, FileNotFoundException {
	String s = "==========   Initial Bank Data ==================\n\n" + toString();
	sc = new Scanner(new File(filename));
	while (sc.hasNext()) {
	    String command = sc.next();
	    String[] commandData = command.split(" ");
	    switch(commandData[0]) {
	    case "o":
		s+=open(commandData[1], Integer.parseInt(commandData[2]), Integer.parseInt(commandData[3]), Double.parseDouble(commandData[4]));
		break;
	    case "c":
		s+=close(Integer.parseInt(commandData[1]));
		break;
	    case "w":
		s+=withdraw(Integer.parseInt(commandData[1]), Double.parseDouble(commandData[2]));
		break;
	    case "d":
		s+=deposit(Integer.parseInt(commandData[1]), Double.parseDouble(commandData[2]));
		break;
	    case "a":
		s+=applyInterest();
		break;
	    }
	}
	s+= ("==========   Final Bank Data ==================\n\n" + toString() + "===============================================\n");
	System.out.println(s);
    }

    public String open(String type, int id, int pin, double balance) {
	if (!accounts.containsKey(id)) {
	    try {
		Account acc;
		switch(type) {
		case "x":
		    acc = new CheckingAccount(id, pin, balance);
		    break;
		case "s":
		    acc = new SavingAccount(id, pin, balance);
		    break;
		case "c":
		    acc = new CDAccount(id, pin, balance);
		    break;
		default:
		    throw new InvalidAccountTypeException();
		}
		acc.setOpen(true);
		accounts.put(id, acc);
		return formatOCStatus(pin, "o", type, balance);
	    } catch (InvalidAccountTypeException e) {
		return formatOCStatus(id, "o", type);
	    } catch (NegativeBalanceException e) {
		return formatOCStatus(id, "o", type);
	    }
	}
	return formatOCStatus(id, "o", type, balance);
    }

    public String close(int id) {
	Account acc = accounts.get(id);
	if (acc != null && acc.getOpen()) {
	    acc.setOpen(false);
	    return formatOCStatus(id, "c", "", acc.getBalance());
	}
	return formatOCStatus(id, "c", "");
    }

    public String deposit(int id, double amount) throws NegativeBalanceException {
	Account acc = accounts.get(id);
	if (acc != null && acc.getOpen()) {
		acc.deposit(amount);
		return formatWDStatus(id, "d", amount, acc.getBalance());
	}
	return formatWDStatus(id, "d", amount);
    }

    public String withdraw(int id, double amount) throws NegativeBalanceException {
	Account acc = accounts.get(id);
	if (acc != null && acc.getOpen()) {
	    acc.withdraw(amount);
	    return formatWDStatus(id, "w", amount, acc.getBalance());
	}
	return formatWDStatus(id, "w", amount);
    }

    public String applyInterest() throws NegativeBalanceException {
	String s = "============== Interest Report ==============\nAccount Adjustment      New Balance\n------- -----------     -----------\n";
        Enumeration<Account> e = accounts.elements();
	while(e.hasMoreElements()) {
	    Account acc = e.nextElement();
	    s += String.format("%d    $%10s     $%10s\n", acc.getID(), acc.applyMonthly(), acc.getBalance());
	}
	return s += "=============================================\n\n";
    }

    public String toString() {
	String data = "Account Type    Account Balance\n------------    ------- -----------\n";
        Enumeration<Account> e = accounts.elements();
	while(e.hasMoreElements()) {
	    Account acc = e.nextElement();
	    data += acc.formatReceipt() + "\n";
	}
	return data;
    }


    public static void main(String[] args) throws NegativeBalanceException, FileNotFoundException {
	if (args.length < 1 || args.length > 2) {
	    System.out.println("Usage: java Bank bankFile [batchFile]");
	    System.exit(0);
	} 
        Bank bank = new Bank(args[0]);
	if (args.length == 1) {
	    //open gui
	} else if (args.length == 2) {
	    bank.batchMode(args[1]);
	}
    }

}


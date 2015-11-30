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
 * @author Ye Ziwei
 * @author Tommy Li
 */

import java.util.Observable;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.lang.String;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Arrays;

public class Bank extends Observable {

    /**
     * All of the accounts that are in the bank.
     */
    private Hashtable<Integer,Account> accounts;

    /**
     * The scanner that reads the bankFile.
     */
    private Scanner sc;
    private String filename;
    private String string_data;

    public Bank(String filename) throws NegativeBalanceException, FileNotFoundException {
	string_data = "";
	accounts = new Hashtable<Integer, Account>();
	this.filename = filename;

	sc = new Scanner(new File(filename));
	while (sc.hasNextLine()) {
	    String accountInfo = sc.nextLine();
	    String[] accountData = accountInfo.split(" ");
	    System.out.println(Arrays.toString(accountData));
	    int newId = Integer.parseInt(accountData[1]);
	    int newPin = Integer.parseInt(accountData[2]);
	    double newBal = Double.parseDouble(accountData[3]);
	    
	    Account acc = null;
	    switch(accountData[0]) {
	    case "x":
		acc = new CDAccount(newId, newPin, newBal);
		break;
	    case "s":
		acc = new SavingAccount(newId, newPin, newBal);	
		break;
	    case "c":
		acc = new CheckingAccount(newId, newPin, newBal);
		break;
	    }
	    accounts.put(newId, acc);
	}
    }

    private void formatOCStatus(int d, String c, String a, double bal) {
	String meth = (c.equals("o")) ? "Open:" : "Closed:";
	if (c.equals("c")) a = " ";
        string_data += String.format("%d    %s   %s   %s Success   $%10s\n", d, c, a, meth, String.format("%.2f", bal));
    }

    private void formatOCStatus(int d, String c, String a) {
	String meth = (c.equals("o")) ? "Open:" : "Closed:";
	if (c.equals("c")) a = " ";
        string_data += String.format("%d    %s   %s   %s Failed\n", d, c, a, meth);
    }

    private void formatWDStatus(int d, String c, double a, double bal) {
        string_data += String.format("%d    %s       $%10s     $%10s\n", d, c,  String.format("%.2f", a), String.format("%.2f", bal));
    }

    private void formatWDStatus(int d, String c, double a) {
        string_data += String.format("%d    %s       $%10s     Failed\n", d, c, String.format("%.2f", a));
    }

    public void batchMode(String filename) throws NegativeBalanceException, FileNotFoundException {
        string_data = "==========   Initial Bank Data ==================\n\n" + toString() +  "\n===============================================\n";
	sc = new Scanner(new File(filename));
	while (sc.hasNextLine()) {
	    String command = sc.nextLine();
	    String[] commandData = command.split(" ");
	    switch(commandData[0]) {
	    case "o":
		open(commandData[1], Integer.parseInt(commandData[2]), Integer.parseInt(commandData[3]), Double.parseDouble(commandData[4]));
		break;
	    case "c":
		close(Integer.parseInt(commandData[1]));
		break;
	    case "w":
		withdraw(Integer.parseInt(commandData[1]), Double.parseDouble(commandData[2]));
		break;
	    case "d":
		deposit(Integer.parseInt(commandData[1]), Double.parseDouble(commandData[2]));
		break;
	    case "a":
		applyInterest();
		break;
	    }
	}
	string_data += ("\n==========   Final Bank Data ==================\n\n" + toString() + "\n===============================================\n");
	System.out.println(string_data);
    }

    public boolean open(String type, int id, int pin, double balance) {
        if ((id < 1000 || id > 9999) && (pin < 1000 || pin > 9999)) {
	}
	if (!idExists(id)) {
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
		accounts.put(id, acc);
	        formatOCStatus(pin, "o", type, balance);
		return true;
	    } catch (InvalidAccountTypeException e) {
	        formatOCStatus(id, "o", type);
		return false;
	    } catch (NegativeBalanceException e) {
	        formatOCStatus(id, "o", type);
		return false;
	    }
	}
	formatOCStatus(id, "o", type);
	return false;
    }

    public boolean close(int id) {
	Account acc = accounts.get(id);
	if (acc != null) {
	    accounts.remove(id);
	    formatOCStatus(id, "c", "", acc.getBalance());
	    return true;
	}
        formatOCStatus(id, "c", "");
	return false;
    }

    public boolean deposit(int id, double amount) throws NegativeBalanceException {
	Account acc = accounts.get(id);
	if (acc != null) {
		acc.deposit(amount);
	        formatWDStatus(id, "d", amount, acc.getBalance());
		return true;
	}
        formatWDStatus(id, "d", amount);
	return false;
    }

    public boolean withdraw(int id, double amount) throws NegativeBalanceException {
	Account acc = accounts.get(id);
	if (acc != null) {
	    acc.withdraw(amount);
	    formatWDStatus(id, "w", amount, acc.getBalance());
	    return true;
	}
        formatWDStatus(id, "w", amount);
	return false;
    }

    public void applyInterest() throws NegativeBalanceException {
	String s = "============== Interest Report ==============\nAccount Adjustment      New Balance\n------- -----------     -----------\n";
        Enumeration<Account> e = accounts.elements();
	while(e.hasMoreElements()) {
	    Account acc = e.nextElement();
	    s += String.format("%d    $%10s     $%10s\n", acc.getID(), String.format("%.2f", acc.applyMonthly()), String.format("%.2f", acc.getBalance()));
	}
        string_data += (s + "=============================================\n\n");
    }

    public int getBalance(int id) {
	return accounts.get(id).getBalance();
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

    public String formatFile() {
	String s = "";
	Enumeration<Account> e = accounts.elements();
	while(e.hasMoreElements()) {
	    s += e.nextElement().toString();
	}
	return s;
    }

    public void write() throws FileNotFoundException, UnsupportedEncodingException {
	PrintWriter w = new PrintWriter(filename, "UTF-8");
	w.print(formatFile());
	w.close();
    }

    public boolean idExists(int id) {
	return accounts.containsKey(id);
    }

    public boolean pinVerify(int id, int pin) {
	return accounts.get(id).getPin() == pin;
    }

    public static void main(String[] args) throws NegativeBalanceException, FileNotFoundException, UnsupportedEncodingException {
	if (args.length < 1 || args.length > 2) {
	    System.out.println("Usage: java Bank bankFile [batchFile]");
	    System.exit(0);
	} 
        Bank bank = new Bank(args[0]);
	if (args.length == 1) {
	    bank.write();
	} else if (args.length == 2) {
	    bank.batchMode(args[1]);
	    bank.write();
	}
    }

}


/**
 * ATM.java
 *
 * Holds the ATM Model that communicates with both the Bank model and
 * the ATM GUI.
 *
 * File:
 *	$Id: ATM.java,v 1.0 2015/11/xx 00:00:00 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: ATM.java,v $
 *	Initial revision
 *
 */

/**
 * The ATM Model.
 *
 * @author Tommy Li
 * @author Ziwei Ye
 */
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.Observer;

public class ATM extends Observable {

    private Bank model;
    private int id = 0;

    //  private Account acc;

    public ATM(Bank model) {
        this.model = model;
    }

    public void setID(int id) {
	this.id = id;
    }

    public boolean deposit(double amount) { 
	if (model.deposit(id, amount)) {
	    setChanged();
	    notifyObservers();
	    return true;
	} else {
	    return false;
	}
    }

    public boolean withdraw(double amount) {
	if (model.withdraw(id, amount)) {
	    setChanged();
	    notifyObservers();
	    return true;
	} else {
	    return false;
	}
    }
    
    public void write() {
	try {
	    model.write();
	} catch (FileNotFoundException e) {}
	catch (UnsupportedEncodingException e) {}
    }
		

    public double getBalance() {
        return model.getBalance(id);
    }

    public boolean idExists() {
	if (model.idExists(id)) {
	    setChanged();
	    notifyObservers();
	    return true;
	} else {
	    return false;
	}
    }

    public boolean pinVerify(int pin) {
	if (model.pinVerify(id, pin)) {
	    setChanged();
	    notifyObservers();
	    return true;
	} else {
	    return false;
	}
    }
}

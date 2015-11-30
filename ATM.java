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

public class ATM extends Observable {

    //Ziwei - you need tointeract with the bank model, not the acc.
    //We also need a way to pass booleans between bank+atm, maybe we can have
    //an instance varaible in bank that stores the bank data crap and have the batch mode [pprint that at the end
    //so we can use the functions to pass booleans

    private Bank model;
    private int id;

    //  private Account acc;

    public ATM(Bank model, id id) {
        this.model = model;
	this.id = id;
	model.addObserver(this);
    }

    public void deposit(double amount) throws NegativeBalanceException { 
	while (true) {
	    if (model.deposit(id, amount)) {
		setChanged();
		notifyObservers();
		break;
	    } else {
		//enter fail message
	    }
	}
    }

    public void withdraw(double amount) throws NegativeBalanceException {
	while (true) {
	    if (model.withdraw(id, amount)) {
		setChanged();
		notifyObservers();
		break;
	    } else {
		//enter fail message
	    }
	}
    }

    public void getBalance() {
        model.getBalance(id);
    }

    public boolean idExists(int id) {
	while (true) {
	    if (model.idExists(id)) {
		setChanged();
		motifyObservers();
		break;
	    } else {

	    }
    }

    public boolean pinVerify(int id, int pin) {
	while(true) {
	    if (model.pinVerify(id, pin)) {
		setChanged();
		motifyObservers();
		break;
	    } else {
		//
	    }
	}
    }
}

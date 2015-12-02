/**
 * ATM.java
 *
 * The model the ATM GUI uses to do its work.
 *
 * File:
 *	$Id: ATM.java,v 1.0 2015/12/02 12:41:13 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: ATM.java,v $
 *	Initial revision
 *
 */

/**
 * The ATM class.
 *
 * @author Ziwei Ye
 * @author Tommy Li
 */

import java.util.Observable;
import java.util.Observer;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ATM extends Observable {

    /**
     * The ID that will be logged in.
     */
    private int ID;

    /**
     * The pin of the user logged in.
     */
    private int pin;

    /**
     * The bank model.
     */
    private Bank model;

    /**
     * The status of the ATM.
     */
    private String status;

    /**
     * Boolean that tells the ATM whether user input is needed.
     */
    private boolean editable;

    /**
     * Counter that tracks the state of the ATM.
     * 0: Account login
     * 1: PIN
     * 2: Pre-transaction
     * 3: Transaction
     * 4: Deposit
     * 5: Withdraw
     * 6: Logout
     * 7: Post Logout
     */
    private int counter;

    /**
     * Constucts an ATM Object.
     * @param model - The Bank model used by the ATM.
     */
    public ATM(Bank model) {
	this.model = model;
	ID = 0;
	pin = 0;
	counter = 0;
	editable = true;
	status = "Welcome to ACME! Please enter your account ID.";
    }

    /**
     * Sets the ID of the ATM.
     * @param ID - The ID this ATM is to be set to.
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Sets the pin of the ATM.
     * @param pin - The pin this ATM will be set to.
     */
    public void setPin(int pin) {
	this.pin = pin;
    }

    /**
     * Gets the status of the ATM.
     * @return The status prompt of the ATM.
     */
    public String getStatus() {
	return status;
    }

    /**
     * Gets the number associated with the state.
     * @return The number that is associated with the state.
     */
    public int getCounter() {
	return counter;
    }

    /**
     * Gets the boolean that checks if the user input is needed.
     * @return The editable boolean.
     */
    public boolean getEdit() {
	return editable;
    }

    /**
     * Checks if the ID Exists.
     */
    public void idExists() {
	if (model.idExists(ID)) {
	    status = "Account found. Please enter your pin.";
	    counter = 1;
	} else {
	    status = "Account not found. Please try again.";
	}
	editable = true;
	setChanged();
	notifyObservers();
    }

    /**
     * Checks if the pin matches the ID.
     */
    public void pinVerify() {
	if (model.pinVerify(ID, pin)) {
	    status = "Login successful. Press OK to go to the transaction menu.";
	    counter = 2;
	    editable = false;
	} else {
	    status = "Login failed. Please enter account information.";
	    counter = 0;
	    editable = true;
	}
	setChanged();
	notifyObservers();
    }

    /**
     * Displays the transcation menu.
     */
    public void preTransaction() {
	status = "Select one: 1: Balance Inquiry; 2: Deposit; 3: Withdraw; 4: Logout.";
	counter = 3;
	editable = true;
	setChanged();
	notifyObservers();
    }

    /**
     * Directs the prompt inputted by the user to the appropriate
     * function.
     * @param input: The user input.
     */
    public void transaction(int input) {
	switch(input) {
	case(1):
	    getBalance();
	    break;
	case(2):
	    preDeposit();
	    break;
	case(3):
	    preWithdraw();
	    break;
	case(4):
	    preLogout();
	    break;
	default:
	    invalid();
	}
    }

    /**
     * Gets the balance of the user.
     */
    public void getBalance() {
	status = "Balance is " + model.getBalance(ID) + ". Press OK to continue.";
	counter = 2;
	editable = false;
	setChanged();
	notifyObservers();
    }

    /**
     * Asks the user for deposit value.
     */
    public void preDeposit() {
	status = "How much would you like to deposit?";
	counter = 4;
	editable = true;
	setChanged();
	notifyObservers();
    }

    /**
     * Deposits the amount specified by user.
     * @param amount - The amount to be deposited.
     */
    public void deposit(double amount) {
	model.deposit(ID, amount);
	status = "Deposit successful. Press OK to continue.";
	counter = 2;
	editable = false;
	setChanged();
	notifyObservers();
    }

    /**
     * Asks the user for withdraw value.
     */
    public void preWithdraw() {
	status = "How much would you like to withdraw?";
	counter = 5;
	editable = true;
	setChanged();
	notifyObservers();
    }

    /**
     * Withdraws from the ATM.
     * @param amount - The amount to withdraw.
     */
    public void withdraw(double amount) {
	if (model.withdraw(ID, amount)) {
	    status = "Withdraw successful. Press OK to continue.";
	} else {
	    status = "Withdraw failed. Press OK to continue";
	}
	counter = 2;
	editable = false;
	setChanged();
	notifyObservers();
    }

    /**
     * Confirms the logout from user.
     */
    public void preLogout() {
	status = "Would you like to logout?";
	counter = 6;
	editable = false;
	setChanged();
	notifyObservers();
    }

    /**
     * Logs out the user and writes all data into file.
     */
    public void logout() throws FileNotFoundException, UnsupportedEncodingException {
	ID = 0;
	pin = 0;
	status = "Logout successful. Press OK to continue.";
	counter = 7;
	model.write();
	editable = false;
	setChanged();
	notifyObservers();
    }

    /**
     * Directs user to the welcome screen after logging out.
     */
    public void postLogout() {
	status = "Welcome to ACME! Please enter your account ID.";
	counter = 0;
	editable = true;
	setChanged();
	notifyObservers();
    }

    /**
     * Allows the user to cancel an action.
     */
    public void cancel() {
	if (counter == 0 || counter == 1) {
	    counter = 0;
	    status = "Welcome to ACME! Please enter your account ID.";
	} else if (counter != 7) {
	    counter = 3;
	    status = "Select one: 1: Balance Inquiry; 2: Deposit; 3: Withdraw; 4: Logout.";
	}
	editable = true;
	setChanged();
	notifyObservers();
    }

    /**
     * Updates the numbers in the ATM GUI.
     */
    public void updateNumbers() {
	setChanged();
	notifyObservers();
    }

    /**
     * Called when the user inputs something invalid.
     */
    public void invalid() {
	status = "Invalid input. Press OK to continue.";
        counter = 8;
	editable = false;
	setChanged();
	notifyObservers();
    }
}

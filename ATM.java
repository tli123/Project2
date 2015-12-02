import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class ATM extends Observable {

    private int ID;

    private int pin;

    private Bank model;

    private String status;

    private int counter;

    public ATM(Bank model) {
	this.model = model;
	ID = 0;
	pin = 0;
	counter = 0;
	status = "Welcome to ACME! Please enter your account ID.";
    }

    public int getID() {
	return ID;
    }

    public void setID(int ID) {
	this.ID = ID;
    }

    public int getPin() {
	return pin;
    }

    public void setPin(int pin) {
	this.pin = pin;
    }

    public String getStatus() {
	return status;
    }

    public int getCounter() {
	return counter;
    }

    public void idExists() {
	if (model.idExists(ID)) {
	    status = "Account found. Please enter your pin.";
	    counter++;
	} else {
	    status = "Account not found. Please try again.";
	}
	setChanged();
	notifyObservers();
    }

    public void pinVerify() {
	if (model.pinVerify(ID, pin)) {
	    status = "Login successful. Press OK to see balance information.";
	    counter++;
	} else {
	    status = "Login failed. Please enter account information.";
	    counter = 0;
	}
	setChanged();
	notifyObservers();
    }

    public void getBalance() {
	status = "Balance is " + model.getBalance(ID) + ". Press OK to continue.";
	counter++;
	setChanged();
	notifyObservers();
    }

    public void preDeposit() {
	status = "How much would you like to deposit?";
	counter++;
	setChanged();
	notifyObservers();
    }

    public void deposit(double amount) {
	model.deposit(ID, amount);
	status = "Deposit successful. Balance is " + model.getBalance(ID) + ". Press OK to continue.";
	counter++;
	setChanged();
	notifyObservers();
    }

    public void preWithdraw() {
	status = "How much would you like to withdraw?";
	counter++;
	setChanged();
	notifyObservers();
    }

    public void withdraw(double amount) {
	if (model.withdraw(ID, amount)) {
	    status = "Withdraw successful. Balance is " + model.getBalance(ID) + ". Press OK to continue.";
	    counter++;
	} else {
	    status = "Withdraw failed. Press OK to begin withdrawing again.";
	    counter--;
	}
	setChanged();
	notifyObservers();
    }

    public void preLogout() {
	status = "Would you like to logout?";
	counter++;
	setChanged();
	notifyObservers();
    }

    public void logout() throws FileNotFoundException, UnsupportedEncodingException {
	ID = 0;
	pin = 0;
	status = "Logout successful. Press OK to continue.";
	counter++;
	model.write();
	setChanged();
	notifyObservers();
    }

    public void postLogout() {
	status = "Welcome to ACME! Please enter your account ID.";
	counter = 0;
	setChanged();
	notifyObservers();
    }

    public void cancel() {
	if (counter == 0 || counter == 1) {
	    counter = 0;
	    status = "Welcome to ACME! Please enter your account ID.";
	}
	setChanged();
	notifyObservers();
    }

    public void updateNumbers() {
	setChanged();
	notifyObservers();
    }

}

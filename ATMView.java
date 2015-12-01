/**
 * ATMView.java
 *
 * Holds the ATM GUI. Communicates with the ATM Model.
 *
 * File:
 *	$Id: ATMView.java,v 1.0 2015/11/xx 00:00:00 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: ATMView.java,v $
 *	Initial revision
 *
 */

/**
 * The ATM GUI View Control.
 *
 * @author Tommy Li
 * @author Ziwei Ye
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.String;
import java.util.Observer;

public class ATMView extends JFrame implements Observer {

    /**
     * The JLabel that holds the prompt of the user.
     */
    private JLabel prompt;

    /**
     * The prompt that is echoed to the user.
     */
    private String promptEcho;

    /**
     * The JLabel that holds the numbers the user presses are displayed.
     */
    private JLabel numbers;

    /**
     * The numbers that are echoed to the user.
     */
    private String number;

    /**
     * The ID that the user will input later.
     */
    private int ID = 0;

    /**
     * The String form of the pin.
     */
    private String pin = "";

    /**
     * The counter that matches the correct prompt for the user.
     * States:
     * 0: Account input
     * 1: Pin input
     * 2: Balance Inquiry
     * 3: Deposit
     * 4: Successful Deposit
     * 5: Withdraw Attempt
     * 6: Withdraw Failure
     * 7: Withdraw Success
     * 8: Logout
     */
    private int counter = 0;

    /**
     * The ATM Model.
     */
    private ATM model;

    /**
     * The constructor that will initialize the components.
     */
    public ATMView(ATM model) {
	this.model = model;
	promptEcho = "Welcome to ACME! Please input your account number.";
	number = " ";
	model.addObserver(this);
	initComponents();
    }

    /**
     * Initializes the components.
     */
    public void initComponents() {
	this.getContentPane().setLayout(new BorderLayout());
	JPanel top = new JPanel();
	top.setLayout(new BorderLayout());
	numbers = new JLabel(number);
	numbers.setHorizontalAlignment(SwingConstants.RIGHT);
	prompt = new JLabel(promptEcho);
	prompt.setHorizontalAlignment(SwingConstants.LEFT);
	top.add(numbers, BorderLayout.SOUTH);
	top.add(prompt, BorderLayout.NORTH);
	this.add(top, BorderLayout.NORTH);

	JPanel numberButtons = new JPanel();
	numberButtons.setLayout(new GridLayout(4, 3));
	JButton button;
	ButtonListener buttonListener = new ButtonListener();
	for (int i = 9; i >= 0; i--) {
	    button = new JButton(i + "");
	    button.addActionListener(buttonListener);
	    numberButtons.add(button);
	}
	JPanel otherButtons = new JPanel();
	otherButtons.setLayout(new GridLayout(4, 1));
	String[] buttons = new String[]{"OK", "Cancel", "Clear", "Close"};
	for (int i = 0; i < buttons.length; i++) {
	    button = new JButton(buttons[i]);
	    button.addActionListener(buttonListener);
	    otherButtons.add(button);
	}
	this.add(numberButtons);
	this.add(otherButtons, BorderLayout.EAST);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocation(100, 100);
	this.setVisible(true);
	this.setSize(500, 400);
    }

    /**
     * Button Listener that controls the buttons in the frame.
     */
    private class ButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("OK")) {
		int input;
		if (counter == 1) {
		    input = Integer.parseInt(pin);
		} else {
		    input = Integer.parseInt(numbers.getText().substring(1, numbers.getText().length()));
		}
		number = " ";
		switch(counter) {
	        case(0):
		    ID = input;
		    if (model.verifyID(ID)) {
			counter++;
			promptEcho = "Account found. Please enter your pin.";
		    } else {
			promptEcho = "Account not found. Please try again.";
		    }
		    break;
		case(1):
		    if (model.verifyPin(input)) {
			promptEcho = "Login Successful. Displaying Balance.";
			model.getBalance();
			counter++;
		    } else {
			promptEcho = "Login failed. Please try again.";
			counter = 0;
		    }
		    break;
		case(2):
		    promptEcho = "How much would you like to deposit?";
		    counter++;
		    break;
		case(3):
		    model.deposit(input);
		    promptEcho = "Deposit successful.";
		    counter++;
		    break;
                case(4):
		    promptEcho = "How much would you like to withdraw?";
		    counter++;
		    break;
                case(5):
		    if (model.withdraw(input)) {
			counter += 2;
			promptEcho = "Withdrawal successful";
		    } else {
			counter++;
			promptEcho = "Withdrawal failed";
		    }
		    break;
		case(6):
		    promptEcho = "Logout?";
		    counter += 2;
		    break;
		case(7):
		    promptEcho = "Logout?";
		    counter += 1;
		    break;
		case(8):
		    promptEcho = "Welcome to ACME! Please enter your Account ID.";
		    counter = 0;
		    ID = 0;
		    pin = null;
		}
		number = " ";
		validate();
	    } else if (e.getActionCommand.equals("Cancel")) {
		switch(counter) {
		case(1):
		    counter = 0;
		    break;
		case(3):
		    counter = 3;
		    break;
		case(4):
		    counter = 4;
		    break;
		}
                number = " ";
		validate();
	    } else if (e.getActionCommand.equals("Clear")) {
		numbers = " ";
		validate();
	    } else if (e.getActionCommand.equals("Close")) {
		
	    } else {
		if (counter == 1) {
		    number = numbers.getText() + "*";
		    pin += e.getActionCommand();
		} else if (counter != 3 && counter != 6) {
		    number = numbers.getText() + e.getActionCommand();
		}
		validate();
	    }
	}
    }
    
    public void update(Observable t, Object o) {
	numbers.setText(number);
	prompt.setText(promptEcho);
	validate();
    }

    public static void main(String[] args) {
	ATMView atm = new ATMView();
    }

}

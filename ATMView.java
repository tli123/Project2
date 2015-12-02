/**
 * ATMView.java
 *
 * Holds the ATM GUI. Communicates with the ATM Model.
 *
 * File:
 *      $Id: ATMView.java,v 1.0 2015/11/xx 00:00:00 csci140 Exp csci140 $
 *
 * Revisions:
 *      $Log: ATMView.java,v $
 *      Initial revision
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
import java.util.Observable;

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
    private enum Gui_states {
	ACCOUNT_INPUT, 
	PIN_INPUT, 
	BALANCE_INQUIRY,
	DEPOSIT,
	SUCCESSFUL_DEPOSIT,
	WITHDRAW_ATTEMPT,
	WITHDRAW_FAILURE,
	WITHDRAW_SUCCESS,
	LOGOUT
    }
    private Gui_states gui_states = Gui_states.ACCOUNT_INPUT;

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
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		int input = 0;;
		if (gui_states == Gui_states.PIN_INPUT) {
		    input = Integer.parseInt(pin);
		} else if (!numbers.getText().equals(" ")){
		    input = Integer.parseInt(numbers.getText().substring(1, numbers.getText().length()));
		}
		number = " ";
		switch(gui_states) {
	        case ACCOUNT_INPUT:
		    ID = input;
		    model.setID(ID);
		    if (model.idExists()) {
			gui_states = Gui_states.PIN_INPUT;
			promptEcho = "Account found. Please enter your pin.";
		    } else {
			promptEcho = "Account not found. Please try again.";
		    }
		    break;
		case PIN_INPUT:
		    if (model.pinVerify(input)) {
			promptEcho = "Login Successful. Displaying Balance: " + model.getBalance();
			gui_states = Gui_states.BALANCE_INQUIRY;
		    } else {
			promptEcho = "Login failed. Please try again.";
			pin = "";
			gui_states = Gui_states.ACCOUNT_INPUT;
		    }
		    break;
		case BALANCE_INQUIRY:
		    promptEcho = "How much would you like to deposit?";
		    gui_states = Gui_states.DEPOSIT;
		    break;
		case DEPOSIT:
		    model.deposit((double)input);
		    promptEcho = "Deposit successful.";
		    gui_states = Gui_states.SUCCESSFUL_DEPOSIT;
		    break;
                case SUCCESSFUL_DEPOSIT:
		    promptEcho = "How much would you like to withdraw?";
		    gui_states = Gui_states.WITHDRAW_ATTEMPT;
		    break;
                case WITHDRAW_ATTEMPT:
		    if (model.withdraw((double)input)) {
			gui_states = Gui_states.WITHDRAW_SUCCESS;
			promptEcho = "Withdrawal successful";
		    } else {
			gui_states = Gui_states.WITHDRAW_FAILURE;
			promptEcho = "Withdrawal failed";
		    }
		    break;
		case WITHDRAW_FAILURE:
		    promptEcho = "How much would you like to withdraw?";
		    gui_states = Gui_states.WITHDRAW_ATTEMPT;
		    break;
		case WITHDRAW_SUCCESS:
		    promptEcho = "Logout?";
		    gui_states = Gui_states.LOGOUT;
		    break;
		case LOGOUT:
		    promptEcho = "Welcome to ACME! Please enter your Account ID.";
		    gui_states = Gui_states.ACCOUNT_INPUT;
		    ID = 0;
		    pin = "";
		    model.write();
		    break;
		}
		prompt.setText(promptEcho);
		numbers.setText(number);
		validate();
	    } else if (e.getActionCommand().equals("Cancel")) {
		switch(gui_states) {
		case PIN_INPUT:
		case ACCOUNT_INPUT:
		    promptEcho = "Welcome to ACME! Please enter your account number.";
		    gui_states = Gui_states.ACCOUNT_INPUT;
		    break;
		case DEPOSIT:
		    gui_states = Gui_states.DEPOSIT;
		    break;
		case WITHDRAW_ATTEMPT:
		    gui_states = Gui_states.WITHDRAW_ATTEMPT;
		    break;
		}
                number = " ";
		numbers.setText(number);
		prompt.setText(promptEcho);
		validate();
	    } else if (e.getActionCommand().equals("Clear")) {
		number = " ";
		numbers.setText(number);
		validate();
	    } else if (e.getActionCommand().equals("Close")) {
		
	    } else {
		if (gui_states == Gui_states.PIN_INPUT) {
		    number = numbers.getText() + "*";
		    pin += e.getActionCommand();
		    numbers.setText(number);
		} else if (gui_states != Gui_states.SUCCESSFUL_DEPOSIT && gui_states != Gui_states.WITHDRAW_FAILURE && gui_states != Gui_states.WITHDRAW_SUCCESS && gui_states != Gui_states.LOGOUT) {
		    number = numbers.getText() + e.getActionCommand();
		    numbers.setText(number);
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
}

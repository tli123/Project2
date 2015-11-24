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

public class ATMView extends JFrame {

    private JLabel numbers;

    private String number;

    public ATMView() {
	initComponents();
    }

    public void initComponents() {
	number = " ";
	this.getContentPane().setLayout(new BorderLayout());
	JPanel top = new JPanel();
	top.setLayout(new FlowLayout(FlowLayout.RIGHT));
	numbers = new JLabel();
	numbers.setHorizontalAlignment(SwingConstants.RIGHT);
	top.add(numbers);
	this.add(top, BorderLayout.NORTH);
	JPanel buttons1 = new JPanel();
	buttons1.setLayout(new GridLayout(4, 3));
	JButton button;
	ButtonListener buttonListener = new ButtonListener();
	for (int i = 9; i >= 0; i--) {
	    button = new JButton(i + "");
	    button.addActionListener(buttonListener);
	    buttons1.add(button);
	}
	button = new JButton("OK");
	button.addActionListener(buttonListener);
	buttons1.add(button);
	JPanel buttons2 = new JPanel();
	buttons2.setLayout(new GridLayout(3, 1));
	String[] buttonsToAdd = new String[]{"Cancel", "Clear", "Close"};
	for (int i = 0; i < buttonsToAdd.length; i++) {
	    button = new JButton(buttonsToAdd[i]);
	    button.addActionListener(buttonListener);
	    buttons2.add(button);
	}
        this.add(buttons1);
	this.add(buttons2, BorderLayout.EAST);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocation(100, 100);
	this.setVisible(true);
	this.setSize(500, 400);
    }

    private class ButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("OK")) {

	    } else if (e.getActionCommand().equals("Cancel")) {
		number = number.length() == 0 || number.length() == 1 ? " " : number.substring(0, number.length() - 1);
		numbers.setText(number);
		validate();
	    } else if (e.getActionCommand().equals("Clear")) {
		number = " ";
		numbers.setText(number);
		validate();
	    } else if (e.getActionCommand().equals("Close")) {

	    } else {
		number = number + e.getActionCommand();
		numbers.setText(number);
		validate();
	    }
	}
    }

    public static void main(String[] args) {
	ATMView atm = new ATMView();
    }

}

/**
 * BankView.java
 *
 * Holds the Bank GUI View that communicates with the Bank model.
 *
 * File:
 *	$Id: BankView.java,v 1.0 2015/12/02 17:23:14 csci140 Exp csci140 $
 *
 * Revisions:
 *	$Log: BankView.java,v $
 *	Initial revision
 *
 */

/**
 * The Bank GUI view and control.
 *
 * @author Tommy Li
 * @author Ziwei Ye  
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Observer;
import java.util.Observable;

public class BankView extends JFrame implements Observer {
    
    /**
     * The Bank model.
     */
    private Bank model;

    /**
     * The JTable that holds the account information in the bank.
     */
    private JTable bankInfo;

    /**
     * Holds the data for the information in the Bank.
     */
    String[] col_names;

    /**
     * Creates the Bank GUI.
     */
    public BankView(Bank model) {
	this.model = model;
	model.addObserver(this);
	this.getContentPane().setLayout(new BorderLayout());
	JPanel middle = new JPanel();
	middle.setLayout(new GridLayout(1,1));
        col_names = new String[]{"Type", "ID", "Balance"};
	bankInfo = new JTable(model.getCurrentStatus(), col_names);
        bankInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	middle.add(bankInfo);
	this.add(middle);
	JPanel bottom = new JPanel();
	bottom.setLayout(new FlowLayout());
	ButtonListener buttonListener = new ButtonListener();
	JButton button = new JButton("Launch ATM");
	button.addActionListener(buttonListener);
	bottom.add(button);
	button = new JButton("Update");
	button.addActionListener(buttonListener);
	bottom.add(button);
	this.add(bottom, BorderLayout.SOUTH);
	this.setSize(750, 600);
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLocation(50, 50);
    }

    /**
     * Private class that acts as the action listener for the buttons.
     */
    private class ButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("Launch ATM")) {
		ATMView atm = new ATMView(new ATM(model));
	    } else if (e.getActionCommand().equals("Update")) {
		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(1,1));
		bankInfo = new JTable(model.getCurrentStatus(), col_names);
		bankInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		middle.add(bankInfo);
	        add(middle);
		validate();
	    }
	}
    }

    /**
     * Update the window when the model indicates an update is
     * required. Updates the information displayed on the GUI.
     * @param t - An Observable -- not used.
     * @param o - An Object -- not used.
     */
    public void update(Observable o, Object t) {
	JPanel middle = new JPanel();
	middle.setLayout(new GridLayout(1,1));
	bankInfo = new JTable(model.getCurrentStatus(), col_names);
	bankInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	middle.add(bankInfo);
        add(middle);
       	validate();
    }

}

    

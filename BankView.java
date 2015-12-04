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
import java.util.Arrays;

public class BankView extends JFrame implements Observer {
    
    /**
     * The Bank model.
     */
    private Bank model;

    private JScrollPane scrollPane;

    /**
     * The JTable that holds the account information in the bank.
     */
    private JTable bankInfo;

    /**
     * JPanel that holds the data.
     */
    private JPanel middle;

    /**
     * Holds the data for the information in the Bank.
     */
    String[] col_names;

    /**
     * The ID number for the ATMs opened.
     */
    private int ATM_ID = 0;

    /**
     * The data for the JTable
     */
    private Object[][] data;

    /**
     * Creates the Bank GUI.
     */
    public BankView(Bank model) {
	super("Tommy Li: txl2747 | Ziwei Ye: zxy1677");
	this.model = model;
	model.addObserver(this);
	this.getContentPane().setLayout(new BorderLayout());
        middle = new JPanel();
	middle.setLayout(new BorderLayout());
        col_names = new String[]{"Type", "ID", "Balance"};
	data = model.getCurrentStatus();
	bankInfo = new JTable(data, col_names);
	scrollPane = new JScrollPane();
	scrollPane.setViewportView(bankInfo);
	bankInfo.setEnabled(false);
        bankInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	middle.add(scrollPane);
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
	CloseListener windowListener = new CloseListener();
	this.addWindowListener(windowListener);
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
		ATMView atm = new ATMView(new ATM(model, ATM_ID));
		ATM_ID++;
	    } else if (e.getActionCommand().equals("Update")) {
	        data = model.getCurrentStatus();
		model.updateTable();
	    }
	}
    }

    /**
     * Private class that prints out the final bank data when gui closes.
     */
    private class CloseListener implements WindowListener {
	public void windowDeactivated(WindowEvent e) {}

	public void windowActivated(WindowEvent e) { }

	public void windowDeiconified(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowClosed(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {
	    System.out.println("\n==========   Final Bank Data ==================\n\n" + model.toString() + "\n===============================================\n");
	}

	public void windowOpened(WindowEvent e) {}
    }

    /**
     * Update the window when the model indicates an update is
     * required. Updates the information displayed on the GUI.
     * @param t - An Observable -- not used.
     * @param o - An Object -- not used.
     */
    public void update(Observable o, Object t) {
        scrollPane = new JScrollPane();
	bankInfo = new JTable(data, col_names);
	bankInfo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	bankInfo.setEnabled(false);
	scrollPane.setViewportView(bankInfo);
	middle.removeAll();
	middle.add(scrollPane);
       	validate();
    }

}

    

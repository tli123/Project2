/**
 * BankView.java
 *
 * Holds the Bank GUI View that communicates with the Bank model.
 *
 * File:
 *	$Id: BankView.java,v 1.0 2015/11/xx 00:00:00 csci140 Exp csci140 $
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
    
    private Bank model;

    private JTextArea bankInfo;

    public BankView(Bank model) {
	this.model = model;
	model.addObserver(this);
	this.getContentPane().setLayout(new BorderLayout());
	JPanel middle = new JPanel();
	middle.setLayout(new FlowLayout());
	bankInfo = new JTextArea();
	bankInfo.setEditable(false);
	bankInfo.setText(model.toString());
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

    private class ButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("Launch ATM")) {
		ATMView atm = new ATMView(new ATM(model));
	    } else if (e.getActionCommand().equals("Update")) {
	        model.toString();
	    }
	}
    }

    public void update(Observable o, Object t) {
	bankInfo.setText(model.getCurrentStatus());
	validate();
    }

}

    

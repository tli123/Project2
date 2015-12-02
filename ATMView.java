import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;
import java.io.*;

public class ATMView extends JFrame implements Observer {

    /**
     * The JLabel that holds the prompt of the user.
     */
    private JLabel prompt;

    /**
     * The JLabel that holds the numbers the user presses are displayed.
     */
    private JLabel numbers;

    /**
     * The numbers that are echoed to the user.
     */
    private String number;

    /**
     *
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
	prompt = new JLabel(model.getStatus());
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
		int input = 0;
		if (model.getCounter() == 1) {
		    input = Integer.parseInt(pin);
		} else if (!numbers.getText().equals(" ")){
		    input = Integer.parseInt(numbers.getText().substring(1, numbers.getText().length()));
		}
		number = " ";
	        switch(model.getCounter()) {
		case(0):
		    model.setID(input);
		    model.idExists();
		    break;
		case(1):
		    model.setPin(input);
		    model.pinVerify();
		    pin = "";
		    break;
		case(2):
		    model.getBalance();
		    break;
		case(3):
		    model.preDeposit();
		    break;
		case(4):
		    model.deposit((double)input);
		    break;
		case(5):
		    model.preWithdraw();
		    break;
		case(6):
		    model.withdraw((double)input);
		    break;
		case(7):
		    model.preLogout();
		    break;
		case(8):
		    try {
			model.logout();
		    } catch (FileNotFoundException f) {
		    } catch (UnsupportedEncodingException f) {
		    }
		    break;
		case(9):
		    model.postLogout();
		    break;
		}
	    } else if (e.getActionCommand().equals("Cancel")) {
		number = " ";
	        model.cancel();
	    } else if (e.getActionCommand().equals("Clear")) {
		number = " ";
		model.updateNumbers();
	    } else if (e.getActionCommand().equals("Close")) {

	    } else {
		int willInput = model.getCounter();
		if (willInput == 1) {
		    number = numbers.getText() + "*";
		    pin += e.getActionCommand();
		} else if (willInput != 2 && willInput != 3 && willInput != 5 && willInput != 7 && willInput != 8 && willInput != 9) {
		    number = numbers.getText() + e.getActionCommand();
		}
	        model.updateNumbers();
	    }
	}
    }
       
    public void update(Observable t, Object o) {
	numbers.setText(number);
	prompt.setText(model.getStatus());
	validate();
    }

}

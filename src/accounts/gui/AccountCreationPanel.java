package accounts.gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import db.JDBCConnection;
import gui.FormRow;
import screentypes.MainScreen;
import sql.UserQuery;
import test.TestObjects;
import vending.Controller;

/**
 * Panel used for creating a new account
 */
public class AccountCreationPanel extends JPanel implements ActionListener{

    //Buttons
	JButton cancelButton = new JButton("Cancel");
	JButton okButton = new JButton("OK");
    JButton useCardReaderButton = new JButton("Enter using card reader...");
	
    //Information Fields
	TextField userName = new TextField();
	TextField firstName = new TextField();
	TextField lastName = new TextField();
	TextField cardNumber = new TextField();
	TextField email = new TextField();
	JPasswordField password = new JPasswordField();
	JPasswordField passwordConfirmation = new JPasswordField();

    Checkbox agreeCheckbox = new Checkbox("I agree to the terms and conditions",false);
    Checkbox memberCheckbox = new Checkbox("ACM Member",false);

    /**
     * Creates a new AccountCreationPanel
     * which is a form with the necessary fields for creating an account
     */
	public AccountCreationPanel() {

		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		userName.setPreferredSize(new Dimension(200, 25));
		firstName.setPreferredSize(new Dimension(200, 25));
		lastName.setPreferredSize(new Dimension(200, 25));
		cardNumber.setPreferredSize(new Dimension(200, 25));
		email.setPreferredSize(new Dimension(200, 25));
		password.setPreferredSize(new Dimension(200, 25));
		passwordConfirmation.setPreferredSize(new Dimension(200, 25));


		FormField firstNameField = new FormField(new JLabel("First Name: "), firstName);
		FormField lastNameField = new FormField(new JLabel("Last Name: "), lastName);

        FormField emailField = new FormField(new JLabel("Email: "), email);
        //TODO: emailField.setAlignmentX(Component.RIGHT_ALIGNMENT);

        FormField passwordField = new FormField(new JLabel("Password: "), password);
        FormField passConfirmField = new FormField(new JLabel("Re-type Password: "), passwordConfirmation);

        JTextArea textArea = new JTextArea(getTermsAndConditions());
        textArea.setEditable(false);


		add(new FormRow(firstNameField, lastNameField));
		add(new FormRow(new FormField(new JLabel("Card Number: "), cardNumber), useCardReaderButton));
		add(new FormField(new JLabel("Username: "), userName));
		add(new FormRow(memberCheckbox));
		add(emailField);
		add(new FormRow(passwordField, passConfirmField));
		add(textArea);
		add(new FormRow(agreeCheckbox));
		add(new FormRow(okButton, cancelButton));

		
		cancelButton.addActionListener(this);
		okButton.addActionListener(this);
        useCardReaderButton.addActionListener(this);
        cancelButton.setFocusable(false);
        okButton.setFocusable(false);
        useCardReaderButton.setFocusable(false);
	}


    /**
     * The terms and conditions for using this application
     *
     * @return the terms and conditions
     */
	public String getTermsAndConditions(){
		return "ACM Terms and Coniditions\n\n" +
				"By using this program you agree to abide by the following rules:\n\n" +
				"\t - You may not use another person's account\n" +
				"\t - You may not attempt to gain access to another person's account\n" +
				"\t - You may not add money to your account which has not been paid\n" +
				"\t - You must pay off any negative balances within 2 weeks of the end of each semester\n" +
				"\t - No mentioning Fight Club outside of Fight Club\n";
				
	}


    /**
     * class to contain a field and it's corresponding label
     */
	private class FormField extends JPanel {
		
		public FormField(JLabel label, TextField field) {
			setLayout(new FlowLayout());
			add(label);
			add(field);
		}
		
		public FormField(JLabel label, JPasswordField field) {
			setLayout(new FlowLayout());
			add(label);
			add(field);
		}
	}


    /**
     * The Listener for the items in this class
     *
     * @param event
     *              the event to process
     */
	@Override
	public void actionPerformed(ActionEvent event) {

        if (event.getSource() == useCardReaderButton) {
            ReadCardNumberPopup cardReader = new ReadCardNumberPopup();
            String cardNum = cardReader.promptUserForInfo();
            if (cardNum != null) {
                cardNumber.setText(cardNum);
            }

        } else if (event.getSource() == cancelButton) {
			Controller.getController().navigateTo(MainScreen.LOGIN_SCREEN);

		} else if (event.getSource() == okButton) {
            int passLength = password.getPassword().length;

            if (firstName.getText() == null || firstName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "First name is required");
            } else if (lastName.getText() == null || lastName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Last name is required");
            } else if (cardNumber.getText() == null || cardNumber.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Card number is required");
            } else if (userName.getText() == null || userName.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Username is required");
            } else if (userName.getText().length() < 4) {
                JOptionPane.showMessageDialog(this, "Username must be at least 4 characters");
            } else if (email.getText() == null || email.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Valid email is required");
            } else if (passLength < 5) {
                JOptionPane.showMessageDialog(this, "Password must be at least 5 characters");
            } else if (passLength != passwordConfirmation.getPassword().length) {
                JOptionPane.showMessageDialog(this, "The passwords much match");
            } else if (new String(password.getPassword(), 0, passLength).equals(new String(passwordConfirmation.getPassword(), 0, passLength)) == false) {
                JOptionPane.showMessageDialog(this, "The passwords must match");
            } else if (agreeCheckbox.getState() == false) {
                JOptionPane.showMessageDialog(this, "You must agree to the terms to continue");
            } else {

                try {
                    BigDecimal cardNum = new BigDecimal(cardNumber.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Card Number must contain only numbers");
                    return;
                }

                MessageDigest md;

                try {
                    md = MessageDigest.getInstance("MD5");

                    md.update(new String(password.getPassword(), 0, passLength).getBytes());
                    byte[] encodedPassword = md.digest();

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < encodedPassword.length; i++) {
                        if ((encodedPassword[i] & 0xff) < 0x10) {
                            sb.append("0");
                        }
                        sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
                    }

                    String encryptedPassword = sb.toString();

                    String query = "" +              //todo: not used to query, used to print out
                            "INSERT INTO accounts " +
                            "VALUES (" +cardNumber.getText() +
                            ", '" + firstName.getText() +
                            "', '" + lastName.getText() +
                            "', '" + userName.getText() +
                            "', '" + encryptedPassword +
                            "', '" + email.getText() +
                            "', " + 0.00 +
                            ", " + memberCheckbox.getState() +
                            ", " + true +
                            ", NOW());";

                    System.out.println(query);
                    //JDBCConnection.update(query);
                    try {
                        JDBCConnection.createUser(firstName.getText(), lastName.getText(), cardNumber.getText(), userName.getText(), memberCheckbox.getState(), email.getText(), encryptedPassword);
                        Controller.getController().navigateTo(MainScreen.LOGIN_SCREEN);
                    } catch (MySQLIntegrityConstraintViolationException ex) {
                        JOptionPane.showMessageDialog(this, "That card number or username is already in use");
                    }

                } catch (NoSuchAlgorithmException ex) {
                    System.err.println("encryption error");
                }

            }
		}
	}

}

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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

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
	
    //Information Fields
	TextField userName = new TextField();
	TextField firstName = new TextField();
	TextField lastName = new TextField();
	TextField cardNumber = new TextField();
	TextField email = new TextField();
	JPasswordField password = new JPasswordField();
	JPasswordField passwordConfirmation = new JPasswordField();

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
		add(new FormField(new JLabel("Card Number: "), cardNumber));
		add(new FormField(new JLabel("Username: "), userName));
		add(new FormRow(new Checkbox("ACM Member",false)));
		add(emailField);
		add(new FormRow(passwordField, passConfirmField));
		add(textArea);
		add(new FormRow(new Checkbox("I agree to the terms and conditions",false)));
		add(new FormRow(okButton, cancelButton));

		
		cancelButton.addActionListener(this);
		okButton.addActionListener(this);
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

		if (event.getSource() == cancelButton) {
			Controller.getController().navigateTo(MainScreen.LOGIN_SCREEN);


		} else if (event.getSource() == okButton) {
            //Create a user for the fields entered and save it to the DB
			TestObjects.globalUser = 
				new UserQuery(
						userName.getText(),
						cardNumber.getText(),
						new String(password.getPassword()),
						firstName.getText(),
						lastName.getText(),
						"20.00",
						"1/23/1990 11:20:10.111",
						"False"
					).toUser();
			Controller.getController().navigateTo(MainScreen.LOGIN_SCREEN);
		}
	}

}

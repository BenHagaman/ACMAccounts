package accounts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import accounts.User;

import screentypes.MainScreen;
import test.TestObjects;
import vending.Controller;
import vending.Money;
import vending.UserSession;

/**
 * Panel for logging in (by Swiping a card, or logging in manually), or creating a new account
 *
 * also acts as a listener for all of it's components
 *
 * This panel is displayed as a "welcome screen"
 */
public class LoginPanel extends JPanel implements ActionListener {

	private final JButton newAcctButton = new JButton("New Account");
	private final JButton loginButton = new JButton("Manual Login");

    /**
     * Constructs a LoginPanel and adds all of the necessary initial components and sub-panels
     */
	public LoginPanel() {

		setBackground(Color.BLACK);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


         //Setup styled text pane
		JTextPane textPane = new JTextPane();
		
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

        textPane.setText("Please Swipe\nYour Card");
        textPane.setFont(new Font("FontName", Font.BOLD, 32));

		textPane.setForeground(Color.WHITE);
		textPane.setBackground(Color.BLACK);
		textPane.setAlignmentX(CENTER_ALIGNMENT);
		textPane.setEditable(false);

		textPane.setMaximumSize(new Dimension(300, 90));
		textPane.setMinimumSize(new Dimension(300, 90));


        //Setup Buttons and set their listeners
		loginButton.setAlignmentX(CENTER_ALIGNMENT);
		newAcctButton.setAlignmentX(CENTER_ALIGNMENT);
		
		newAcctButton.addActionListener(this);
		loginButton.addActionListener(this);


		add(Box.createRigidArea(new Dimension(10, 200))); //Move the text down 200 px
		add(textPane);
		add(loginButton);
		add(newAcctButton);

	}


    /**
     * Listener for this class
     *
     * @param event
     *          the ActionEvent that happened
     */
	public void actionPerformed(ActionEvent event) {


        User user;//TODO: Temporary for testing, remove

		if(event.getSource().equals(newAcctButton)) {
			Controller.getController().navigateTo(MainScreen.ACCOUNT_CREATION_SCREEN);


		} else if (event.getSource().equals(loginButton)){

            //TODO: used for testing, remove!
			if(TestObjects.globalUser == null) {
				user = new User(" Mr. Super Long Name!", new Money(new Double("10.00")));
			} else {
				user = TestObjects.globalUser;
			}

			UserSession.startUserSession(user);
			Controller.getController().logIn(UserSession.getCurrentSession());
		}
		
	}
}

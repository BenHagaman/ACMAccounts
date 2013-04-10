package accounts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import accounts.User;

import db.JDBCConnection;
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
public class LoginPanel extends JPanel implements ActionListener, KeyListener {

	private final JButton newAcctButton = new JButton("New Account");
	private final JButton loginButton = new JButton("Manual Login");
    private StringBuilder cardReaderString = new StringBuilder("");

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

        textPane.setFocusable(false);
        loginButton.setFocusable(false);
        newAcctButton.setFocusable(false);
		
		newAcctButton.addActionListener(this);
		loginButton.addActionListener(this);


		add(Box.createRigidArea(new Dimension(10, 200))); //Move the text down 200 px
		add(textPane);
		add(loginButton);
		add(newAcctButton);

        this.setFocusable(true);
        //this.requestFocusInWindow();
        //this.requestFocus();
        //this.addKeyListener(this);
	}

    @Override
    public boolean isFocusable() {
        return true;
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

            ManualLoginPopup login = new ManualLoginPopup();
            login.promptForCredentials();
		}
		
	}


    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == '\n'){
            String cardno = cardReaderString.substring(0, cardReaderString.indexOf("="));
            System.out.println(cardno);
            cardReaderString = new StringBuilder("");

            //TODO
            //String query = "SELECT * FROM accounts WHERE card_number=" + cardno + ";";

            ResultSet rs = JDBCConnection.getUsers(cardno);

            try {
                if(rs.next()) {

                    User user = new User(rs.getString("username"), rs.getString("first_name"), rs.getString("last_name"), rs.getBigDecimal("card_number"), new Money(rs.getDouble("balance")));

                    UserSession.startUserSession(user);
                    Controller.getController().logIn(UserSession.getCurrentSession());
                }
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        } else {
            cardReaderString.append(keyEvent.getKeyChar());
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //do nothing
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //do nothing
    }
}

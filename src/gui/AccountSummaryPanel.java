package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import accounts.User;
import screentypes.UserScreen;
import vending.Controller;
import vending.UserSession;

public class AccountSummaryPanel extends JPanel implements ActionListener{
	
	JButton logOutButton = new JButton("Log Out");
	JButton transHistButton = new JButton("Account Options");
	private final Color darkGreen = new Color(0, 120, 0);
	private final Color darkRed = new Color(210, 0, 0);


	private final JLabel nameComponent = new JLabel();
	private JTextPane balanceComponent = new JTextPane();

    private final User user;
	
	
	public AccountSummaryPanel(UserSession session) {
		setLayout(new GridLayout(2,2, 10, 5));
        user = session.getUser();
		
		setOpaque(false);
		
		nameComponent.setText(user.getUserName());
		nameComponent.setFont(new Font("FontName", Font.BOLD, 26));
		
		add(nameComponent);

		balanceComponent.setText("Balance: " + user.getBalance().toString());
		balanceComponent.setForeground(user.getBalance().isNegative() ? darkRed : darkGreen);
		balanceComponent.setFont(new Font("FontName", Font.BOLD, 26));
		balanceComponent.setEditable(false);
		balanceComponent.setOpaque(false);
		add(balanceComponent);
		
		add(logOutButton);
		add(transHistButton);

		
		logOutButton.addActionListener(this);
		transHistButton.addActionListener(this);
		
	}


    /**
     * Updates the balance being displayed for the user
     */
    public void updateBalance() {
        balanceComponent.setText("Balance: " + user.getBalance().toString());
        balanceComponent.setForeground(user.getBalance().isNegative() ? darkRed : darkGreen);

        updateUI();
    }
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(logOutButton)) {
			Controller.getController().logOut();
            updateUI();


		} else if (e.getSource().equals(transHistButton)){
			Controller.getController().navigateTo(UserScreen.ACCOUNT_OPTIONS_SCREEN);
            transHistButton.setFocusPainted(false);
            updateUI();


		}
		
	}
}

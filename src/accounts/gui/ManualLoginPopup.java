package accounts.gui;

import accounts.User;
import db.JDBCConnection;
import gui.FormRow;
import vending.Controller;
import vending.Money;
import vending.Product;
import vending.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 3/25/13
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class ManualLoginPopup {

    JPanel promptPanel = new JPanel();

    public ManualLoginPopup() {

    }


    public void promptForCredentials() {
        Product customProduct = new Product();

        ProductDialog dialog = new ProductDialog(new JFrame());

        dialog.promptForCredentials();
    }
}

class ProductDialog {
    private JFrame parentFrame;
    private JDialog dialog;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton okButton;
    private JButton cancelButton;
    private static boolean isDone = false;

    public ProductDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void init() {
        okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonListener());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                close();
            }
        });

        dialog = new JDialog(parentFrame, "Login", true);
        dialog.getContentPane().add(createPane());
        dialog.getRootPane().setDefaultButton(okButton);
        dialog.pack();
        dialog.setSize(new Dimension(400,150));
        dialog.setLocationRelativeTo(parentFrame);
    }

    private Container createPane() {
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        //passwordField.setText("");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(new FormRow(new Label("Username:"), usernameField));
        //topPanel.add(new Label("Description:"));
        //topPanel.add(descriptionField);
        topPanel.add(new FormRow(new Label("Password:"), passwordField));
        //topPanel.add(new Label("Price: $"));
        //topPanel.add(priceField);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(okButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        return mainPanel;
    }

    public void promptForCredentials() {
        if (dialog == null) {
            init();
        }


        dialog.setVisible(true);

        //while (dialog.isVisible()) {
        //do nothing
        //}
    }

    private void close() {
        dialog.setVisible(false);
    }

    private class OkButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // Do work
            //close();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword(), 0, passwordField.getPassword().length);

            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");

                md.update(password.getBytes());
                byte[] encodedPassword = md.digest();

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < encodedPassword.length; i++) {
                    if ((encodedPassword[i] & 0xff) < 0x10) {
                        sb.append("0");
                    }
                    sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
                }

                String encryptedPassword = sb.toString();

                if(username != null && username.length() != 0 && passwordField.getPassword().length != 0){

                    ResultSet rs = JDBCConnection.login(username, encryptedPassword);
                    try {
                        if(rs.next()) {

                            User user = new User(username, rs.getString("first_name"), rs.getString("last_name"), rs.getBigDecimal("card_number"), new Money(rs.getDouble("balance")));

                            UserSession.startUserSession(user);
                            Controller.getController().logIn(UserSession.getCurrentSession());

                            close();
                        } else {
                            JOptionPane.showMessageDialog(parentFrame, "Incorrect username or password, please try again");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

            } catch (NoSuchAlgorithmException ex) {
                System.err.println("encryption error");
            }

        }
    }
}
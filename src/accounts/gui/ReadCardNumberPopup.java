package accounts.gui;

import accounts.User;
import db.JDBCConnection;
import gui.FormRow;
import vending.Controller;
import vending.Money;
import vending.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vending.Money;
import vending.Product;
import vending.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/25/12
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadCardNumberPopup {

    JPanel promptPanel = new JPanel();

    String cardno;

    public ReadCardNumberPopup() {

    }


    public String promptUserForInfo() {

        CardNumberDialog dialog;
        JFrame frame = new JFrame();

        dialog = new CardNumberDialog(frame);
        cardno = dialog.promptForCardNumber();

        return cardno;
    }
}

class CardNumberDialog {
    private JFrame parentFrame;
    private JDialog dialog;
    private JTextField descriptionField;
    private JTextField priceField;
    private boolean done = false;

    private JButton okButton;
    private JButton cancelButton;
    private static boolean isDone = false;
    private static StringBuilder cardReaderString = new StringBuilder();
    private static String cardno;
    private final CardNumberDialog selfReference = this;

    public CardNumberDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void init() {

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                close();
            }
        });

        dialog = new JDialog(parentFrame, "Swipe Your Card", true);
        dialog.getContentPane().add(createPane());
        dialog.getRootPane().setDefaultButton(okButton);
        dialog.pack();
        dialog.setSize(new Dimension(400,150));
        dialog.setLocationRelativeTo(parentFrame);
    }

    private Container createPane() {
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Please swipe your card..."));

        JPanel bottomPanel = new JPanel();
        //bottomPanel.add(Box.createHorizontalGlue());
        //bottomPanel.add(okButton);
        bottomPanel.add(cancelButton);
        cancelButton.setFocusable(false);
        topPanel.setFocusable(false);
        bottomPanel.setFocusable(false);

        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);



        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                mainPanel.requestFocusInWindow();
                mainPanel.requestFocus();   mainPanel.addKeyListener(new KeyListener() {
                    public void keyTyped(KeyEvent keyEvent) {
                        if (keyEvent.getKeyChar() == '\n') {
                            cardno = cardReaderString.substring(0, cardReaderString.indexOf("="));
                            System.out.println(cardno);
                            cardReaderString = new StringBuilder("");

                            done = true;
                            selfReference.close();


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
                });
            }});

        return mainPanel;
    }

    public String promptForCardNumber() {
        if (dialog == null) {
            init();
        }

        dialog.setVisible(true);

        while(!done && dialog.isVisible());

        close();
        return cardno;

    }

    protected void close() {
        done = true;
        dialog.setVisible(false);
    }
}

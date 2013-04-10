package gui;

import vending.Controller;
import vending.Money;
import vending.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/1/12
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddMoneyPanel extends PanelWithTitle implements ActionListener {

    //private final

    JButton add50c = new JButton("$0.50");
    JButton add1d = new JButton("$1.00");
    JButton add5d = new JButton("$5.00");
    JButton add10d = new JButton("$10.00");
    JButton add20d = new JButton("$20.00");
    JButton addOver9000d = new JButton("Over $9000");
    JButton addOtherAmt = new JButton("Other Amount");

    public AddMoneyPanel() {
        super("Add Money");

        panel.setLayout(new GridLayout(3, 3, 7, 10));

        Font buttonFont = new Font("Button Font", Font.BOLD, 26);

        add50c.setFont(buttonFont);
        add1d.setFont(buttonFont);
        add5d.setFont(buttonFont);
        add10d.setFont(buttonFont);
        add20d.setFont(buttonFont);
        addOver9000d.setFont(buttonFont);
        addOtherAmt.setFont(buttonFont);

        add50c.addActionListener(this);
        add1d.addActionListener(this);
        add5d.addActionListener(this);
        add10d.addActionListener(this);
        add20d.addActionListener(this);
        addOver9000d.addActionListener(this);
        addOtherAmt.addActionListener(this);

        panel.add(add50c);
        panel.add(add1d);
        panel.add(add5d);

        panel.add(add10d);
        panel.add(add20d);
        panel.add(addOver9000d);

        panel.add(new JPanel());
        panel.add(addOtherAmt);
        panel.add(new JPanel());

        updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        Money moneyToAdd = null;

        if(event.getSource() == add50c) {
            moneyToAdd = new Money(.50);

        } else if (event.getSource() == add1d) {
            moneyToAdd = new Money(1.00);

        } else if (event.getSource() == add5d) {
            moneyToAdd = new Money(5.00);

        } else if (event.getSource() == add10d) {
            moneyToAdd = new Money(10.00);

        } else if (event.getSource() == add20d) {
            moneyToAdd = new Money(20.00);

        } else if (event.getSource() == addOver9000d) {

            JOptionPane.showConfirmDialog(
                    null,
                    "9000?! THERE'S NO WAY THAT CAN BE RIGHT!!",
                    "WHAT!!?",
                    JOptionPane.PLAIN_MESSAGE);
            System.out.println("WHAT!!? 9000? THERE'S NO WAY THAT CAN BE RIGHT!!");

        } else if (event.getSource() == addOtherAmt) {
            String amount = JOptionPane.showInputDialog("Please enter the amount in dollars (eg. 1.25 with no \'$\')");

            try {
                Double d = new Double(amount);
                if (d < 0.01) {
                    JOptionPane.showMessageDialog(this, "Amount must be at least $0.01");
                } else if (d > 100) {
                    JOptionPane.showMessageDialog(this, "Amount can not exceed $100");
                } else {
                    moneyToAdd = new Money(d);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "\'" + amount + "\' is not a valid dollar amount");
            }

        }

        if (moneyToAdd != null) {

            int response = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure that you want to add " + moneyToAdd.toString() + " to your account?" +
                            "\n Note: ACM must actually receive the money at the time you add it to your account",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                UserSession.getCurrentSession().getUser().addMoney(moneyToAdd);

                Controller.getController().updateBalance();
            }
        }
    }
}

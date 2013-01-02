package gui;

import screentypes.UserScreen;
import vending.Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AccountOptionsPanel extends PanelWithTitle implements ActionListener {


    private final JButton addMoneyButton = new JButton("Add Money");
    private final JButton accountHistoryButton = new JButton("History");
    private final JButton makePurchaseButton = new JButton("Make a Purchase");


	public AccountOptionsPanel() {

        super("Account Options");

		panel.setLayout(new GridLayout(3, 5, 10, 10));

        //row 1
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());

        //row 2
        panel.add(new JPanel());
        panel.add(accountHistoryButton);
        panel.add(makePurchaseButton);
        panel.add(addMoneyButton);
        panel.add(new JPanel());

        //row 3
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(new JPanel());

        addMoneyButton.addActionListener(this);
        accountHistoryButton.addActionListener(this);
        makePurchaseButton.addActionListener(this);

        panel.updateUI();
        updateUI();
	}

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addMoneyButton) {
            Controller.getController().navigateTo(UserScreen.ADD_MONEY_SCREEN);

        } else if (event.getSource() == accountHistoryButton) {
            Controller.getController().navigateTo(UserScreen.ACCOUNT_HISTORY_SCREEN);

        } else if (event.getSource() == makePurchaseButton) {
            Controller.getController().navigateTo(UserScreen.PRODUCT_SELECTION_SCREEN);
        }
    }
}

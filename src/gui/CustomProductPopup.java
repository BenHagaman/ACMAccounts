package gui;

import vending.Money;
import vending.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/25/12
 * Time: 6:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CustomProductPopup {

    JPanel promptPanel = new JPanel();
    Product prod = new Product();

    String product;
    Money amount;

    public CustomProductPopup() {

    }


    public Product promptUserForInfo() {
        Product customProduct = new Product();

        ProductDialog dialog = new ProductDialog(new JFrame());

        customProduct = dialog.promptForProduct();

        return customProduct;
    }
}

class ProductDialog {
    private JFrame parentFrame;
    private JDialog dialog;
    private JTextField descriptionField;
    private JTextField priceField;
    private Product product = new Product();

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

        dialog = new JDialog(parentFrame, "Custom Purchase", true);
        dialog.getContentPane().add(createPane());
        dialog.getRootPane().setDefaultButton(okButton);
        dialog.pack();
        dialog.setSize(new Dimension(400,150));
        dialog.setLocationRelativeTo(parentFrame);
    }

    private Container createPane() {
        descriptionField = new JTextField(15);
        priceField = new JTextField(10);
        priceField.setText("0.00");

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(new FormRow(new Label("Description:"), descriptionField));
        //topPanel.add(new Label("Description:"));
        //topPanel.add(descriptionField);
        topPanel.add(new FormRow(new Label("Price: $"), priceField));
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

    public Product promptForProduct() {
        if (dialog == null) {
            init();
        }


        dialog.setVisible(true);

        close();
        return product;

    }

    private void close() {
        dialog.setVisible(false);
    }

    private class OkButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try{
                if (new Double(priceField.getText()) < 0) {
                    JOptionPane.showMessageDialog(dialog, "Price can not be negative");
                } else if(new Double(priceField.getText()) > 100) {
                    JOptionPane.showMessageDialog(dialog, "Price can not be over $100");
                } else {
                    product.setName(descriptionField.getText());
                    product.setPrice(new Money(new Double(priceField.getText())));
                    close();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, priceField.getText() + " is not a valid dollar amount");
            }
            //close();
        }
    }
}
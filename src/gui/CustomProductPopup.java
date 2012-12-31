package gui;

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
public class CustomProductPopup extends JDialog {

    JPanel promptPanel = new JPanel();

    public CustomProductPopup() {
        setSize(new Dimension(500, 400));




        add(promptPanel);

    }


    public Product promptUserForInfo() {
        Product customProduct = new Product();

        //setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);


        //setVisible(true);
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        new SampleDialog(new JFrame()).show();


        return customProduct;
    }
}

class SampleDialog {
    private JFrame parentFrame;
    private JDialog dialog;
    private JTextField field;

    private JButton okButton;
    private JButton cancelButton;

    public SampleDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void init() {
        this.field = new JTextField();

        this.okButton = new JButton("OK");
        this.okButton.addActionListener(new OkButtonListener());

        this.cancelButton = new JButton("Cancel");
        this.cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                close();
            }
        });

        this.dialog = new JDialog(this.parentFrame, "Sample", true);
        this.dialog.getContentPane().add(createPane());
        this.dialog.getRootPane().setDefaultButton(this.okButton);
        this.dialog.pack();
        this.dialog.setLocationRelativeTo(this.parentFrame);
    }

    private Container createPane() {
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new Label("Something"));
        topPanel.add(this.field);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(this.okButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(this.cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(9,9,9,9));
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        return mainPanel;
    }

    public void show() {
        if (this.dialog == null) {
            init();
        }

        this.field.setText("Initial value");

        this.dialog.show();
    }

    private void close() {
        this.dialog.hide();
    }

    private class OkButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            // Do work
            close();
        }
    }
}
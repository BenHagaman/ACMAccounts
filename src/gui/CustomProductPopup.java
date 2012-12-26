package gui;

import vending.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        setEscapeKeyMap();
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

    private void setEscapeKeyMap(){
        String CANCEL_ACTION_KEY = "CANCEL_ACTION_KEY";
        int noModifiers = 0;
        Object KeyEvent = null;

        InputMap inputMap = this.dialog.getRootPane().getInputMap(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        AbstractAction cancelAction = new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                close();
            }
        };
        this.dialog.getRootPane().getActionMap().put(
                CANCEL_ACTION_KEY, cancelAction);
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

//
//public class CustomDialog extends JFrame {
//    CustomDialog(String title){
//        super(title);
//        addWindowListener(
//                new WindowAdapter() {
//                    public void windowClosing(WindowEvent e) {
//                        dispose();
//                        System.exit(0);
//                    }
//                }
//        );
//
//        String[] labels = {"Name","Value"};
//        String[] defaults = {"",""};
//        MultilineDialogPanel panel = new
//                MultilineDialogPanel(labels,defaults,20);
//        int selection = JOptionPane.showConfirmDialog(this,panel,"Multiline" +
//                "Dialog",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
//        if (selection==JOptionPane.OK_OPTION){
//            System.out.println(panel.getResults()[0]);
//            System.out.println(panel.getResults()[1]);
//        }
//    }
//    public static void main(String[] args){
//        CustomDialog dlg = new CustomDialog("Multiline dialog");
//        dlg.show();
//    }
//}
//
//
//class MultilineDialogPanel extends JPanel{
//    GridBagConstraints gbc = new GridBagLayout();
//    GridBagLayout gbl = new GridBagConstraints();
//    ArrayList options = new ArrayList();
//    MultilineDialogPanel(String[] labels,String[] defaults,int
//            fieldLength){
//        super();
//        if (fieldLength == -1){fieldLength = 20;};
//        this.setLayout(gbl);
//        for (int i =0;i<labels.length;i++){
//            addComponent(new
//                    JLabel(labels[i]),0,i*2,GridBagConstraints.WEST);
//            JTextField text = new JTextField(defaults[i],fieldLength);
//            addComponent(text,0,i*2+1,GridBagConstraints.WEST);
//            options.add(text);
//        }
//    }
//    public String[] getResults(){
//        String[] results = new String[options.size()];
//        for (int i=0;i<options.size();i++){
//            results[i] = ((javax.swing.text.JTextComponent)(options.get(i))).getText();
//        } return results;
//    }
//    public void addComponent(Component component,int xpos, int ypos){
//        gbc.gridx = xpos;
//        gbc.gridy = ypos;
//        gbc.insets = new Insets(2,2,2,2);
//        gbl.setConstraints(component,gbc);
//        this.add(component);
//    }
//    public void addComponent(Component component,int xpos,int ypos,int
//            anchor){
//        gbc.anchor = anchor;
//        addComponent(component,xpos,ypos);
//    }
//}
package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/25/12
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormRow extends JPanel{


    /**
     * creates a panel which contains multiple components in a single row
     */
    public FormRow(JComponent ... args) {
        setLayout(new FlowLayout());
        for(JComponent component: args) {
            add(component);
        }
    }

    public FormRow(Component ... args) {
        setLayout(new FlowLayout());
        for(Component component: args) {
            add(component);
        }
    }
}

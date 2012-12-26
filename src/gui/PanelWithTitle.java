package gui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import javax.swing.BoxLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/2/12
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class PanelWithTitle extends JPanel {
    public static final String NORTH = BorderLayout.NORTH;
    public static final String EAST = BorderLayout.SOUTH;
    public static final String WEST = BorderLayout.WEST;
    public static final String SOUTH = BorderLayout.SOUTH;
    public static final String CENTER = BorderLayout.CENTER;

    private final JPanel titlePanel = new JPanel();
    protected final JPanel panel = new JPanel();

    public PanelWithTitle(String title) {
        setLayout(new BorderLayout());

        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JTextPane textPane = new JTextPane();

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        textPane.setText(title);
        textPane.setFont(new Font("FontName", Font.BOLD, 32));

        textPane.setForeground(Color.BLACK);
        textPane.setOpaque(false);
        textPane.setAlignmentX(CENTER_ALIGNMENT);
        textPane.setEditable(false);

        textPane.setMaximumSize(new Dimension(300, 90));
        textPane.setMinimumSize(new Dimension(300, 90));

        titlePanel.add(textPane);

        add(BorderLayout.NORTH, titlePanel);
        add(panel);
    }

    public PanelWithTitle(String title, int panelPosition) {

    }
}

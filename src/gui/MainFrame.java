package gui;

import javax.swing.JFrame;

/**
 * Sets up the JFrame which contains most of the panels
 */
public class MainFrame extends JFrame {

    private static String frameTitle = "ACM Accounts (Dev)";

	public MainFrame() {

		super(frameTitle);

        //Closes unless locked in the Controller
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
		setSize(800, 600);
	}

}

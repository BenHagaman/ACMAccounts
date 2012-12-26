package vending;

import gui.MainFrame;


/**
 * Main Class for Vending
 * Sets up the JFrame and the Controller
 */
public class Vending{
	public static void main(String args[]) {
		
		MainFrame frame = new MainFrame();

        //Set up controller and add login panel to the frame
		Controller.initialize(frame);
	}
}
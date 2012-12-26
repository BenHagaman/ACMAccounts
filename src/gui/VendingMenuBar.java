package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import vending.Controller;


public class VendingMenuBar extends JMenuBar implements ActionListener{

	JMenu fileMenu = new JMenu("File");
	JMenu editMenu = new JMenu("Edit");
	JMenu viewMenu = new JMenu("View");
	JMenu windowMenu = new JMenu("Window");
	JMenu helpMenu = new JMenu("Help");
	
	JMenuItem quit = new JMenuItem("Quit");
	
	public VendingMenuBar()  {
		
		fileMenu.add(quit);
		quit.addActionListener(this);
		
		
		
		
		add(fileMenu);
		add(editMenu);
		add(viewMenu);
		add(windowMenu);
		add(helpMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == quit){
			Controller.getController().exit();
		}
		
	}
	
	
	

}

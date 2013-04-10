package vending;
import db.JDBCConnection;
import gui.MainPanel;
import gui.VendingMenuBar;

import java.awt.BorderLayout;

import javax.swing.*;

import screentypes.MainScreen;
import screentypes.UserScreen;
import screentypes.extensibility.MainScreenLevel;

import accounts.gui.AccountCreationPanel;
import accounts.gui.LoginPanel;


public class Controller {
	
	private static Controller controller;
	private static UserSession session;
	
	//Menu
	private static VendingMenuBar menuBar = new VendingMenuBar();
	
	//Frames
	private static JFrame mainFrame;

	//Panels
	private static MainPanel navigationPanel;
	private static LoginPanel loginPanel;
	private static AccountCreationPanel accountCreationPanel;
	private static JPanel currentPanel; //current panel being displayed

    //Enum representation of the current screen being displayed
	private static MainScreen currentScreen = MainScreen.LOGIN_SCREEN;
	

    /*
     * private constructor. Only one controller is allowed at a time
     */
	private Controller(){
        //nothing to do
	}


    /**
     * Access method for the current controller
     *
     * @return the controller
     */
	public static Controller getController() {
		return controller;
	}


    /**
     * Set up a controller and add the initial panels to the JFrame
     *
     * @param frame
     *          The JFrame for this controller to control
     */
	public static void initialize(JFrame frame) {

		controller = new Controller();
		mainFrame = frame;

        //Add MenuBar
		frame.add(BorderLayout.NORTH, menuBar);

        //Add LoginPanel
		loginPanel = new LoginPanel();
		currentPanel = loginPanel;
        frame.getContentPane().add(loginPanel);
        loginPanel.setVisible(true);
        loginPanel.requestFocusInWindow();
        loginPanel.addKeyListener(loginPanel);

        //Display the login panel
        loginPanel.updateUI();
		frame.update(frame.getGraphics());
	}


    /**
     * Log in with the user contained within the user session
     *
     * @param userSession
     *              User Session containing the user who is logging in
     */
	public void logIn(UserSession userSession) {
		session = userSession;
		mainFrame.remove(loginPanel);
		loginPanel = null;
		
		navigationPanel = new MainPanel(userSession);
		mainFrame.add(navigationPanel);
		currentPanel = navigationPanel;
		
		currentScreen = MainScreen.NAVIGATION_SCREEN;
	}

    /**
     * Log out of the current session and destroy the objects
     * containing information about the user
     */
	public void logOut() {

		mainFrame.remove(navigationPanel);

		session.destroy();
		session = null;

		navigationPanel = null;

		loginPanel = new LoginPanel();
		mainFrame.add(loginPanel);
		currentPanel = loginPanel;
		currentScreen = MainScreen.LOGIN_SCREEN;
        loginPanel.setVisible(true);
        loginPanel.requestFocusInWindow();
        loginPanel.addKeyListener(loginPanel);

        System.gc();
	}


    /**
     * Block the user from exiting the program until 'unlockExit()' is called
     */
	public void lockExit() {
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		navigateTo(UserScreen.ACCOUNT_HISTORY_SCREEN);
	}


    /**
     * Remove the current panel and navigate to the specified screen
     *
     * @param screen
     *          The screen to navigate to
     */
	public void navigateTo(MainScreenLevel screen) {
        //if the screen is not specifically a MainScreen then pass it to the next level
		if(screen instanceof UserScreen){
			UserScreen userScreen = (UserScreen) screen;
			navigationPanel.navigateToScreen(userScreen);

		} else if(screen instanceof MainScreen){
			MainScreen newMainScreen = (MainScreen)screen;
			
			if(currentScreen != screen) {

				removeCurrentPanel();
				
				if (screen == MainScreen.LOGIN_SCREEN) {
					if(session != null) {
						logOut();
					} else {
						loginPanel = new LoginPanel();
						currentPanel = loginPanel;
						mainFrame.add(loginPanel);
						currentScreen = newMainScreen;

                        mainFrame.repaint();
                        currentPanel.updateUI();

                        loginPanel.setVisible(true);

                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run() {
                                loginPanel.addKeyListener(loginPanel);
                                loginPanel.requestFocusInWindow();
                                loginPanel.requestFocus();
                            }});

					}

				} else if (screen == MainScreen.ACCOUNT_CREATION_SCREEN) {
					accountCreationPanel = new AccountCreationPanel();
					currentPanel = accountCreationPanel;
					mainFrame.add(accountCreationPanel);
					currentScreen = newMainScreen;

                    mainFrame.repaint();
                    currentPanel.updateUI();

				} else if (screen == MainScreen.NAVIGATION_SCREEN && session != null) {
					currentScreen = newMainScreen;

                    mainFrame.repaint();
                    currentPanel.updateUI();

				}
			}
		}
	}


    /**
     * remove the current main panel being displayed
     */
	private void removeCurrentPanel() {

		if (currentScreen == MainScreen.LOGIN_SCREEN) {
			mainFrame.remove(loginPanel);
			loginPanel = null;

		} else if (currentScreen == MainScreen.ACCOUNT_CREATION_SCREEN) {
			mainFrame.remove(accountCreationPanel);
			accountCreationPanel = null;

		} else if (currentScreen == MainScreen.NAVIGATION_SCREEN) {
			mainFrame.remove(navigationPanel);
			navigationPanel = null;
		}

	}


    /**
     * Allow the user to exit again after being locked with a call to 'lockExit()'
     */
	public void unlockExit() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


    /**
     * Update the balance shown in the User's AccountSummaryPanel
     */
	public void updateBalance() {
		navigationPanel.updateBalance();
	}

    /**
     * exit the program
     */
	public void exit() {

        if(session != null){
            session.destroy();
            session = null;
        }

        if(mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }

        JDBCConnection.close();

		System.exit(0);
	}
}

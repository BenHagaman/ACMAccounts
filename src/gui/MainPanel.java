package gui;

import gui.PanelWithNavigation.NavigationType;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import screentypes.UserScreen;
import vending.UserSession;

/**
 * The Main panel that the user navigates around in until the user logs out
 */
public class MainPanel extends JPanel{

	private AccountSummaryPanel acctSummaryPanel;
    private UserScreen currentScreen;
    private AccountOptionsPanel accountOptionsPanel;
    private AddMoneyPanel addMoneyPanel;
    private AccountHistoryPanel accountHistoryPanel;
	private PanelWithNavigation productViewingPanel = 
		    new PanelWithNavigation(new ProductViewingPanel(), NavigationType.ARROWS_WITH_PAGE_NUM);

    /**
     * Creates a new panel to contain the different panels that a user can navigate to
     * this panel contains a summary of the user's account as well as some navigation
     * controls at the top of this panel
     *
     * @param session
     */
	public MainPanel(UserSession session) {

		this.setLayout(new BorderLayout());

		currentScreen = UserScreen.PRODUCT_SELECTION_SCREEN;

        //summary at the top of the panel of the user's account
		acctSummaryPanel = new AccountSummaryPanel(session);
		
		add(BorderLayout.NORTH, acctSummaryPanel);
		add(BorderLayout.CENTER, productViewingPanel);
		
	}


    /**
     * update the acctSummaryPanel values by making a new one.
     */
	public void updateBalance() {
		acctSummaryPanel.updateBalance();
	}


    /**
     * navigate to the UserScreen passed in
     * if the screen is not a direct component of this panel
     * then pass this request to the panel it's directly contained in
     *
     * @param screen
     *              the screen to be changed to
     */
	public void navigateToScreen(UserScreen screen) {

		if (screen != currentScreen) {

			if (screen == UserScreen.ACCOUNT_HISTORY_SCREEN) {
                removeCurrentPanel();

                accountHistoryPanel = new AccountHistoryPanel();
                add(BorderLayout.CENTER, accountHistoryPanel);
                updateUI();

			} else if (screen == UserScreen.ACCOUNT_OPTIONS_SCREEN) {
				removeCurrentPanel();

                accountOptionsPanel = new AccountOptionsPanel();
				add(BorderLayout.CENTER, accountOptionsPanel);

                updateUI();


			} else if (screen == UserScreen.ADD_MONEY_SCREEN) {
                removeCurrentPanel();

                addMoneyPanel = new AddMoneyPanel();
                add(BorderLayout.CENTER, addMoneyPanel);
                updateUI();


			} else if (screen == UserScreen.PRODUCT_SELECTION_SCREEN) {
                System.out.println("OK");
                removeCurrentPanel();

                productViewingPanel = new PanelWithNavigation(new ProductViewingPanel(), NavigationType.ARROWS_WITH_PAGE_NUM);

                add(BorderLayout.CENTER, productViewingPanel);

                updateUI();
			}

            currentScreen = screen;
		}
	}


    private void removeCurrentPanel() {

        if (currentScreen == UserScreen.ACCOUNT_HISTORY_SCREEN) {
            remove(accountHistoryPanel);
            accountHistoryPanel = null;

        } else if (currentScreen == UserScreen.ACCOUNT_OPTIONS_SCREEN) {
            remove(accountOptionsPanel);
            accountOptionsPanel = null;


        } else if (currentScreen == UserScreen.ADD_MONEY_SCREEN) {
            remove(addMoneyPanel);
            addMoneyPanel = null;


        } else if (currentScreen == UserScreen.PRODUCT_SELECTION_SCREEN) {
            remove(productViewingPanel);
            productViewingPanel = null;
        }
    }
}

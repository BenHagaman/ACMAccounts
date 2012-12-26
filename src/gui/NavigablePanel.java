package gui;

import javax.swing.JPanel;

/**
 * The interface for a panel which has several pages
 * which are intended to be easily navigable via a simple
 * control panel
 */
public abstract class NavigablePanel extends JPanel{
	
	public abstract void nextPage();
	
	public abstract void prevPage();
	
	public abstract void setPage(int i);
	
	public abstract int getPage();
	
	public abstract int getTotalPages();
}

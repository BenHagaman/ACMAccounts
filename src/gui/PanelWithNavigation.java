package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelWithNavigation extends JPanel implements ActionListener{

	private JPanel navigationPanel;
	private NavigablePanel contentPanel;
	
	private JButton prevButton = new JButton("Previous");
	private JButton nextButton = new JButton("Next");
	
	public static enum NavigationType{
		ARROWS_WITH_PAGE_NUM;
	}
	
	PanelWithNavigation(NavigablePanel panel, NavigationType type) {
		setLayout(new BorderLayout());
		
		contentPanel = panel;
		add(BorderLayout.CENTER, contentPanel);
		
		navigationPanel = new JPanel();
		add(BorderLayout.SOUTH, navigationPanel);
		
		if (type == NavigationType.ARROWS_WITH_PAGE_NUM) {
			navigationPanel.add(prevButton);
			navigationPanel.add(nextButton);
			nextButton.addActionListener(this);
			prevButton.addActionListener(this);
			prevButton.setEnabled(false); //currently on the first page

            if (contentPanel.getTotalPages() <= 1) {
                nextButton.setEnabled(false);
                prevButton.setEnabled(false);
            }
		}
	}
	
	public JPanel getContentPanel() {
		return contentPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == prevButton) {
			contentPanel.prevPage();
			
			nextButton.setEnabled(true);
			if(contentPanel.getPage() == 1) {
				prevButton.setEnabled(false);
			}
			
		} else if (e.getSource() == nextButton) {
			contentPanel.nextPage();
			
			prevButton.setEnabled(true);
			if(contentPanel.getPage() == contentPanel.getTotalPages()) {
				nextButton.setEnabled(false);
			}
		}
		
	}
	
	
	
}

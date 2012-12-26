package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import vending.Controller;
import vending.Product;
import vending.UserSession;

public class ProductPanel extends JPanel implements ActionListener{
	
	
	private final Product product;
    JButton itemButton;

    /**
     * Panel containing information about a product.
     *
     * This is a 200x200 panel which displays the name and price of a product,
     * as well as an image of the product.
     *
     * @param prod
     */
	public ProductPanel(Product prod) {
		product = prod;
		setBorder(BorderFactory.createEtchedBorder());

		this.setLayout(new BorderLayout());
		
		setMinimumSize(new Dimension(200, 200));
		setMaximumSize(new Dimension(200, 200));
		setBackground(new Color(220, 220, 220));

        //Create button with image if possible, otherwise label it with the name of the product
        if (prod.getImagePath() == null) {
		    itemButton = new JButton(product.getName() == null ? "Not Available" : product.getName());

        } else {

            Image img = null;

            try {
                File file = new File(prod.getImagePath());
                img = ImageIO.read(file);
                img = img.getScaledInstance(250, 160, Image.SCALE_SMOOTH);

                itemButton = new JButton(new ImageIcon(img));
            } catch (IOException e) {
                //ignore
                System.out.println(ImageIO.getCacheDirectory().getAbsolutePath());
                itemButton = new JButton(product.getName() == null ? "Not Available" : product.getName());
            }

        }

		add(itemButton);
		
		JLabel price = new JLabel("Price: " + 
				(product.getPrice() == null ? 
						"$0.00" 
						: product.getPrice().toString()));
		
		JLabel name = new JLabel(product.getName() == null ? "Product" : product.getName());
        name.setFont(new Font("Name", Font.BOLD,16));

		add(BorderLayout.NORTH, name);
		add(BorderLayout.SOUTH, price);
		
		itemButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        int response = JOptionPane.showConfirmDialog(
                null,
                "Are you sure that you want to purchase " + product.getName() +
                        " for " + product.getPrice().toString() + "?",
                "Confirm Purchase",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            UserSession.getCurrentSession().getUser().makePurchase(product);

            Controller.getController().updateBalance();
        }
	}

	public Product getProduct(){
		return product;
	}

    public void addActionListener(ActionListener listener) {
        itemButton.addActionListener(listener);
    }

}

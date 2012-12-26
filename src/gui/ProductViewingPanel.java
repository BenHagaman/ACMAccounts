package gui;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import vending.Controller;
import vending.Money;
import vending.Product;
import vending.UserSession;

public class ProductViewingPanel extends NavigablePanel implements ActionListener{

	//TODO: DEFUALT TO 15 ITEMS PER PAGE

    Product otherProduct = new Product(); //other product - for items not in DB
	private int page;
	private final ArrayList<ProductPanel> allProducts = new ArrayList<ProductPanel>();
	
	public ProductViewingPanel(){
		
		page = 0; //set to 1 at the end
		this.setLayout(new GridLayout(3, 4, 7, 10));


        otherProduct.setName("Other");
        otherProduct.setImagePath("images/unkn.png");
        //other.setPrice(new Money());
        ProductPanel otherProductPanel = new ProductPanel(otherProduct) {

            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomProductPopup().promptUserForInfo();

            }
        };

        allProducts.add(otherProductPanel);

		
		Product mtnDew = new Product();
		mtnDew.setName("Mountain Dew");
        mtnDew.setImagePath("images/mtndew.png");
		mtnDew.setPrice(new Money(0.50));
		
		Product coke = new Product();
		coke.setName("Coca-Cola");
        coke.setImagePath("images/coke.png");
		coke.setPrice(new Money(0.75));
		
		Product bebopCola = new Product();
		bebopCola.setName("Bebop Cola");
        bebopCola.setImagePath("images/bebopcola.jpg");
		bebopCola.setPrice(new Money(1.00));

		allProducts.add(new ProductPanel(mtnDew));
		allProducts.add(new ProductPanel(coke));
		allProducts.add(new ProductPanel(bebopCola));
		
		for(int i = 1; i <= 2; i++) {
			allProducts.add(new ProductPanel(new Product()));
		}

		
		Product candy = new Product();
		candy.setName("Candy Bar");
        candy.setImagePath("images/candy.jpg");
		candy.setPrice(new Money(1.00));
		
		Product chocolate = new Product();
		chocolate.setName("CHOCOLATE!!");
        chocolate.setImagePath("images/chocolate.jpg");
		chocolate.setPrice(new Money(5.01));
		
		allProducts.add(new ProductPanel(candy));
		allProducts.add(new ProductPanel(chocolate));

        for(int i = 1; i <= 4; i++) {
            allProducts.add(new ProductPanel(new Product()));
        }

        for(ProductPanel productPanel: allProducts) {
            productPanel.addActionListener(this);
        }

        setPage(1);
	
	}

	@Override
	public void nextPage() {
		
		if(page < (int) ((double) allProducts.size() / 15.0) + 1 ) {

			setPage(page + 1);
		}
	}

	@Override
	public void prevPage() {
		
		if(page != 1) {
			
			setPage(page - 1);
		}
	}

	@Override
	public void setPage(int index) {
		if(index > 0 && index <= (int) ((double) allProducts.size() / 15.0) + 1 ) {

            removeAll();
			page = index;

			int iMax = page * 15 - 1;

			if (allProducts.size() <= iMax) {
				iMax = allProducts.size() - 1;
			}

			for (int i = (page - 1) * 15;i <= iMax; i++) {
				add(allProducts.get(i));
			}

            if (index == getTotalPages()) {
                if (iMax <= page * 15 - 1) {
                    for (int i = iMax; i < page * 15 - 1; i++) {
                        add(new JPanel());
                    }
                }
            }

			updateUI();
		}
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public int getTotalPages() {
		return (int) ((double) allProducts.size() / 15.0) + 1;
		
	}

    @Override
    public void actionPerformed(ActionEvent event) {

        if(event.getSource() instanceof ProductPanel) {
            Product productPurchased = ((ProductPanel) event.getSource()).getProduct();

            if (event.getSource().equals(otherProduct)) {
                System.out.println("OTHER MANG");

                return;
            }

            UserSession.getCurrentSession().getUser().makePurchase(productPurchased);

            Controller.getController().updateBalance();
        }

    }
}

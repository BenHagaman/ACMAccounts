package vending;

/**
 * A class which represents a product that the user can purchase
 *
 * contains a price, name, and image path (path for an image of the product)
 */
public class Product {
	
	private Money price;
	private String name;
	private String imagePath;
	
	public Product() {
		
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setImagePath(String newImagePath) {
		imagePath = newImagePath;
	}
	
	public void setPrice(Money newPrice) {
		price = newPrice;
	}
	
	public String getName() {
		return name;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public Money getPrice() {
		return price;
	}

}

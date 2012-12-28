package vending;

/**
 * A class which represents a product that the user can purchase
 *
 * contains a price, name, and image path (path for an image of the product)
 */
public class Product {

    private Integer id;
    private Money price;
	private String name;
	private String imagePath;
    private boolean isInStock;
	
	public Product() {
		
	}

    public Integer getId() {
        return id;
    }

    public Money getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

}

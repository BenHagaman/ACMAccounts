package accounts;

import vending.Money;
import vending.Product;

public class User {

	Money balance = new Money(10.00);
	String userName;
	
	public User(String userName, Money balance) {
		this.userName = userName;
		this.balance = balance;
	}
	
	public void makePurchase(Product product) {
        if (product == null){
            return;
        }

		balance.subtract(product.getPrice());
		System.out.println(balance.toString());
	}

    public void addMoney(Money amount) {
        if (amount == null) {
            return;
        }

        balance.add(amount);
        System.out.println(balance.toString());
    }
	
	public Money getBalance() {
		return balance;
	}
	
	public String getUserName() {
		return userName;
	}

    /**
     * removes all identifiable information about the user
     */
    public void destroy() {
        System.out.println(" user.Destroy() Call");
        //balance = null;   TODO: ADD BACK IN
        //userName = null;  TODO: ADD BACK IN
    }
	
	
}

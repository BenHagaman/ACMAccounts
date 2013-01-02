package accounts;

import test.TestObjects;
import vending.Money;
import vending.Product;
import vending.Transaction;

import java.util.Date;

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



        Transaction trans = new Transaction();

        trans.setPname(product.getName());
        trans.setAmount(product.getPrice().toBigDecimal().doubleValue());
        trans.setDate("1/2/2013");
        trans.setAcctBalance(balance.toBigDecimal().doubleValue());

        TestObjects.getTransactionList().add(0, trans);
	}

    public void addMoney(Money amount) {
        if (amount == null) {
            return;
        }

        balance.add(amount);
        System.out.println(balance.toString());

        Transaction trans = new Transaction();

        trans.setPname("Add Money");
        trans.setAmount(amount.toBigDecimal().doubleValue());
        trans.setDate("1/2/2013");
        trans.setAcctBalance(balance.toBigDecimal().doubleValue());

        TestObjects.getTransactionList().add(0, trans);
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

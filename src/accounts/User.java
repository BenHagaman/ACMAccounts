package accounts;

import db.JDBCConnection;
import test.TestObjects;
import vending.Money;
import vending.Product;
import vending.Transaction;

import java.math.BigDecimal;

public class User {

	Money balance = new Money(10.00);
	String userName;
    String firstName;
    String lastName;
    BigDecimal cardno;
	
	public User(String userName, String firstName, String lastName, BigDecimal cardno, Money balance) {
		this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;

		this.balance = balance;
        this.cardno = cardno;
	}
	
	public void makePurchase(Product product) {
        if (product == null){
            return;
        }

		balance.subtract(product.getPrice());
		System.out.println(balance.toString());



        Transaction trans = new Transaction();

        String productName = product.getName();
        BigDecimal amount = product.getPrice().toBigDecimal();
        BigDecimal newBalance = balance.toBigDecimal();



        String addTransactionToDB = "INSERT INTO transactions VALUES(null, " + cardno + ", " +
                amount + ", \'Purchased " + productName + "\', " + newBalance + ", NOW());";

        JDBCConnection.update(addTransactionToDB);


        String updateBalance = "UPDATE accounts SET balance=" + balance.toBigDecimal() + " WHERE username =\'" + userName + "\';";
        System.out.println(updateBalance);
        JDBCConnection.update(updateBalance);
	}

    public void addMoney(Money amount) {
        if (amount == null) {
            return;
        }

        balance.add(amount);
        System.out.println(balance.toString());

        BigDecimal newBalance = new BigDecimal(balance.toBigDecimal().doubleValue());

        String addTransactionToDB = "INSERT INTO transactions VALUES(null, " + cardno + ", " +
                amount.toBigDecimal() + ",\'Deposited Money\', " + newBalance + ", NOW());";

        System.out.println(addTransactionToDB);
        JDBCConnection.update(addTransactionToDB);


        String updateBalance = "UPDATE accounts SET balance=" + balance.toBigDecimal() + " WHERE username =\'" + userName + "\';";
        System.out.println(updateBalance);
        JDBCConnection.update(updateBalance);

        //TestObjects.getTransactionList().add(0, trans);
    }
	
	public Money getBalance() {
		return balance;
	}
	
	public String getUserName() {
		return userName;
	}

    public BigDecimal getCardno() {
        return cardno;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

package sql;

import java.math.BigDecimal;

import vending.Money;

import accounts.User;

public class UserQuery {
	
	String userName;
	String cardNumber;
	String password;
	String firstName;
	String lastName;
	String balance;
	String creationDate;
	String isActive;

	public UserQuery(
			String userName,
			String cardNumber,
			String password,
			String firstName,
			String lastName,
			String balance,
			String creationDate,
			String isActive
			) {
		this.userName = userName;
		this.cardNumber = cardNumber;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
		this.creationDate = creationDate;
		this.isActive = isActive;
	}
	
	public User toUser() {
		return new User(userName, firstName, lastName, new BigDecimal(cardNumber) ,new Money(new Double(balance)));
	}
	
}

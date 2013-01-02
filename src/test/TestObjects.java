package test;

import accounts.User;
import vending.Transaction;

import java.util.List;
import java.util.Vector;

public class TestObjects {

	public static User globalUser;
    private static Vector<Transaction> transactionList = null;

    public static Vector<Transaction> getTransactionList() {
        if(transactionList == null) {
            transactionList = new Vector<Transaction>();

            Transaction trans = new Transaction();

            trans.setPname("Add Money");
            trans.setAmount(0.50);
            trans.setDate("7/11/2012");
            trans.setAcctBalance(10.00);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Mountain Dew");
            trans.setAmount(0.50);
            trans.setDate("7/11/2012");
            trans.setAcctBalance(9.50);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Coca-Cola");
            trans.setAmount(0.50);
            trans.setDate("5/13/2012");
            trans.setAcctBalance(10.00);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Bebop Cola");
            trans.setAmount(0.50);
            trans.setDate("5/10/2012");
            trans.setAcctBalance(10.50);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Cheetos");
            trans.setAmount(0.50);
            trans.setDate("4/12/2012");
            trans.setAcctBalance(11.00);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Cake");
            trans.setAmount(1.50);
            trans.setDate("4/1/2012");
            trans.setAcctBalance(12.50);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Add Money");
            trans.setAmount(10.00);
            trans.setDate("4/1/2012");
            trans.setAcctBalance(2.50);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Applesauce");
            trans.setAmount(0.25);
            trans.setDate("3/26/2012");
            trans.setAcctBalance(2.75);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Kit-Kat");
            trans.setAmount(0.75);
            trans.setDate("3/24/2012");
            trans.setAcctBalance(3.50);

            transactionList.add(trans);

            trans = new Transaction();

            trans.setPname("Oatmeal Cream Pie");
            trans.setAmount(0.50);
            trans.setDate("2/11/2012");
            trans.setAcctBalance(4.00);

            transactionList.add(trans);
        }

        return transactionList;
    }
}

package vending;


/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/27/12
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
//@org.hibernate.annotations.Entity
public class Transaction {
    //@Id
    private Integer id;
    private Integer uid;
    private Integer pid;
    private boolean isCustom;
    private Double amount;
    private String notes;



    public Transaction() {

    }

}

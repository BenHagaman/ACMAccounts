package vending;


import java.util.Date;

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
    private String pname;
    private Double amount;
    private Double acctBalance;
    private boolean isCustom;
    private String notes;
    private String date;

    public Transaction() {

    }

    public Integer getId() {
        return id;
    }

    public Integer getUid() {
        return uid;
    }

    public Integer getPid() {
        return pid;
    }

    public String getPname() {
        return pname;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getAcctBalance() {
        return acctBalance;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setAcctBalance(Double acctBalance) {
        this.acctBalance = acctBalance;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

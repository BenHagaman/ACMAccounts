package vending;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 12/27/12
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private Integer id;
    private Integer cardNumber;
    private String firstName;
    private String lastName;
    private boolean isAMember;
    private boolean isActive;
    private boolean isOfficer;

    public User() {

    }

    public Integer getID() {
        return id;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isAMember() {
        return isAMember;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isOfficer() {
        return isOfficer;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAMember(boolean AMember) {
        isAMember = AMember;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setOfficer(boolean officer) {
        isOfficer = officer;
    }
}

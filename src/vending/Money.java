package vending;

import java.math.BigDecimal;

/**
 * Class representing money in U.S. Dollars
 */
public class Money {

    //underlying BigDecimal representation of the amount
	private BigDecimal amount;

    /**
     * @param d
     *          The amount of money in double format
     */
	public Money(double d) {
		amount = BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

    /**
     * @return
     *          A String representation of this Money object in
     *          The format : "$D.cc"
     */
	public String toString() {
		return "$" + amount.toPlainString();
	}

    /**
     * get the amount of dollars without cents
     *
     * @return
     *      dollar amount
     */
	public int getDollarAmount(){
		return amount.intValue();
	}

    /**
     * returns the underlying BigDecimal representiation
     *
     * @return
     *      amount in BigDecimal format
     */
	public BigDecimal toBigDecimal() {
		return new BigDecimal(amount.toPlainString());
	}

    /**
     * return the amount of cents without the dollar amount
     * (not the total amount converted to cents)
     *
     * @return
     *          number of cents past the dollarAmount
     */
	public int getCents(){

        final BigDecimal dollarAmount = BigDecimal.valueOf(getDollarAmount());
        final BigDecimal centsAmount = amount.subtract(dollarAmount);

        return centsAmount.movePointRight(2).intValue();
	}

    /**
     * @return true of this Money object represents a negative value (less than $0)
     */
	public boolean isNegative() {
		return (amount.compareTo(BigDecimal.ZERO) == -1);
	}

    /**
     * add a Money amount from this Money object
     *
     * @param m
     *          money amount to add to this Money object
     */
    public void add(Money m) {
        amount = amount.add(m.toBigDecimal());
    }

    /**
     * subtract a Money amount from this Money object
     *
     * @param m
     *          money amount to subtract from this Money object
     */
	public void subtract(Money m) {
		amount = amount.subtract(m.toBigDecimal());
	}
}

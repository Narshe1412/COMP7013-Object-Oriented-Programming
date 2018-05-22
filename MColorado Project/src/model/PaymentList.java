package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates a list of Payment objects for ths system
 * 
 * @author Manuel Colorado
 * @deprecated
 */
public class PaymentList extends ArrayList<Payment> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public PaymentList() {
		super();
	}

	/**
	 * Add a new Payment to the system with the default date of today and the amount
	 * passed by parameter
	 * 
	 * @param amount
	 *            A numeric (double) value that represents the amount paid
	 * @return the newly created payment object
	 */
	public Payment addNew(final double amount) {
		Payment p = new Payment(amount);
		super.add(p);
		p.setPaymentID(indexOf(p));
		return p;
	}

	/**
	 * Updates a payment passed by parameter with a new amount and date
	 * 
	 * @param i
	 *            Payment that will be modified
	 * @param amount
	 *            New numeric value representing the value
	 * @param date
	 *            a new date for the payment object passed by parameter
	 */
	public void update(final Payment i, final double amount, final Date date) {
		i.setPaymentAmt(amount);
		i.setPaymentDate(date);
	}

	/**
	 * Updates a payment using the id passed by parameter with a new amount and date
	 * 
	 * @param id
	 *            Id of the Payment that will be modified
	 * @param amount
	 *            New numeric value representing the value
	 * @param date
	 *            a new date for the payment object passed by parameter
	 */
	public void updateById(final int id, final double amount, final Date date) {
		Payment toUpdate = get(id);
		toUpdate.setPaymentAmt(amount);
		toUpdate.setPaymentDate(date);
	}

	/**
	 * Deletes a payment passed by parameter
	 * 
	 * @param i
	 *            the payment that will be removed from the system
	 */
	public void delete(final Payment i) {
		super.remove(i);
	}

	/**
	 * Deletes the payment that is referenced by the id passed by parameter
	 * 
	 * @param id
	 *            A numeric value representing the ID of a payment
	 */
	public void deleteById(final int id) {
		super.remove(id);
	}

	@Override
	public String toString() {
		String result = "";
		for (Payment inv : this) {
			result += indexOf(inv) + ": " + inv.toString() + "\n";
		}
		return result;
	}

	public Payment getById(final int paymentID) {
		// TODO Document and polish
		for (Payment p : this) {
			if (p.getPaymentID() == paymentID) {
				return p;
			}
		}
		return null;
	}
}

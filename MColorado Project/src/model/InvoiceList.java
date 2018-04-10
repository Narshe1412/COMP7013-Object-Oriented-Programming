package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created an ordered list of Invoice objects
 * 
 * @author Manuel Colorado
 *
 */
public class InvoiceList extends ArrayList<Invoice> implements Serializable {

	private static final long serialVersionUID = 1L;

	public InvoiceList() {
		super();
	}

	/**
	 * Add a new empty invoice to the system
	 * 
	 * @return The invoice just created
	 */
	public Invoice addNew() {
		Invoice inv = new Invoice(new Date());
		super.add(inv);
		inv.setInvoiceID(indexOf(inv));
		return inv;
	}

	/**
	 * Updates an invoice passed by parameter with a new date passed by parameter
	 * 
	 * @param i
	 *            Invoice object that will be updated
	 * @param date
	 *            the new Date for the invoice
	 */
	public void update(final Invoice i, final Date date) {
		i.setInvoiceDate(date);
	}

	/**
	 * Updates an invoice using its ID as parameter with a new Date passed by
	 * parameter
	 * 
	 * @param id
	 *            Integer representing the ID of the invoice
	 * @param date
	 *            the new Date for the invoice
	 */
	public void updateById(final int id, final Date date) {
		Invoice toUpdate = get(id);
		toUpdate.setInvoiceDate(date);
	}

	/**
	 * Delete the invoice passed by parameter
	 * 
	 * @param i
	 *            An invoice object that will be deleted from the list
	 */
	public void delete(final Invoice i) {
		super.remove(i);
	}

	/**
	 * Deleted the invoice that correspond to the ID passed by parameter
	 * 
	 * @param id
	 *            An integer that represents the ID field of the invoice
	 */
	public void deleteById(final int id) {
		super.remove(id);
	}

	@Override
	public String toString() {
		String result = "";
		for (Invoice inv : this) {
			result += indexOf(inv) + ": " + inv.toString() + "\n";
		}
		return result;
	}
}

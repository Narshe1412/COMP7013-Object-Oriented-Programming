//TODO refactoring
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceList extends ArrayList<Invoice> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public InvoiceList() {
		super();
	}
	
	public Invoice addNew() {
		Invoice inv = new Invoice(new Date());
		super.add(inv);
		inv.setInvoiceID(indexOf(inv));
		return inv;
	}
	
	public void update(final Invoice i, final Date date) {
		i.setInvoiceDate(date);
	}
	
	public void updateById(final int id, final Date date) {
		Invoice toUpdate = get(id);
		toUpdate.setInvoiceDate(date);
	}
	
	public void delete(final Invoice i) {
		super.remove(i);
	}
	
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

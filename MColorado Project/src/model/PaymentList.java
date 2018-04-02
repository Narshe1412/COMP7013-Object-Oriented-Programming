package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PaymentList extends ArrayList<Payment> implements Serializable{

	private static final long serialVersionUID = 1L;

	public PaymentList() {
		super();
	}
	
	public Payment addNew(final double amount) {
		Payment p = new Payment(amount);
		super.add(p);
		p.setPaymentID(indexOf(p));
		return p;
	}
	
	public void update(final Payment i, final double amount, final Date date) {
		i.setPaymentAmt(amount);
		i.setPaymentDate(date);
	}
	
	public void updateById(final int id, final double amount, final Date date) {
		Payment toUpdate = get(id);
		toUpdate.setPaymentAmt(amount);
		toUpdate.setPaymentDate(date);
	}
	
	public void delete(final Payment i) {
		super.remove(i);
	}
	
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
}

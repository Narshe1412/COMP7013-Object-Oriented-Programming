package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Invoice {

	private static int id = 0;
	private int invoiceID;
	private double invoiceAmt;
	private Date invoiceDate;
	private boolean isPaid;
	private List<Procedure> in_procList;
	private List<Payment> in_paymentList;
	
	public Invoice(Date date) {
		setInvoiceDate(date);
		objectSetup();
	}
	
	public Invoice(String date) {
		setInvoiceDate(date);
		objectSetup();
	}
	
	private void objectSetup() {
		setInvoiceID();
		in_paymentList = new ArrayList<Payment>();
		in_procList = new ArrayList<Procedure>();
		setPaid(false);
		invoiceAmt = 0;
	}
	
	public void setInvoiceDate(Date date) {
		this.invoiceDate = date;
	}
	
	public void setInvoiceDate(String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date parsedDate = df.parse(date);
			setInvoiceDate(parsedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}
	
	public int getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID() {
		this.invoiceID = id;
		id++;
	}

	public double getInvoiceAmt() {
		calculateInvoiceAmt();
		return invoiceAmt;
	}
	
	public double calculateInvoiceAmt() {
		// Calculates total spent and total paid
		// Assigns total spent to invoiceAmt property
		// Returns remaining to pay
		double total = 0;
		for (Procedure proc: in_procList) {
			total += proc.getProcCost();
		}
		invoiceAmt = total;
		
		double paid = 0;
		for (Payment payment: in_paymentList) {
			paid += payment.getPaymentAmt();
		}
		if (total - paid > 0) {
			return total - paid;
		} else {
			setPaid(true);
			return 0;
		}
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Collection<Procedure> getIn_procList() {
		return in_procList;
	}

	public Collection<Payment> getIn_paymentList() {
		return in_paymentList;
	}
}

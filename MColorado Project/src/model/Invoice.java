package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Invoice implements Serializable{

	//private static int id = 0;
	private int invoiceID;
	private double invoiceAmt;
	private Date invoiceDate;
	private boolean isPaid;
	private final List<Procedure> in_procList = new ArrayList<Procedure>();
	private final List<Payment> in_paymentList = new ArrayList<Payment>();
	
	public Invoice() {
		setInvoiceDate(new Date());
		objectSetup();
	}
	
	public Invoice(final Date date) {
		setInvoiceDate(date);
		objectSetup();
	}
	
	public Invoice(final String date) {
		setInvoiceDate(date);
		objectSetup();
	}
	
	private void objectSetup() {
		//setInvoiceID();
		setPaid(false);
		invoiceAmt = 0;
	}
	
	public void setInvoiceDate(final Date date) {
		this.invoiceDate = date;
	}
	
	public void setInvoiceDate(final String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("date-" + date);
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
	
	public String getStringDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(getInvoiceDate());
	}
	
	public int getInvoiceID() {
		return invoiceID;
	}

	/*public void setInvoiceID() {
		this.invoiceID = id;
		id++;
	}*/
	
	public void setInvoiceID(final int i) {
		this.invoiceID = i;
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
			total += proc.getProcCost().get();
		}
		invoiceAmt = total;
		
		double paid = calculateInvoicePaid();
		if ((total - paid) > 0) {
			setPaid(false);
			return total - paid;
		} else {
			setPaid(true);
			return 0;
		}
	}
	
	public double calculateInvoicePaid() {
		double paid = 0;
		for (Payment payment: in_paymentList) {
			paid += payment.getPaymentAmt().get();
		}
		return paid;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(final boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Collection<Procedure> getIn_procList() {
		return in_procList;
	}

	public Collection<Payment> getIn_paymentList() {
		return in_paymentList;
	}
	
	public boolean addProcedure(final Procedure proc) {
		boolean result = getIn_procList().add(proc);
		calculateInvoiceAmt();
		System.out.println("ispaid " +  isPaid() + "total" );
		return result;
	}
	
	public boolean removeProcedure(final Procedure proc) {
		boolean result =  getIn_procList().remove(proc);
		calculateInvoiceAmt();
		return result;
	}
	
	public boolean addPayment(final Payment pay) {
		boolean result = getIn_paymentList().add(pay);
		calculateInvoiceAmt();
		return result;
	}
	
	public boolean removePayment(final Payment pay) {
		boolean result =  getIn_paymentList().remove(pay);
		calculateInvoiceAmt();
		return result;
	}
}

package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Invoice {

	private static int id = 0;
	private int invoiceID;
	private double invoiceAmt;
	private Date invoiceDate;
	private boolean isPaid;
	private ArrayList<Procedure> in_procList;
	private ArrayList<Payment> in_paymentList;
	
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
		this.in_paymentList = new ArrayList<Payment>();
		this.in_procList = new ArrayList<Procedure>();
		setPaid(false);
		setInvoiceAmt(0);
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
		return invoiceAmt;
	}

	public void setInvoiceAmt(double invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	
	public double calculateInvoiceAmt() {
		//TODO
		return 0;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	//TODO add a single procedure and payment
	
	public ArrayList<Procedure> getIn_procList() {
		return in_procList;
	}

	public ArrayList<Payment> getIn_paymentList() {
		return in_paymentList;
	}

}

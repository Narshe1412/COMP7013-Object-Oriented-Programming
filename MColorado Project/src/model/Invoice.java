package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import exception.ExceptionDialog;
/**
 * 
 * @author Manuel Colorado
 *
 */
@SuppressWarnings("serial")
public class Invoice implements Serializable{

	//private static int id = 0;
	private int invoiceID;
	private double invoiceAmt;
	private Date invoiceDate;
	private boolean isPaid;
	private final List<Procedure> in_procList = new ArrayList<Procedure>();
	private final List<Payment> in_paymentList = new ArrayList<Payment>();
	
	/**
	 * Default Constructor
	 */
	public Invoice() {
		setInvoiceDate(new Date());
		objectSetup();
	}
	
	/**
	 * Constructor using Date object for dates
	 * @param date a Date object containing the date of the invoice
	 */
	public Invoice(final Date date) {
		setInvoiceDate(date);
		objectSetup();
	}
	
	/**
	 * Constructor using a String with the date
	 * @param date a string representing a Date
	 */
	public Invoice(final String date) {
		setInvoiceDate(date);
		objectSetup();
	}
	
	/**
	 * Sets initial values for an Invoice
	 */
	private void objectSetup() {
		//setInvoiceID();
		setPaid(false);
		invoiceAmt = 0;
	}
	
	/**
	 * Sets up the invoice date
	 * @param date A Date object with the new date for the invoice
	 */
	public void setInvoiceDate(final Date date) {
		this.invoiceDate = date;
	}
	
	/**
	 * Sets up the invoice date using a string with the format dd/MM/yyyy
	 * @param date a string representing a date in the forma dd/MM/yyyy
	 */
	public void setInvoiceDate(final String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date parsedDate = df.parse(date);
			setInvoiceDate(parsedDate);
		} catch (ParseException e) {
			ExceptionDialog exWin = new ExceptionDialog("Date Parse Error", "The Date provided is not in the correct dd/MM/yyyy format.", e);
			exWin.show();
		}
	}

	/**
	 * Returns the date of the invoice
	 * @return A Date object containing the date from this invoice
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	
	/**
	 * Returns the date of the invoice
	 * @return A String with the format of dd/MM/yyyy containing the date of the invoice
	 */
	public String getStringDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(getInvoiceDate());
	}
	
	/**
	 * Returns the ID of the invoice
	 * @return an integer detailing the ID of the invoice
	 */
	public int getInvoiceID() {
		return invoiceID;
	}

	/*public void setInvoiceID() {
		this.invoiceID = id;
		id++;
	}*/
	
	/**
	 * Sets up the ID for the invoice
	 * @param i An integer containing the ID of the invoice
	 */
	public void setInvoiceID(final int i) {
		this.invoiceID = i;
	}

	/**
	 * Calculates the amount total for the invoice 
	 * @return a double value with the calculation of all the invoice value
	 */
	public double getInvoiceAmt() {
		calculateInvoiceAmt();
		return invoiceAmt;
	}
	
	/**
	 * Calculates the total invoice value
	 * @return a double value with the remaining amount to pay for the invoice
	 */
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
	
	/**
	 * Calculate the amount of payments that have been done against the invoice
	 * @return a double value with the result of the sum of all invoices paid
	 */
	public double calculateInvoicePaid() {
		double paid = 0;
		for (Payment payment: in_paymentList) {
			paid += payment.getPaymentAmt().get();
		}
		return paid;
	}

	/**
	 * Returns the value of the isPaid variable of this invoice
	 * @return true if the invoice is paid, false otherwise
	 */
	public boolean isPaid() {
		return isPaid;
	}

	/**
	 * Sets if the invoice is correctly paid or not
	 * @param isPaid a true or false value requested by the calculation
	 */
	public void setPaid(final boolean isPaid) {
		this.isPaid = isPaid;
	}

	/**
	 * Gets a collection of Procedure items related to this invoice
	 * @return a collection of Procedure objects associated with this Invoice
	 */
	public Collection<Procedure> getIn_procList() {
		return in_procList;
	}

	/**
	 * Gets a collection of Payments done against this Invoice
	 * @return a Collection of Payment objects associated with this invoice
	 */
	public Collection<Payment> getIn_paymentList() {
		return in_paymentList;
	}
	
	/**
	 * Adds a new procedure to the Invoice and calculates the new AmountPaid and isPaid values 
	 * @param proc reference to the Procedure that will be added to this invoice
	 * @return a boolean value with the result of the Add operation
	 */
	public boolean addProcedure(final Procedure proc) {
		boolean result = getIn_procList().add(proc);
		calculateInvoiceAmt();
		return result;
	}
	
	/**
	 * Removes a procedure passed by parameter from the Invoice and calculates the new values for Paid/isPaid
	 * @param proc Procedure that will be removed from the Invoice
	 * @return a boolean value with the result of the operation
	 */
	public boolean removeProcedure(final Procedure proc) {
		boolean result =  getIn_procList().remove(proc);
		calculateInvoiceAmt();
		return result;
	}
	
	/**
	 * Adds a new Payment to the Invoice and calculates the new AmountPaid and isPaid values 
	 * @param pay reference to the Payment that will be added to this invoice
	 * @return a boolean value with the result of the Add operation
	 */
	public boolean addPayment(final Payment pay) {
		boolean result = getIn_paymentList().add(pay);
		calculateInvoiceAmt();
		return result;
	}
	
	/**
	 * Removes a payment passed by parameter from the Invoice and calculates the new values for Paid/isPaid
	 * @param pay Payment that will be removed from the Invoice
	 * @return a boolean value with the result of the operation
	 */
	public boolean removePayment(final Payment pay) {
		boolean result =  getIn_paymentList().remove(pay);
		calculateInvoiceAmt();
		return result;
	}
}

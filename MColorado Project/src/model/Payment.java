package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import exception.ExceptionDialog;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
/**
 * Stores the information related to a Payment made by a customer
 * 
 * @author Manuel Colorado
 *
 */
public class Payment implements Serializable {
	// private static int id = 0;
	private int paymentID;
	private double paymentAmt;
	private Date paymentDate;

	/**
	 * Constructor
	 * 
	 * @param amount
	 *            a numeric value representing the amount paid
	 */
	public Payment(final double amount) {
		setPaymentAmt(amount);
		setPaymentDate(new Date());
	}

	/**
	 * Constructor
	 * 
	 * @param amount
	 *            a numeric value representing the amount paid
	 * @param date
	 *            a Date object representing the date the payment was made
	 */
	public Payment(final double amount, final Date date) {
		setPaymentAmt(amount);
		setPaymentDate(date);
	}

	/**
	 * Constructor
	 * 
	 * @param amount
	 *            a numeric value representing the amount paid
	 * @param date
	 *            a string representing the date the payment was made in dd/MM/yyyy
	 *            format
	 */
	public Payment(final double amount, final String date) {
		setPaymentAmt(amount);
		setPaymentDate(date);
	}

	/**
	 * Constructor
	 * 
	 * @param amount
	 *            a numeric value representing the amount paid
	 * @param date
	 *            a LocalDate object representing the date the payment was made
	 */
	public Payment(double amount, LocalDate date) {
		setPaymentAmt(amount);
		setPaymentDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}

	/**
	 * Obtains the DoubleProperty object based on the value of the field amount
	 * 
	 * @return a DoublePorperty object
	 */
	public DoubleProperty getPaymentAmt() {
		return new SimpleDoubleProperty(paymentAmt);
	}

	/**
	 * Set the value of the payment
	 * 
	 * @param paymentAmt
	 *            a numeric value that represents a payment
	 */
	public void setPaymentAmt(final double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	/**
	 * Obtains the Date object value registered in the Payment object
	 * 
	 * @return a Date object with the date recorded
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Modifies the date of the payment object
	 * 
	 * @param paymentDate
	 *            a Date object thare represents a Payment date
	 */
	public void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * Modifies the date of the payment object
	 * 
	 * @param date
	 *            a string representing a date in the format dd/MM/yyyy
	 */
	public void setPaymentDate(final String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date parsedDate = df.parse(date);
			setPaymentDate(parsedDate);
		} catch (ParseException e) {
			ExceptionDialog exWin = new ExceptionDialog("Date Error", "Error when parsing date value", e);
			exWin.show();
		}
	}

	/**
	 * Obtains the payment id from the object
	 * 
	 * @return an integer value that represents the id of the object
	 */
	public int getPaymentID() {
		return paymentID;
	}

	/**
	 * Changes the id registered for the object
	 * 
	 * @param id
	 *            a numeric value that represents the object
	 */
	public void setPaymentID(int id) {
		/*
		 * this.paymentID = id; id++;
		 */
		this.paymentID = id;
	}

	public String toString() {
		return "Date: " + getPaymentStringDate().get() + "\tTotal: " + getPaymentAmt().get() + " $";
	}

	/**
	 * Prints the value of the toString() on the JVM console
	 */
	public void print() {
		System.out.println(toString());
	}

	/**
	 * Obtains an observable StringProperty based on the registered date on the
	 * object
	 * 
	 * @return a StringProperty object
	 */
	public StringProperty getPaymentStringDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return new SimpleStringProperty(df.format(getPaymentDate()));

	}
}

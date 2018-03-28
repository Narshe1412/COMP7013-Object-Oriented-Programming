package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Payment implements Serializable{
	private static int id = 0;
	private int paymentID;
	private DoubleProperty paymentAmt;
	private Date paymentDate;
	
	public Payment (final double amount) {
		setPaymentAmt(amount);
		setPaymentDate(new Date());
		setPaymentID();
	}
	
	public Payment (final double amount, final Date date) {
		setPaymentAmt(amount);
		setPaymentDate(date);
		setPaymentID();
	}
	
	public Payment (final double amount, final String date) {
		setPaymentAmt(amount);
		setPaymentDate(date);
		setPaymentID();
	}

	public DoubleProperty getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(final double paymentAmt) {
		this.paymentAmt = new SimpleDoubleProperty(paymentAmt);
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(final Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	public void setPaymentDate(final String date) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date parsedDate = df.parse(date);
			setPaymentDate(parsedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID() {
		this.paymentID = id;
		id++;
	}
	
	public String toString() {
		return "[" + getPaymentID() + "] Total: $" + getPaymentAmt() + " Date: " +  getPaymentDate();
	}
	
	public void print() {
		System.out.println(toString());
	}

	public StringProperty getPaymentStringDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return new SimpleStringProperty(df.format(getPaymentDate()));
		
	}
}

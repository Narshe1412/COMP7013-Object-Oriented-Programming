package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Payment implements Serializable{
	//private static int id = 0;
	private int paymentID;
	private double paymentAmt;
	private Date paymentDate;
	
	public Payment (final double amount) {
		setPaymentAmt(amount);
		setPaymentDate(new Date());
	}
	
	public Payment (final double amount, final Date date) {
		setPaymentAmt(amount);
		setPaymentDate(date);
	}
	
	public Payment (final double amount, final String date) {
		setPaymentAmt(amount);
		setPaymentDate(date);
	}

	public Payment(double amount, LocalDate date) {
		setPaymentAmt(amount);
		setPaymentDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	}

	public DoubleProperty getPaymentAmt() {
		return new SimpleDoubleProperty(paymentAmt);
	}
	
	public void setPaymentAmt(final double paymentAmt) {
		this.paymentAmt = paymentAmt;
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

	public void setPaymentID(int id) {
		/*this.paymentID = id;
		id++;*/
		this.paymentID =id;
	}
	
	public String toString() {
		return "Date: " +  getPaymentStringDate().get() + "\tTotal: " + getPaymentAmt().get() + " $";
	}
	
	public void print() {
		System.out.println(toString());
	}

	public StringProperty getPaymentStringDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return new SimpleStringProperty(df.format(getPaymentDate()));
		
	}
}

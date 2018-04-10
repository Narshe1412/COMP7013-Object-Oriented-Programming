package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Creates a Patient object in the system
 * 
 * @author Manuel Colorado
 *
 */
@SuppressWarnings("serial")
public class Patient extends Person {
	// private static int id = 0;
	private int patientNo;
	private final List<Invoice> p_invoiceList;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            a string representing the name of the Patient
	 * @param address
	 *            a string representing the address of the Patient
	 */
	public Patient(final String name, final String address) {
		super(name, address);
		p_invoiceList = new ArrayList<Invoice>();
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            a string representing the name of the Patient
	 * @param address
	 *            a string representing the address of the Patient
	 * @param phone
	 *            a string representing the phone of the patient
	 */
	public Patient(String name, String address, String phone) {
		super(name, address, phone);
		// setPatientNo();
		p_invoiceList = new ArrayList<Invoice>();
	}

	/**
	 * Obtains the ID of the patient registered in the system
	 * 
	 * @return a numeric value that represents the ID the patient has on the system
	 */
	public int getPatientNo() {
		return patientNo;
	}

	/**
	 * Configures the ID of the patient
	 * 
	 * @param id
	 *            a numeric value that represents the ID the patient will have on
	 *            the system
	 */
	public void setPatientNo(int id) {
		patientNo = id;
		// this.patientNo = id;
		// id++;
	}

	/**
	 * Obtains the list of Invoice elements associated with this Patient
	 * 
	 * @return a Collection of Invoice items
	 */
	public Collection<Invoice> getP_invoiceList() {
		return p_invoiceList;
	}

	/**
	 * Obtains the total amount of all the invoices for this Patient
	 * 
	 * @return a numeric representing the sum of all invoices on the system
	 */
	public double getTotalInvoiceValue() {
		double total = 0;
		for (Invoice inv : getP_invoiceList()) {
			total += inv.getInvoiceAmt();
		}
		return total;
	}

	/**
	 * Obtains the total amount left to pay for a Patient in the system
	 * 
	 * @return a numeric representation of the sum of the remaining value of all the
	 *         invoices
	 */
	public double getRemainingInvoiceValue() {
		double total = 0;
		for (Invoice inv : getP_invoiceList()) {
			if (!inv.isPaid()) {
				total += inv.calculateInvoiceAmt();
			}
		}
		return total;
	}

	/**
	 * Adds a new invoice to this Patient
	 * 
	 * @param inv
	 *            an Invoice object that will be associated to this patient
	 * @return a true or false result representing if the operation was successful
	 */
	public boolean addInvoice(final Invoice inv) {
		return getP_invoiceList().add(inv);
	}

	/**
	 * Removes an invoice from this Patient
	 * 
	 * @param inv
	 *            an Invoice object that will be removed from this patient
	 * @return a true or false result representing if the operation was successful
	 */
	public boolean removeInvoice(final Invoice inv) {
		return getP_invoiceList().remove(inv);
	}

	public String toString() {
		return "Name: " + getName() + "\n\tAddress: " + getAddress() + "\tPhone: " + getPhone();
	}
}

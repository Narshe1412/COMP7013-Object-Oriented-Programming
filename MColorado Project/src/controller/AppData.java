package controller;

import java.util.List;

import model.*;

/**
 * Stores the data for the application
 * 
 * @author Manuel Colorado
 *
 */
public enum AppData {
	INSTANCE;

	private Dentist savedUser;

	/**
	 * Stores the information regarding the current saved user to bypass Login
	 * screen
	 * 
	 * @return a Dentist object with all the details for the current user
	 */
	public Dentist getSavedUser() {
		return savedUser;
	}

	/**
	 * Sets the current user as saved user, to bypass the login screen in future
	 * uses of the application
	 * 
	 * @param user
	 *            a Dentist object with all the user details
	 */
	public void setSavedUser(final Dentist user) {
		this.savedUser = user;
	}

	private DentistList userList;

	/**
	 * Gets the list of the complete list of users for the system
	 * 
	 * @return a list of type DentistList with all the users for the system
	 */
	public DentistList getUserList() {
		return userList;
	};

	/**
	 * Sets a list passed by parameter as the list of users for this system
	 * 
	 * @param userList
	 *            an object containing the list of users for the system
	 */
	public void setUserList(final List<Dentist> userList) {
		this.userList = (DentistList) userList;
	}

	private PatientList patientList;

	/**
	 * Gets the complete list of patients as used by the system
	 * 
	 * @return A PatientList object containing all the patients
	 */
	public PatientList getPatientList() {
		return patientList;
	}

	/**
	 * Sets up the list of patients that will be used by the system
	 * 
	 * @param patientList
	 *            An object containing the List of patients
	 */
	public void setPatientList(List<Patient> patientList) {
		this.patientList = (PatientList) patientList;

	};

	private InvoiceList invoiceList;

	/**
	 * Obtains the list of Invoices of the system
	 * 
	 * @return an InvoiceList object recorded in the system
	 */
	public InvoiceList getInvoiceList() {
		return invoiceList;
	}

	/**
	 * Set up a list of invoices in the system, passed by parameter
	 * 
	 * @param invoiceList
	 *            a list of invoices recorded in the system
	 */
	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = (InvoiceList) invoiceList;
	}

	private ProcedureList procList;

	/**
	 * Obtains the list of procedures stored in the system
	 * 
	 * @return a ProcedureList object containing the list of Procedures stored in
	 *         the system
	 */
	public ProcedureList getProcedureList() {
		return procList;
	}

	/**
	 * Set up a list of Procedures in the system
	 * 
	 * @param procedureList
	 *            the list of procedures that will be stored in the system
	 */
	public void setProcedureList(List<Procedure> procedureList) {
		this.procList = (ProcedureList) procedureList;
	}

	private PaymentList paymentList;

	/**
	 * Obtains the list the payments stored in the system
	 * 
	 * @return a PaymentList object with all the payments stored in the system
	 */
	public PaymentList getPaymentList() {
		return paymentList;
	}

	/**
	 * Set up a list of payments that will store in the system
	 * 
	 * @param paymentList
	 *            a list of Payment objects recorded in the system
	 */
	public void setPaymentList(List<Payment> paymentList) {
		this.paymentList = (PaymentList) paymentList;
	}

}

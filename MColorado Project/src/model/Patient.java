package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Patient extends Person {
	private static int id = 0;
	private int patientNo;
	private final List<Invoice> p_invoiceList;
	
	public Patient(final String name, final String address) {
		super(name, address);
		setPatientNo();
		p_invoiceList = new ArrayList<Invoice>();
	}

	public int getPatientNo() {
		return patientNo;
	}
	
	public void setPatientNo() {
		this.patientNo = id;
		id++;
	}

	public Collection<Invoice> getP_invoiceList() {
		return p_invoiceList;
	}
	
	public boolean addInvoice(final Invoice inv) {
		return getP_invoiceList().add(inv);
	}
	
	public boolean removeInvoice(final Invoice inv) {
		return getP_invoiceList().remove(inv);
	}
}

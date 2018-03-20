package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
public class Patient extends Person {
	private static int id = 0;
	private int patientNo;
	private final List<Invoice> p_invoiceList;

	public Patient(final String name, final String address) {
		super(name, address);
		p_invoiceList = new ArrayList<Invoice>();
	}

	public Patient(String name, String address, String phone) {
		super(name, address, phone);
		setPatientNo();
		p_invoiceList = new ArrayList<Invoice>();
	}

	public int getPatientNo() {
		return patientNo;
	}

	private void setPatientNo() {
		// Only used for the constructor. Won't give public access.
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

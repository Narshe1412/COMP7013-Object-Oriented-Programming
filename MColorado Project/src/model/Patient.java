package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Patient extends Person {
	private static int id = 0;
	private int patientNo;
	private List<Invoice> p_invoiceList;
	
	public Patient(String name, String address) {
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

	public Collection getP_invoiceList() {
		return p_invoiceList;
	}
}

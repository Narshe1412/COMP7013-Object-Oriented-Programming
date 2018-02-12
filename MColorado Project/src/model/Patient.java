package model;

import java.util.ArrayList;

public class Patient extends Person {
	private static int id = 0;
	private int patientNo;
	private ArrayList<Invoice> p_invoiceList;
	
	public Patient(String name, String address) {
		super(name, address);
		setPatientNo();
	}

	public int getPatientNo() {
		return patientNo;
	}
	
	public void setPatientNo() {
		this.patientNo = id;
		id++;
	}

	public ArrayList<Invoice> getP_invoiceList() {
		return p_invoiceList;
	}

	// TODO Add Invoice to this patient
	
}

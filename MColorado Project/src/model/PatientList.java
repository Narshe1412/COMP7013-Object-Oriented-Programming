package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles the serializable list of customers in the system
 * @author Manuel Colorado
 *
 */
public class PatientList extends ArrayList<Patient> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public PatientList() {
		super();
	}
	
	/**
	 * Locates the Patient object based on their name
	 * @param name String that represents the name of the patient
	 * @return the Patient object with all relevant details of the patient or null if it cannot finds it
	 */
	public Patient find(String name) {
		for (Patient patient: this) {
			if (patient.getName().equalsIgnoreCase(name)) {
				return patient;
			}
		}
		return null;
	}
	
	public Patient add(String name, String address, String phone) {
		Patient p = new Patient(name, address,phone); 
		super.add(p);
		p.setPatientNo(indexOf(p));
		return p;
	}
	
	public void update(Patient p, String name, String address, String phone) {
		p.setName(name);
		p.setAddress(address);
		p.setPhone(phone);
	}
	
	public void updateById(int id, String name, String address, String phone) {
		Patient toUpdate = get(id);
		toUpdate.setName(name);
		toUpdate.setAddress(address);
		toUpdate.setPhone(phone);
	}
	
	public String toString() {
		String result = "";
		for (Patient patient: this) {
			result += indexOf(patient) + ": " + patient.toString() + "\n";
		}
		return result;
	}
}

//public class PatientList {
/*	
	private AtomicInteger id = new AtomicInteger();
	
	public PatientList() {
		this(0);
	}
	
	public PatientList(int id) {
		this.id = (int)id;
	}

	public int getNextId() {
		return id.getAndIncrement();
	}*/
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles the serializable list of customers in the system
 * 
 * @author Manuel Colorado
 *
 */
public class PatientList extends ArrayList<Patient> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public PatientList() {
		super();
	}

	/**
	 * Locates the Patient object based on their name
	 * 
	 * @param name
	 *            String that represents the name of the patient
	 * @return the Patient object with all relevant details of the patient or null
	 *         if it cannot finds it
	 */
	public Patient find(String name) {
		for (Patient patient : this) {
			if (patient.getName().equalsIgnoreCase(name)) {
				return patient;
			}
		}
		return null;
	}

	/**
	 * Adds a new Patient object to the list of patients in the system
	 * 
	 * @param name
	 *            String that represents the name of the patient
	 * @param address
	 *            String that represents the address of the patient
	 * @param phone
	 *            String that represents the phone of the patient
	 * @return the reference to the patient already in the list
	 */
	public Patient add(final String name, final String address, final String phone) {
		Patient p = new Patient(name, address, phone);
		super.add(p);
		p.setPatientNo(indexOf(p));
		return p;
	}

	/**
	 * Update a patient object in the system by providing the object reference
	 * 
	 * @param p
	 *            Patient object reference to the patient in the list
	 * @param name
	 *            String that represents the name of the patient
	 * @param address
	 *            String that represents the address of the patient
	 * @param phone
	 *            String that represents the phone of the patient
	 */
	public void update(final Patient p, final String name, final String address, final String phone) {
		p.setName(name);
		p.setAddress(address);
		p.setPhone(phone);
	}

	/**
	 * Update a patient object in the system by providing the position in the list
	 * 
	 * @param id
	 *            Id of the patient object in the list
	 * @param name
	 *            String that represents the name of the patient
	 * @param address
	 *            String that represents the address of the patient
	 * @param phone
	 *            String that represents the phone of the patient
	 */
	public void updateById(final int id, final String name, final String address, final String phone) {
		Patient toUpdate = get(id);
		toUpdate.setName(name);
		toUpdate.setAddress(address);
		toUpdate.setPhone(phone);
	}

	/**
	 * Deletes a Patient object from the list provided its id
	 * 
	 * @param id
	 *            numeric identifier to delete
	 */
	public void deleteById(final int id) {
		super.remove(id);
	}

	/**
	 * Delete a Patient object from the list using its reference
	 * 
	 * @param p
	 *            reference to the Patient object in the list
	 */
	public void delete(final Patient p) {
		super.remove(p);
	}

	/**
	 * Override of the toString() method Obtains the list of patients in the format
	 * "ID: Name Address: ... Phone: ... \n"
	 */
	@Override
	public String toString() {
		String result = "";
		for (Patient patient : this) {
			result += indexOf(patient) + ": " + patient.toString() + "\n";
		}
		return result;
	}
}

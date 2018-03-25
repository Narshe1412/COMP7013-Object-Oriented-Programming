package controller;

import java.util.List;

import model.Dentist;
import model.DentistList;
import model.Patient;
import model.PatientList;

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
	 * @param userList an object containing the list of users for the system 
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

}

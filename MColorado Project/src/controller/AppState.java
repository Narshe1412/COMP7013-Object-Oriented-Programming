package controller;

import java.util.List;

import model.Dentist;
import model.DentistList;
import model.Patient;

/**
 * Singleton class to hold the state of the application
 * 
 * @author Manuel Colorado
 *
 */
public enum AppState {
	INSTANCE;
	/**
	 * @deprecated SQL will make a persistent state
	 */
	private boolean modified;

	private Dentist currentUser;
	private Patient currentPatient;
	private Patient previousPatient;

	private Dentist savedUser;

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

	/**
	 * Establishes the current user of the application
	 * 
	 * @return a Dentist object with the current user details of the system
	 */
	public Dentist getCurrentUser() {
		return currentUser;
	}

	/**
	 * Sets up the current user for the system, to establish permissions and options
	 * 
	 * @param user
	 *            a Dentist object that establishes the current user
	 */
	public void setCurrentUser(final Dentist user) {
		this.currentUser = user;
	}

	/**
	 * Returns the current patient selected for the system
	 * 
	 * @return a Patient object with all the details
	 */
	public Patient getCurrentPatient() {
		return currentPatient;
	}

	/**
	 * Establishes the current patient in the system
	 * 
	 * @param p
	 *            a Patient object that is currently being handled by the user
	 */
	public void setCurrentPatient(final Patient p) {
		currentPatient = p;
	}

	/**
	 * Establishes the previous patient for backup purposes
	 * 
	 * @param p
	 *            a Patient object
	 */
	public void setPreviousPatient(final Patient p) {
		previousPatient = p;
	};

	/**
	 * Gets the previous patient as stored in the system
	 * 
	 * @return a Patient object that was previously saved by the setPreviousPatient
	 *         method
	 */
	public Patient getPreviousPatient() {
		return previousPatient;
	}

}

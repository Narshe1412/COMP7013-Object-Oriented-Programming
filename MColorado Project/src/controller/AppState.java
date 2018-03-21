package controller;

import model.Dentist;
import model.Patient;

/**
 * Singleton class to hold the state of the application
 * 
 * @author Manuel Colorado
 *
 */
public enum AppState {
	INSTANCE;

	private boolean modified;

	/**
	 * Checks if any of the models have been modified and the app requires saving
	 * 
	 * @return true if modified, false if not
	 */
	public boolean isModified() {
		return modified;
	};

	/**
	 * Sets the modified state on the application
	 * 
	 * @param state
	 *            true to indicate some value has been modified and the model needs
	 *            to be saved
	 */
	public void setModified(final boolean state) {
		modified = state;
	};

	private Dentist currentUser;

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
	 */
	public void setCurrentUser(final Dentist user) {
		this.currentUser = user;
	}

	private Patient currentPatient;

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
	};

}

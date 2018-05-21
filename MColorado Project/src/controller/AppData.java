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
}

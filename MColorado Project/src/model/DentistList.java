package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the serializable list of users in the system
 * @author Manuel Colorado
 * @param <T>
 *
 */
public class DentistList extends ArrayList<Dentist> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 */
	public DentistList() {
		super();
	}
	
	/**
	 * Locates the Dentist object based on their username
	 * @param username String that represents the username of the user
	 * @return the Dentist object with all relevant details of the user or null if it cannot finds it
	 */
	public Dentist find(String username) {
		for (Dentist dentist: this) {
			if (dentist.getUsername().equalsIgnoreCase(username)) {
				return dentist;
			}
		}
		return null;
	}
}

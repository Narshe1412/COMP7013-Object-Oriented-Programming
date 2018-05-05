package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the serializable list of users in the system
 * @author Manuel Colorado

 */
public abstract class ElementList<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private final ArrayList<T> elements;
	
	/**
	 * Constructor
	 */
	public ElementList() {
		elements = new ArrayList<T>();
	}
	
	/**
	 * Locates the Dentist object based on their username
	 * @param username String that represents the username of the user
	 * @return the Dentist object with all relevant details of the user or null if it cannot finds it
	 */
	public abstract T find(String criteria);

	/**
	 * Adds a new dentist to the system and return the reference for this object
	 * @param pDentist a Dentist object that will be added to the system
	 * @return the Dentist object just created
	 */
	public T addNew(T pElement) {
		if(elements.add(pElement)) {
			return pElement;
		}
		return null;
	}
}

package model;

import java.io.Serializable;

@SuppressWarnings("serial")
/**
 * 
 * @author Manuel Colorado
 * 
 * Class with package access level. No need to make it public
 */
class Person implements Serializable{
	
	private String name;
	private String address;
	private String phone;
	
	/**
	 * Constructor
	 * @param name Name of the person
	 * @param address Address of the person
	 * @param phone Phone of the person
	 */
	public Person(final String name, final String address, final String phone) {
		setName(name);
		setAddress(address);
		setPhone(phone);
	}
	
	/**
	 * Constructor
	 * @param name Name of the person
	 * @param address Address of the person
	 */
	public Person(final String name, final String address) {
		setName(name);
		setAddress(address);
		setPhone("");
	}
	
	/**
	 * Provides the name of the person
	 * @return a string with the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * Changes the name of the user with the string provided
	 * @param name a String that identifies the name of the person
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Provides the address of the person
	 * @return a string with the recorded address of the person
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Changes the address of the person with the string provided
	 * @param address a string that identifies the address of a person
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * Provides the phone of the person
	 * @return a string with the recorded phone of the person
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Changes the phone of the person with the string provided
	 * @param phone a string with the new phone value for the person
	 */
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		return name + "\nAddress: " + address +  "\nPhone:" + phone;
	}

	/**
	 * Calls the method toString() and prints them in the JVM console
	 */
	public void print() {
		System.out.println(toString());
	}
}

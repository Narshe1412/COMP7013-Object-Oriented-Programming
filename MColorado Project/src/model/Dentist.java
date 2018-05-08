package model;

import exception.PassException;

/**
 * 
 * @author Manuel Colorado
 * @version 1.0
 */
public class Dentist extends Person {
	private static final long serialVersionUID = 1L;
	private int userNo;
	private String username;
	private String password;
	private final transient String sk = "h4xx0rPRIVVYk3yl33t";

	/**
	 * Creates a new dentist in the system using their personal details
	 * 
	 * @param name
	 *            Name of the dentist
	 * @param address
	 *            Address of the dentist
	 * @param phone
	 *            Phone of the dentist
	 */
	public Dentist(final String name, final String address, final String phone) {
		super(name, address, phone);
	}

	/**
	 * Creates a new user in the system, using the username as name in the system
	 * and leaving address and phone as xxx
	 * 
	 * @param username
	 *            username for the system
	 * @param password
	 *            password for the system, that would be hashed
	 * @throws PassException
	 *             throws exception if password cannot be hashed
	 */
	public Dentist(final String username, final String password) throws PassException {
		super(username, "xxx", "xxx");
		setUsername(username);
		setPassword(password);
	}

	/**
	 * Creates a new user in the system, with all needed parameters
	 * 
	 * @param name
	 *            Name of the dentist
	 * @param address
	 *            Address for the dentist
	 * @param phone
	 *            Phone for the dentist
	 * @param username
	 *            Username that will be used in the system
	 * @param password
	 *            Password that will be used in the system, it will be hashed
	 * @throws PassException
	 *             throws exception if password cannot be hashed
	 */
	public Dentist(final String name, final String address, final String phone, final String username,
			final String password) throws PassException {
		super(name, address, phone);
		setUsername(username);
		setPassword(password);
	}

	/**
	 * Creates a new user in the system, with all needed parameters minus the
	 * password
	 * 
	 * @param name
	 *            Name of the dentist
	 * @param address
	 *            Address for the dentist
	 * @param phone
	 *            Phone for the dentist
	 * @param username
	 *            Username that will be used in the system
	 */
	public Dentist(final String name, final String address, final String phone, final String username) {
		super(name, address, phone);
		setUsername(username);
	}

	/**
	 * Changes the username for the dentist
	 * 
	 * @param username
	 *            new username to be changed in the system
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * Gets username for this instance of the Dentist class
	 * 
	 * @return a string with the username of this Dentist
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Creates a hashed password in the system for this user
	 * 
	 * @param password
	 *            string that will be hashed and stored for this user
	 * @throws PassException
	 *             throws exception if password cannot be hashed
	 */
	public void setPassword(final String password) throws PassException {
		this.password = PasswordHandler.encrypt(password, sk);
	}

	/**
	 * Stores a hashed password from the database
	 * 
	 * @param password
	 *            an encoded Base64 string that contains the hashed password
	 */
	public void setHashedPassword(final String password) {
		this.password = password;
	}

	/**
	 * Obtains the unhashed password from the user
	 * 
	 * @return the unhashed password from the user
	 * @throws PassException
	 *             if password cannot be decrypted
	 */
	@SuppressWarnings("unused")
	private String getPassword() throws PassException {
		return PasswordHandler.decrypt(password, sk);
	}

	/**
	 * Returns the stored hashed password from the system without decoding it
	 * 
	 * @return an encoded Base64 string that represents a hashed password
	 */
	public String getHashedPassword() {
		return password;
	}

	/**
	 * Checks if the password passed by parameter matches the one stored and returns
	 * true/false based on this match
	 * 
	 * @param password
	 *            string that contains the password we want to check for a match
	 * @return true if the password matches, false if it doesn't
	 * @throws PassException
	 *             throws exception if password cannot be encrypted
	 */
	public boolean verifyPassword(String password) throws PassException {
		return PasswordHandler.encrypt(password, sk).equalsIgnoreCase(this.password);
	}

	/**
	 * Produces the details from the user in string format
	 * 
	 * @return string with the details of the user, which are Name, Address, Phone
	 *         (from parent class) and Username (not password)
	 */
	public String toString() {
		return super.toString() + "\nUsername: " + getUsername();
	}

	/**
	 * Prints in the console the details of this user
	 */
	public void print() {
		System.out.println(toString());
	}

	/**
	 * Gets the user id in the system
	 * 
	 * @return an integer representation of the user id
	 */
	public int getUserNo() {
		return userNo;
	}

	/**
	 * Set up the user id in the system
	 * 
	 * @param userNo
	 *            an integer that represents the user id in the system
	 */
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
}

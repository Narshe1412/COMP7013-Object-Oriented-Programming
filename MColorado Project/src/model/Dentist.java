package model;

@SuppressWarnings("serial")
/**
 * 
 * @author Manuel Colorado
 *
 */
public class Dentist extends Person {
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
	 * @throws Exception
	 *             throws exception if password cannot be hashed
	 */
	public Dentist(final String username, final String password) throws Exception {
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
	 * @throws Exception
	 *             throws exception if password cannot be hashed
	 */
	public Dentist(final String name, final String address, final String phone, final String username,
			final String password) throws Exception {
		super(name, address, phone);
		setUsername(username);
		setPassword(password);
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
	 * @throws Exception
	 *             throws exception if password cannot be hashed
	 */
	public void setPassword(final String password) throws Exception {
		this.password = PasswordHandler.encrypt(password, sk);
	}

	/**
	 * Obtains the unhashed password from the user
	 * 
	 * @return the unhashed password from the user
	 * @throws Exception
	 *             if password cannot be decrypted
	 */
	private String getPassword() throws Exception {
		return PasswordHandler.decrypt(password, sk);
	}

	/**
	 * Checks if the password passed by parameter matches the one stored and returns
	 * true/false based on this match
	 * 
	 * @param password
	 *            string that contains the password we want to check for a match
	 * @return true if the password matches, false if it doesn't
	 * @throws Exception
	 *             throws exception if password cannot be decrypted
	 */
	public boolean verifyPassword(String password) throws Exception {
		return password.equalsIgnoreCase(getPassword());
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
}

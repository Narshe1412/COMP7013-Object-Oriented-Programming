package model;

public class Dentist extends Person{
	private String username;
	private String password;
	private final String sk = "h4xx0rPRIVVYk3yl33t";
	
	public Dentist(String name, String address, String phone) {
		super(name, address, phone);
	}
	
	public Dentist(String username, String password) throws Exception {
		super(username, "xxx", "xxx");
		setUsername(username);
		setPassword(password);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) throws Exception {
		this.password = PasswordHandler.encrypt(password, sk) ;
	}
	
	public String getPassword() throws Exception {
		return PasswordHandler.decrypt(password, sk);
	}
	
	public String toString() {
		return super.toString() + "\nUsername: " + getUsername();
	}
	
	public void print() {
		System.out.println(toString());
	}
}

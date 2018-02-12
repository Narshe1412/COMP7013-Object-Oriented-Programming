package model;

public class Dentist extends Person{
	private String username;
	private String password;
	
	public Dentist(String name, String address, String phone) {
		super(name, address, phone);
	}
	
	public Dentist(String username, String password) {
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
	
	public void setPassword(String password) {
		this.password = password;
		//TODO Encryption?
	}
	
	public String getPassword() {
		return "not gonna happen";
	}
	
	public String toString() {
		return super.toString() + "\nUsername: " + getUsername();
	}
	
	public void print() {
		System.out.println(toString());
	}
}

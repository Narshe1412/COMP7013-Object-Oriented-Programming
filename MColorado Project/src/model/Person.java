package model;

class Person {
	private String name;
	private String address;
	private String phone;
	
	public Person(final String name, final String address, final String phone) {
		setName(name);
		setAddress(address);
		setPhone(phone);
	}
	
	public Person(final String name, final String address) {
		setName(name);
		setName(address);
		setPhone("");
	}
	
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}
	
	public String toString() {
		return name + "\nAddress: " + address +  "\nPhone:" + phone;
	}

	public void print() {
		System.out.println(toString());
	}
}

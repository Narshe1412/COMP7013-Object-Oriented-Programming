package classes;

public class Procedure {
	private String procedureName;
	private String procedure_code;
	private double price;

	public Procedure(String name, String code, double price) {
		this.procedure_code = code;
		this.procedureName = name;
		setPrice(price);
	}

	public void setPrice(double p) {
		this.price = p;
	}

	public double getPrice() {
		return this.price;
	}

	public String getCode() {
		return this.procedure_code;
	}

	public String getName() {
		return this.procedureName;
	}
}

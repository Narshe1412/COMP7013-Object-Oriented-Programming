package model;

public class Procedure {
	private static int id = 0;
	private int procID;
	private String procName;
	private double procCost;

	public Procedure (String name, double cost) {
		setProcName(name);
		setProcCost(cost);
		this.procID = id;
		id++;
	}
	
	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public double getProcCost() {
		return procCost;
	}

	public void setProcCost(double procCost) {
		this.procCost = procCost;
	}

	public int getProcID() {
		return procID;
	}
	
	public String toString() {
		return "[" + getProcID() + "] " + getProcName() + " - " + getProcCost();
	}
	
	public void print() {
		System.out.println(toString());
	}
}

//TODO Refactoring
package model;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Procedure implements Serializable{
	//private static int id = 0;
	private int procID;
	private String procName;
	private double procCost;
	private boolean disabled;

	public Procedure (final String name, final double cost) {
		setProcName(name);
		setProcCost(cost);
		setDisabled(false);
//		this.procID = id;
//		id++;
	}
	
	public StringProperty getProcName() {
		return new SimpleStringProperty(procName);
	}

	public void setProcName(final String procName) {
		this.procName = procName;
	}

	public DoubleProperty getProcCost() {
		return new SimpleDoubleProperty(procCost);
	}

	public void setProcCost(final double procCost) {
		this.procCost = procCost;
	}

	public int getProcID() {
		return procID;
	}
	
	public void setProcID(final int id) {
		this.procID = id;
	}
	
	public String toString() {
		return getProcName().get() + "\tPrice: " + getProcCost().get() + " $";
	}
	
	public void print() {
		System.out.println(toString());
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}

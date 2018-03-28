package model;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@SuppressWarnings("serial")
public class Procedure implements Serializable{
	private static int id = 0;
	private int procID;
	private StringProperty procName;
	private DoubleProperty procCost;

	public Procedure (final String name, final double cost) {
		setProcName(name);
		setProcCost(cost);
//		this.procID = id;
//		id++;
	}
	
	public StringProperty getProcName() {
		return procName;
	}

	public void setProcName(final String procName) {
		this.procName = new SimpleStringProperty(procName);
	}

	public DoubleProperty getProcCost() {
		return procCost;
	}

	public void setProcCost(final double procCost) {
		this.procCost = new SimpleDoubleProperty(procCost);
	}

	public int getProcID() {
		return procID;
	}
	
	public void setProcID(final int id) {
		this.procID = id;
	}
	
	public String toString() {
		return "[" + getProcID() + "] " + getProcName() + " - " + getProcCost();
	}
	
	public void print() {
		System.out.println(toString());
	}
}

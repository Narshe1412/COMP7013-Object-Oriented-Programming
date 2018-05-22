package model;

import java.io.Serializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 
 * @author Manuel Colorado
 *
 */
@SuppressWarnings("serial")
public class Procedure implements Serializable {
	private int procID;
	private String procName;
	private double procCost;
	private boolean disabled;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            a string that represents the name of the procedure
	 * @param cost
	 *            a numeric value that represents the cost of the procedure
	 */
	public Procedure(final String name, final double cost) {
		setProcName(name);
		setProcCost(cost);
		setDisabled(false);
	}

	/**
	 * Obtains the observable value of the procedure name
	 * 
	 * @return a StringProperty object with the String value of the procedure name
	 */
	public StringProperty getProcName() {
		return new SimpleStringProperty(procName);
	}

	/**
	 * Changes the procedure name to the one passed by the parameter
	 * 
	 * @param procName
	 *            a string that will change the name recorded in the system
	 */
	public void setProcName(final String procName) {
		this.procName = procName;
	}

	/**
	 * Obtains the observable value of the cost of the procedure
	 * 
	 * @return a DoubleProperty object with the value of the procedure
	 */
	public DoubleProperty getProcCost() {
		return new SimpleDoubleProperty(procCost);
	}

	/**
	 * Changes the cost of the procedure with the value passed by parameter
	 * 
	 * @param procCost
	 *            a double value that will be stored as procedure cost
	 */
	public void setProcCost(final double procCost) {
		this.procCost = procCost;
	}

	/**
	 * Obtains the ID of the procedure
	 * 
	 * @return a numeric value that represents the ID of the procedure
	 */
	public int getProcID() {
		return procID;
	}

	/**
	 * Configures the id of the procedure with the id passed by the parameter
	 * 
	 * @param id
	 *            a numeric value that represents the ID of the procedure
	 */
	public void setProcID(final int id) {
		this.procID = id;
	}

	public String toString() {
		return getProcName().get() + "\tPrice: " + getProcCost().get() + " $";
	}

	/**
	 * Prints the result of the toString() method in the JVM console
	 */
	public void print() {
		System.out.println(toString());
	}

	/**
	 * Obtains the value if the procedure is disabled in the system (equivalent to
	 * delete)
	 * 
	 * @return true if the procedure is disabled, false otherwise
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled property of the system
	 * 
	 * @param disabled
	 *            a boolean value that determines if the procedure is disabled or
	 *            not
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}

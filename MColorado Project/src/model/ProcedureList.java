package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Creates a list of Procedures in the system
 * 
 * @author Manuel Colorado
 *
 */
public class ProcedureList extends ArrayList<Procedure> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public ProcedureList() {
		super();
	}

	/**
	 * Creates and add a new procedure on the system based on the required
	 * parameters for the new Procedure
	 * 
	 * @param name
	 *            Name of the new procedure
	 * @param price
	 *            Price of the new procedure
	 * @return a reference to the new Procedure object created on the system
	 */
	public Procedure addNew(final String name, final double price) {
		Procedure proc = new Procedure(name, price);
		super.add(proc);
		proc.setProcID(indexOf(proc));
		return proc;
	}

	/**
	 * Updates the Prcedure object passed by parameter with the name and priced
	 * passed by parameter
	 * 
	 * @param p
	 *            a reference to the procedure that will be updated
	 * @param name
	 *            the new name for this procedure
	 * @param price
	 *            a numeric value with the new price for the procedure
	 */
	public void update(final Procedure p, final String name, final double price) {
		p.setProcName(name);
		p.setProcCost(price);
	}

	/**
	 * Updates the Prcedure based on the id passed by parameter with the name and
	 * priced passed by parameter
	 * 
	 * @param id
	 *            a reference to the procedure that will be updated
	 * @param name
	 *            the new name for this procedure
	 * @param price
	 *            a numeric value with the new price for the procedure
	 */
	public void updateById(final int id, final String name, final double price) {
		Procedure toUpdate = get(id);
		toUpdate.setProcName(name);
		toUpdate.setProcCost(price);
	}

	/**
	 * Deletes the procedure passed by parameter
	 * 
	 * @param p
	 *            a Procedure that will be removed from the system
	 */
	public void delete(final Procedure p) {
		super.remove(p);
	}

	/**
	 * Deletes a procedure that matches with the id passed by parameter
	 * 
	 * @param id
	 *            a numeric value that identifies the id in of a procedure in the
	 *            system
	 */
	public void deleteById(final int id) {
		super.remove(id);
	}

	/**
	 * Returns a procedure whose string will matches the string passed by parameter
	 * 
	 * @param name
	 *            the name of the procedure that will be attempted to be found in
	 *            the system
	 * @return a reference to the Procedure found. If none is found, null will be
	 *         returned
	 */
	public Procedure find(String name) {
		for (Procedure proc : this) {
			if (name.equalsIgnoreCase(proc.getProcName().get())) {
				return proc;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String result = "";
		for (Procedure proc : this) {
			result += indexOf(proc) + ": " + proc.toString() + "\n";
		}
		return result;
	}
}

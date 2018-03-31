package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProcedureList extends ArrayList<Procedure> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProcedureList() {
		super();
	}
	

	public Procedure addNew(final String name, final double price) {
		Procedure proc = new Procedure(name, price);
		super.add(proc);
		proc.setProcID(indexOf(proc));
		return proc;
	}
	
	public void update(final Procedure p, final String name, final double price) {
		p.setProcName(name);
		p.setProcCost(price);
	}
	
	public void updateById(final int id, final String name, final double price) {
		Procedure toUpdate = get(id);
		toUpdate.setProcName(name);
		toUpdate.setProcCost(price);
	}
	
	public void delete(final Procedure p) {
		super.remove(p);	
	}
	
	public void deleteById(final int id) {
		super.remove(id);
	}
	
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

	
	

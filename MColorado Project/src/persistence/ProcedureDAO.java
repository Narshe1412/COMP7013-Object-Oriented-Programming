package persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import model.Procedure;

public class ProcedureDAO implements IDBOperationRepository<Procedure> {
	IDBManager procDB;

	public ProcedureDAO() {
		procDB = new TableHandler("procs");

	}

	@Override
	public boolean add(Procedure contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Procedure> getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Procedure> getAll() {
		ArrayList<Procedure> returnedList = new ArrayList<>();
		if (procDB.exists()) {
			CachedRowSet crs = (CachedRowSet) procDB.loadDB();
			try {
				while (crs.next()) {
					int procId = crs.getInt("procId");
					String procName = crs.getString("procName");
					double procCost = crs.getDouble("procCost");
					boolean disabled = crs.getBoolean("disabled");
					Procedure p = new Procedure(procName, procCost);
					p.setProcID(procId);
					p.setDisabled(disabled);
					returnedList.add(p);
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to find Procedure database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	public Iterable<Procedure> getAllFromInvoice(final int id) {
		TableHandler invprocDB = new TableHandler("invoiceprocedure");
		ArrayList<Procedure> returnedList = new ArrayList<>();
		if (invprocDB.exists()) {
			String sql = null; // TODO SQL
			CachedRowSet crs = (CachedRowSet) invprocDB.executeStatement(sql);
			try {
				while (crs.next()) {
					int ipid = crs.getInt("ipid");
					int invoiceID = crs.getInt("invoiceID");
					int procedureID = crs.getInt("procedureID");
					int procId = crs.getInt("procId");
					String procName = crs.getString("procName");
					double procCost = crs.getDouble("procCost");
					boolean disabled = crs.getBoolean("disabled");
					Procedure p = new Procedure(procName, procCost);
					p.setProcID(procId);
					p.setDisabled(disabled);
					if (invoiceID == id) {
						returnedList.add(p);
					}
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to find Procedure database", "");
				exwin.show();
			}
		}
		return null;
	}

	@Override
	public boolean update(Procedure contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Procedure contents) {
		// TODO Auto-generated method stub
		return false;
	}

}
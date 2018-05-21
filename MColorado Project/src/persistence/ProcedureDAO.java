package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import model.Procedure;

public class ProcedureDAO implements IDBOperationRepository<Procedure> {
	TableHandler procDB;

	public ProcedureDAO() {
		procDB = new TableHandler("procs");

	}

	@Override
	public int add(Procedure contents) {
		if (procDB.exists()) {
			try {
				String sql = "INSERT INTO procs (procId, procName, procCost, deleted) " + "VALUES (NULL, ?, ?, 0);";
				procDB.openConnection();
				PreparedStatement pstmt = procDB.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, contents.getProcName().getValue());
				pstmt.setDouble(2, contents.getProcCost().get());

				int procId = procDB.executeUpdate(pstmt);
				if (procId > 0) {
					contents.setProcID(procId);
					return procId;
				} else {
					throw new SQLException("Unable to create procedure.");
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Procedure database", "");
				exwin.show();
			} finally {
				procDB.closeConnection();
			}
		}
		return -1;
	}

	@Override
	public Procedure getByID(int id) {
		if (procDB.exists()) {
			try {
				String sql = "SELECT * FROM procs p WHERE p.procId = ?";
				procDB.openConnection();
				PreparedStatement pstmt = procDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);

				CachedRowSet crs = (CachedRowSet) procDB.executeStatement(pstmt);
				while (crs.next()) {
					int procId = crs.getInt("procId");
					String procName = crs.getString("procName");
					double procCost = crs.getDouble("procCost");
					boolean disabled = crs.getBoolean("disabled");
					Procedure p = new Procedure(procName, procCost);
					p.setProcID(procId);
					p.setDisabled(disabled);
					return p;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to find Procedure database", "");
				exwin.show();
			} catch (NullPointerException e) {
				// Resultset is empty, no need to action
			} finally {
				procDB.closeConnection();
			}
		}
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
			try {
				String sql = "SELECT * FROM procs p, invoiceprocedure i " + "WHERE i.invoiceID = ? "
						+ "AND i.procedureID = p.procId";
				invprocDB.openConnection();
				PreparedStatement pstmt = invprocDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);

				CachedRowSet crs = (CachedRowSet) invprocDB.executeStatement(pstmt);
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
			} catch (NullPointerException e) {
				// Resultset is empty, no need to action
			} finally {
				invprocDB.closeConnection();
			}
		}
		return returnedList;
	}

	@Override
	public boolean update(Procedure contents) {
		if (procDB.exists()) {
			try {

				String sql = "UPDATE procs SET procName = ?, procCost = ? WHERE procs.procId = ?";
				procDB.openConnection();
				PreparedStatement pstmt = procDB.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, contents.getProcName().getValue());
				pstmt.setDouble(2, contents.getProcCost().get());
				pstmt.setInt(3, contents.getProcID());

				if (procDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Procedure database", "");
				exwin.show();
			} finally {
				procDB.closeConnection();
			}
		}
		return false;
	}

	@Override
	public boolean remove(Procedure contents) {
		if (procDB.exists()) {
			try {

				String sql = "UPDATE procs SET deleted = 1 WHERE procs.procId = ?";
				procDB.openConnection();
				PreparedStatement pstmt = procDB.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, contents.getProcID());

				if (procDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Procedure database", "");
				exwin.show();
			} finally {
				procDB.closeConnection();
			}
		}
		return false;
	}

}

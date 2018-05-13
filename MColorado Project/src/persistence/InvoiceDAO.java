package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import model.Invoice;

public class InvoiceDAO implements IDBOperationRepository<Invoice> {
	TableHandler invoiceDB;

	public InvoiceDAO() {
		invoiceDB = new TableHandler("invoice");

	}

	@Override
	public int add(Invoice contents) {
		if (invoiceDB.exists()) {
			try {
				String sql = "INSERT INTO invoice (invoiceID, invoiceDate, patientNo, deleted) "
						+ "VALUES (NULL, ?, ?, 0);";
				invoiceDB.openConnection();
				PreparedStatement pstmt = invoiceDB.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, contents.getStringDate());
				pstmt.setInt(2, contents.getBilledTo().getPatientNo());

				int invoiceID = invoiceDB.executeUpdate(pstmt);
				if (invoiceID > 0) {
					contents.setInvoiceID(invoiceID);
					return invoiceID;
				} else {
					throw new SQLException("Unable to create user.");
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Invoice database", "");
				exwin.show();
			} finally {
				invoiceDB.closeConnection();
			}
		}
		return -1;
	}

	@Override
	public Invoice getByID(int id) {
		if (invoiceDB.exists()) {
			try {
				String sql = "SELECT * FROM invoice WHERE invoiceID = ?";
				invoiceDB.openConnection();
				PreparedStatement pstmt = invoiceDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);

				CachedRowSet crs = (CachedRowSet) invoiceDB.executeStatement(pstmt);
				while (crs.next()) {
					int invoiceID = crs.getInt("invoiceID");
					String invoiceDate = crs.getString("invoiceDate");
					int patientNo = crs.getInt("patientNo");
					Invoice i = new Invoice(invoiceDate);
					i.setInvoiceID(invoiceID);
					return i;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Invoice database", "");
				exwin.show();
				while (e != null) {
					System.out.println(e.getSQLState());
					System.out.println(e.getMessage());
					System.out.println(e.getErrorCode());
					e = e.getNextException();
				}

			} catch (NullPointerException e) {
				// Resultset is empty, no need to action
			} finally {
				invoiceDB.closeConnection();
			}
		}
		return null;
	}

	@Override
	public Iterable<Invoice> getAll() {
		ArrayList<Invoice> returnedList = new ArrayList<>();
		if (invoiceDB.exists()) {
			CachedRowSet crs = (CachedRowSet) invoiceDB.loadDB();
			try {
				while (crs.next()) {
					int invoiceID = crs.getInt("invoiceID");
					String invoiceDate = crs.getString("invoiceDate");
					Invoice i = new Invoice(invoiceDate);
					i.setInvoiceID(invoiceID);
					returnedList.add(i);
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Invoice database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	public Iterable<Invoice> getAllFromPatient(final int id) {
		ArrayList<Invoice> returnedList = new ArrayList<>();
		if (invoiceDB.exists()) {
			try {
				String sql = "SELECT * FROM invoice WHERE patientNo = ?";
				invoiceDB.openConnection();
				PreparedStatement pstmt = invoiceDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);

				CachedRowSet crs = (CachedRowSet) invoiceDB.executeStatement(pstmt);
				while (crs.next()) {
					int invoiceID = crs.getInt("invoiceID");
					String invoiceDate = crs.getString("invoiceDate");
					int patientNo = crs.getInt("patientNo");
					Invoice i = new Invoice(invoiceDate);
					i.setInvoiceID(invoiceID);
					if (patientNo == id) {
						returnedList.add(i);
					}
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Invoice database", "");
				exwin.show();
				while (e != null) {
					System.out.println(e.getSQLState());
					System.out.println(e.getMessage());
					System.out.println(e.getErrorCode());
					e = e.getNextException();
				}

			} catch (NullPointerException e) {
				// Resultset is empty, no need to action
			} finally {
				invoiceDB.closeConnection();
			}
		}
		return returnedList;
	}

	@Override
	public boolean update(Invoice contents) {
		if (invoiceDB.exists()) {
			try {
				String sql = "UPDATE invoice SET invoiceDate = ? WHERE invoiceID = ?";
				invoiceDB.openConnection();
				PreparedStatement pstmt = invoiceDB.getCon().prepareStatement(sql);
				pstmt.setString(1, contents.getStringDate());
				pstmt.setInt(2, contents.getInvoiceID());
				if (invoiceDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				invoiceDB.closeConnection();
			}
		}
		return false;
	}

	@Override
	public boolean remove(Invoice contents) {
		if (invoiceDB.exists()) {
			try {
				String sql = "UPDATE invoice SET deleted = 1 WHERE invoiceID = ?";
				invoiceDB.openConnection();
				PreparedStatement pstmt = invoiceDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, contents.getInvoiceID());
				if (invoiceDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				invoiceDB.closeConnection();
			}
		}
		return false;
	}

}

package persistence;

import java.sql.SQLException;
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
	public boolean add(Invoice contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Invoice> getByID(int id) {
		// TODO Auto-generated method stub
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
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	public Iterable<Invoice> getAllFromPatient(final int id) {
		ArrayList<Invoice> returnedList = new ArrayList<>();
		if (invoiceDB.exists()) {
			String sql = null; //TODO SQL
			CachedRowSet crs = (CachedRowSet) invoiceDB.executeStatement(sql);
			try {
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
			}
		}
		return returnedList;
	}

	@Override
	public boolean update(Invoice contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Invoice contents) {
		// TODO Auto-generated method stub
		return false;
	}

}

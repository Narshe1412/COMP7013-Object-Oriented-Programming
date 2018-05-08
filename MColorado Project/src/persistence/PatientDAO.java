package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import model.Invoice;
import model.Patient;

public class PatientDAO implements IDBOperationRepository<Patient> {

	TableHandler patientDB;

	public PatientDAO() {
		patientDB = new TableHandler("patient");

	}

	@Override
	public boolean add(Patient contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Patient getByID(int id) {
		if (patientDB.exists()) {
			try {
				String sql = "SELECT * FROM patient WHERE patientNo = ?";
				patientDB.openConnection();
				PreparedStatement pstmt = patientDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);

				CachedRowSet crs = (CachedRowSet) patientDB.executeStatement(pstmt);
				while (crs.next()) {
					int patientNo = crs.getInt("patientNo");
					String name = crs.getString("name");
					String address = crs.getString("address");
					String phone = crs.getString("phone");
					Patient p = new Patient(name, address, phone);
					p.setPatientNo(patientNo);
					return p;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Patient database", "");
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
				patientDB.closeConnection();
			}
		}
		return null;
	}

	@Override
	public Iterable<Patient> getAll() {
		ArrayList<Patient> returnedList = new ArrayList<>();
		if (patientDB.exists()) {
			CachedRowSet crs = (CachedRowSet) patientDB.loadDB();
			try {
				while (crs.next()) {
					int patientNo = crs.getInt("patientNo");
					String name = crs.getString("name");
					String address = crs.getString("address");
					String phone = crs.getString("phone");
					Patient p = new Patient(name, address, phone);
					p.setPatientNo(patientNo);
					returnedList.add(p);
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to find Patient database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	@Override
	public boolean update(Patient contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Patient contents) {
		// TODO Auto-generated method stub
		return false;
	}

}

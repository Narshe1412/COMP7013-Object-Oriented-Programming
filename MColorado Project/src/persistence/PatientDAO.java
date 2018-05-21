package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
	public int add(Patient contents) {
		if (patientDB.exists()) {
			try {
				String sql = "INSERT INTO patient (patientNo, name, address, phone) " + "VALUES (NULL, ?, ?, ?);";
				patientDB.openConnection();
				PreparedStatement pstmt = patientDB.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, contents.getName());
				pstmt.setString(2, contents.getAddress());
				pstmt.setString(3, contents.getPhone());

				int patientID = patientDB.executeUpdate(pstmt);
				if (patientID > 0) {
					contents.setPatientNo(patientID);
					return patientID;
				} else {
					throw new SQLException("Unable to create user.");
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				patientDB.closeConnection();
			}
		}
		return -1;
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
					boolean deleted = crs.getBoolean("deleted");
					Patient p = new Patient(name, address, phone);
					p.setPatientNo(patientNo);
					if(!deleted) {
						returnedList.add(p);	
					}
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
		if (patientDB.exists()) {
			try {
				String sql = "UPDATE patient SET name = ? ," + " address = ? ," + " phone = ?"
						+ " WHERE patient.patientNo = ?";
				patientDB.openConnection();
				PreparedStatement pstmt = patientDB.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, contents.getName());
				pstmt.setString(2, contents.getAddress());
				pstmt.setString(3, contents.getPhone());
				pstmt.setInt(4, contents.getPatientNo());
				if (patientDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Patient database", "");
				exwin.show();
			} finally {
				patientDB.closeConnection();
			}
		}
		return false;
	}

	@Override
	public boolean remove(Patient contents) {
		if (patientDB.exists()) {
			try {
				String sql = "UPDATE patient SET deleted = 1 WHERE patientNo = ?";
				patientDB.openConnection();
				PreparedStatement pstmt = patientDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, contents.getPatientNo());
				if (patientDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Patient database", "");
				exwin.show();
			} finally {
				patientDB.closeConnection();
			}
		}
		return false;
	}

}

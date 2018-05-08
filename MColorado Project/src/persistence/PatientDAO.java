package persistence;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import model.Patient;

public class PatientDAO implements IDBOperationRepository<Patient> {

	IDBManager patientDB;

	public PatientDAO() {
		patientDB = new TableHandler("patient");

	}

	@Override
	public boolean add(Patient contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Patient> getByID(int id) {
		// TODO Auto-generated method stub
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

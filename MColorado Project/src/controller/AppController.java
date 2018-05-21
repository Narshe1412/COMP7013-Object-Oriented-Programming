package controller;

import java.util.ArrayList;
import model.Patient;
import persistence.PatientDAO;

public class AppController {

	public int addPatient(Patient p) {
		return new PatientDAO().add(p);
	}

	public boolean updatePatient(Patient p) {
		return new PatientDAO().update(p);
	}

	public ArrayList<Patient> getAllPatients() {
		return (ArrayList<Patient>) new PatientDAO().getAll();
	}

	public Patient getPatient(int id) {
		return new PatientDAO().getByID(id);
	}

	public boolean deletePatient(Patient p) {
		return new PatientDAO().remove(p);
	}
}

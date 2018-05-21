package controller;

import java.util.ArrayList;

import model.*;
import persistence.InvoiceDAO;
import persistence.PatientDAO;
import persistence.ProcedureDAO;

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

	public ArrayList<Invoice> getInvoicesFromPatient(Patient p) {
		return (ArrayList<Invoice>) new InvoiceDAO().getAllFromPatient(p.getPatientNo());
	}

	public void addInvoiceToPatient(Invoice i, Patient p) {
		i.setBilledTo(p);
		int invoiceId = new InvoiceDAO().add(i);
		if (invoiceId > 0) {
			p.addInvoice(i);
		}

	}

	public Invoice getInvoiceById(int id) {
		return new InvoiceDAO().getByID(id);
	}

	public ArrayList<Procedure> getAllProcedures() {
		return (ArrayList<Procedure>) new ProcedureDAO().getAll();
	}

	public Procedure getProcedureByName(String name) {
		for (Procedure p : getAllProcedures()) {
			if (name.equalsIgnoreCase(p.getProcName().get())) {
				return p;
			}
		}
		return null;
	}

	public int addProcedure(Procedure p) {
		return new ProcedureDAO().add(p);
	}

	public boolean deleteProcedure(Procedure p) {
		return new ProcedureDAO().remove(p);

	}
}

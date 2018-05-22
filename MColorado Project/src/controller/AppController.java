package controller;

import java.util.ArrayList;
import model.*;
import persistence.DentistDAO;
import persistence.InvoiceDAO;
import persistence.PatientDAO;
import persistence.PaymentDAO;
import persistence.ProcedureDAO;

public class AppController {

	public int addDentist(Dentist d) {
		return new DentistDAO().add(d);
	}

	public boolean updateDentist(Dentist d) {
		return new DentistDAO().update(d);
	}

	public ArrayList<Dentist> getAllDentist() {
		return (ArrayList<Dentist>) new DentistDAO().getAll();
	}

	public boolean deleteDentist(Dentist d) {
		return new DentistDAO().remove(d);
	}
	
	public Dentist getDentistByUsername(String username) {
		return new DentistDAO().getByUsername(username);
	}

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

	public void addPaymentToInvoice(Payment p, Invoice i) {
		p.setContainedIn(i);
		int paymentId = new PaymentDAO().add(p);
		if (paymentId > 0) {
			i.addPayment(p);
			i.calculateInvoicePaid();
		}

	}

	public boolean deletePayment(Payment p) {
		return new PaymentDAO().remove(p);
	}

	public void deletePaymentFromInvoice(Payment p, Invoice i) {
		if (deletePayment(p)) {
			i.removePayment(p);
		}
	}

	public ArrayList<Payment> getPaymentFromInvoice(Invoice inv) {
		return (ArrayList<Payment>) new PaymentDAO().getAllFromInvoice(inv.getInvoiceID());
	}

	public ArrayList<Payment> getAllPayments() {
		return (ArrayList<Payment>) new PaymentDAO().getAll();
	}

}

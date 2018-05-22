package controller;

import java.util.ArrayList;

import model.*;
import persistence.DentistDAO;
import persistence.InvoiceDAO;
import persistence.PatientDAO;
import persistence.PaymentDAO;
import persistence.ProcedureDAO;

public class AppController {

	// Dentist
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

	// Patient
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
			i.setInvoiceID(invoiceId);
			p.addInvoice(i);
		}
	}

	public Invoice getInvoiceById(int id) {
		return new InvoiceDAO().getByID(id);
	}

	public boolean updateInvoice(Invoice i) {
		return new InvoiceDAO().update(i);
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

	public boolean updateProcedure(Procedure p) {
		deleteProcedure(p);
		return (addProcedure(p) > 0);
	}

	public void addPaymentToInvoice(Payment p, Invoice i) {
		p.setContainedIn(i);
		int paymentId = new PaymentDAO().add(p);
		if (paymentId > 0) {
			i.addPayment(p);
			i.calculateInvoicePaid();
		}
	}

	public void addProcedureToInvoice(Procedure p, Invoice i) {
		if (new InvoiceDAO().addProcedureToInvoice(p, i)) {
			i.addProcedure(p);
		}
	}

	public void deleteProcedureFromInvoice(Procedure p, Invoice i) {
		if (new InvoiceDAO().deleteProcedureFromInvoice(p, i)) {
			i.removeProcedure(p);
		}
	}

	public boolean deletePayment(Payment p) {
		return new PaymentDAO().remove(p);
	}

	public void deletePaymentFromInvoice(Payment p, Invoice i) {
		if (deletePayment(p)) {
			Payment toDelete = null;
			for (Payment payment : i.getIn_paymentList()) {
				if (payment.getPaymentID() == p.getPaymentID()) {
					toDelete = payment;
				}
			}
			i.removePayment(toDelete);
		}
	}

	public ArrayList<Payment> getPaymentsFromInvoice(Invoice inv) {
		return (ArrayList<Payment>) new PaymentDAO().getAllFromInvoice(inv.getInvoiceID());
	}

	public ArrayList<Payment> getAllPayments() {
		return (ArrayList<Payment>) new PaymentDAO().getAll();
	}

	public ArrayList<Procedure> getProceduresByInvoice(Invoice inv) {
		return (ArrayList<Procedure>) new ProcedureDAO().getAllFromInvoice(inv.getInvoiceID());
	}

}

package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
/**
 * Default values for the system, for testing or initial load purposes
 * @author Manuel Colorado
 *
 */
public class Defaults {
	private static ArrayList<Procedure> procedures;
	private static List<Dentist> dentists;
	private static List<Patient> patients;
	private static List<Invoice> invoices;

	public static List<Procedure> createProcedures() {

		procedures = new ProcedureList();
		procedures.add(new Procedure("Examination", 20));
		procedures.add(new Procedure("Hygienist Scale and Polish", 60));
		procedures.add(new Procedure("Root planing", 80));
		procedures.add(new Procedure("Panoramic X-ray", 60));
		procedures.add(new Procedure("Fillings", 80));
		procedures.add(new Procedure("Root Canal Treatment", 400));
		procedures.add(new Procedure("Crowns", 800));
		procedures.add(new Procedure("Dentures", 450));
		procedures.add(new Procedure("Teeth Whitening", 250));

		return procedures;
	}

	public static List<Dentist> createDentists() throws Exception {
		dentists = new DentistList();
		dentists.add(new Dentist("admin", "admin"));
		dentists.add(new Dentist("jdoe", "doe"));
		return dentists;
	}
	
	public static List<Patient> createPatient() {
		patients = new PatientList();
		patients.add(new Patient("John Doe", "23 My Street", "555-2323"));
		return patients;
	}
	
	public static List<Invoice> createInvoice() {
		invoices = new InvoiceList();
		invoices.add(new Invoice());
		invoices.get(0).getIn_paymentList().add(new Payment(203));
		invoices.get(0).getIn_paymentList().add(new Payment(11, new Date()));
		invoices.get(0).getIn_paymentList().add(new Payment(23.7, "15/01/2017"));
		
		invoices.get(0).getIn_procList().add(procedures.get(0));
		invoices.get(0).getIn_procList().add(procedures.get(3));
		invoices.get(0).getIn_procList().add(procedures.get(3));
		invoices.get(0).getIn_procList().add(procedures.get(1));
		invoices.get(0).getIn_procList().add(procedures.get(0));
		invoices.get(0).getIn_procList().add(procedures.get(0));
		invoices.get(0).getIn_procList().add(procedures.get(6));
		
		return invoices;
	}
}

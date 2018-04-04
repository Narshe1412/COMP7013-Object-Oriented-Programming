package model;

import java.util.Date;
import java.util.List;
/**
 * Default values for the system, for testing or initial load purposes
 * @author Manuel Colorado
 *
 */
public class Defaults {
	private static ProcedureList procedures;
	private static List<Dentist> dentists;
	private static List<Patient> patients;
	private static List<Invoice> invoices;
	private static List<Payment> payments;

	public static List<Procedure> createProcedures() {

		procedures = new ProcedureList();
		procedures.addNew("Examination", 20);
		procedures.addNew("Hygienist Scale and Polish", 60);
		procedures.addNew("Root planing", 80);
		procedures.addNew("Panoramic X-ray", 60);
		procedures.addNew("Fillings", 80);
		procedures.addNew("Root Canal Treatment", 400);
		procedures.addNew("Crowns", 800);
		procedures.addNew("Dentures", 450);
		Procedure disabled = procedures.addNew("Disabled", 250);
		disabled.setDisabled(true);
		procedures.addNew("Teeth Whitening", 250);

		return procedures;
	}
	
	public static List<Payment> createPayments() {
		payments = new PaymentList();
		
		payments.add(new Payment(203));
		payments.add(new Payment(11, new Date()));
		payments.add(new Payment(23.7, "15/01/2017"));
		
		payments.add(new Payment(11, "01/01/2016"));
		payments.add(new Payment(11, "03/01/2017"));
		payments.add(new Payment(23.7, "15/01/2017"));
		
		payments.add(new Payment(203, "03/09/2017"));
		payments.add(new Payment(11, "15/08/2017"));
		payments.add(new Payment(23.7, "15/01/2017"));
		
		return payments;
		
	}

	public static List<Dentist> createDentists() throws Exception {
		dentists = new DentistList();
		dentists.add(new Dentist("admin", "admin123"));
		dentists.add(new Dentist("jdoe", "12345678"));
		return dentists;
	}
	
	public static List<Patient> createPatient() {
		patients = new PatientList();
		patients.add(new Patient("John Doe", "23 My Street", "555-2323"));
		patients.add(new Patient("Angela Doe", "Another Street", "111-333"));
		patients.add(new Patient("Will Iam Shakesper", "Somewhere in London", "333-111-55"));
		return patients;
	}
	
	public static List<Invoice> createInvoice() {
		invoices = new InvoiceList();
		invoices.add(new Invoice());
		invoices.add(new Invoice());
		invoices.add(new Invoice());
		invoices.get(0).getIn_paymentList().add(payments.get(0));
		invoices.get(0).getIn_paymentList().add(payments.get(1));
		invoices.get(0).getIn_paymentList().add(payments.get(2));
		
		invoices.get(1).getIn_paymentList().add(payments.get(3));
		invoices.get(1).getIn_paymentList().add(payments.get(4));
		invoices.get(1).getIn_paymentList().add(payments.get(5));
		
		invoices.get(2).getIn_paymentList().add(payments.get(6));
		invoices.get(2).getIn_paymentList().add(payments.get(7));
		invoices.get(2).getIn_paymentList().add(payments.get(8));
		
		invoices.get(0).getIn_procList().add(procedures.get(0));
		invoices.get(0).getIn_procList().add(procedures.get(3));
		invoices.get(0).getIn_procList().add(procedures.get(3));
		invoices.get(0).getIn_procList().add(procedures.get(1));
		invoices.get(0).getIn_procList().add(procedures.get(0));
		invoices.get(0).getIn_procList().add(procedures.get(0));
		invoices.get(0).getIn_procList().add(procedures.get(6));
		
		invoices.get(1).getIn_procList().add(procedures.get(0));
		invoices.get(1).getIn_procList().add(procedures.get(3));
		invoices.get(1).getIn_procList().add(procedures.get(1));
		invoices.get(1).getIn_procList().add(procedures.get(1));
		invoices.get(1).getIn_procList().add(procedures.get(4));
		invoices.get(1).getIn_procList().add(procedures.get(4));
		invoices.get(1).getIn_procList().add(procedures.get(6));
		
		invoices.get(2).getIn_procList().add(procedures.get(0));
		invoices.get(2).getIn_procList().add(procedures.get(1));
		invoices.get(2).getIn_procList().add(procedures.get(3));
		invoices.get(2).getIn_procList().add(procedures.get(1));
		invoices.get(2).getIn_procList().add(procedures.get(6));
		invoices.get(2).getIn_procList().add(procedures.get(6));
		invoices.get(2).getIn_procList().add(procedures.get(6));
		
		patients.get(0).addInvoice(invoices.get(0));
		patients.get(1).addInvoice(invoices.get(1));
		patients.get(2).addInvoice(invoices.get(2));
		return invoices;
	}
}

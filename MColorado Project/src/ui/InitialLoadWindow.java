package ui;

import java.util.List;

import controller.AppData;
import javafx.stage.Stage;
import model.Defaults;
import model.Dentist;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import persistence.FileHandler;
import persistence.IDBManager;

public class InitialLoadWindow extends Stage{

	public InitialLoadWindow() throws Exception {
		super();
		loadState();
	}
	
	@SuppressWarnings("unchecked")
	private void loadState() throws Exception {
		IDBManager userDB = new FileHandler("user.dat");
		if (userDB.exists()) {
			AppData.INSTANCE.setUserList((List<Dentist>) userDB.loadDB());
		} else {
			AppData.INSTANCE.setUserList(Defaults.createDentists());
		}
		
		IDBManager patientDB = new FileHandler("patient.dat");
		if (patientDB.exists()) {
			AppData.INSTANCE.setPatientList((List<Patient>) patientDB.loadDB());
		} else {
			AppData.INSTANCE.setPatientList(Defaults.createPatient());
		}
		
		IDBManager procDB = new FileHandler("procedures.dat");
		if (procDB.exists()) {
			AppData.INSTANCE.setProcedureList((List<Procedure>) procDB.loadDB());
		} else {
			AppData.INSTANCE.setProcedureList(Defaults.createProcedures());
		}
		
		IDBManager paymentDB = new FileHandler("payments.dat");
		if (paymentDB.exists()) {
			AppData.INSTANCE.setPaymentList((List<Payment>) paymentDB.loadDB());
		} else {
			AppData.INSTANCE.setPaymentList(Defaults.createPayments());
		}
		
		IDBManager invoiceDB = new FileHandler("invoice.dat");
		if (invoiceDB.exists()) {
			AppData.INSTANCE.setInvoiceList((List<Invoice>) invoiceDB.loadDB());
		} else {
			AppData.INSTANCE.setInvoiceList(Defaults.createInvoice());
		}
	}
}

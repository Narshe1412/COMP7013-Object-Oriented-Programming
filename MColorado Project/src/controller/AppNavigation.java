package controller;

import java.io.Serializable;
import java.util.List;

import javafx.stage.Stage;
import model.*;
import persistence.FileHandler;
import persistence.IDBManager;

public class AppNavigation {
	private enum TypeOfChange {
		USER_CHANGED, PATIENT_CHANGED, INVOICE_ADDED
	}

	private static Stage app;

	public AppNavigation(Stage app) {
		setMainWindow(app);
	}

	public void showWindow() {
		app.show();
	}

	public static void setMainWindow(Stage newWindow) {
		app = newWindow;
	}

	public static Stage getMainWindow() {
		return app;
	}

	public static void updateUser(Dentist newUser) {
		AppState.INSTANCE.setCurrentUser(newUser);
		refreshUI(TypeOfChange.USER_CHANGED);
	}

	public static void updatePatient(Patient newPatient) {
		AppState.INSTANCE.setPreviousPatient(AppState.INSTANCE.getCurrentPatient());
		AppState.INSTANCE.setCurrentPatient(newPatient);
		refreshUI(TypeOfChange.PATIENT_CHANGED);
	}

	public static void refreshUI(TypeOfChange change) {
		// TODO Make sure that every time we load a new patient all the previous tabs
		// are removed and the proper ones are added to the system

	}

	@SuppressWarnings("unchecked")
	public static void loadState() throws Exception {

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

	public static void saveUsers() {
		IDBManager userDB = new FileHandler("user.dat");
		userDB.saveDB(AppData.INSTANCE.getUserList());
	}

	public static void saveState() {
		saveUsers();
		IDBManager procDB = new FileHandler("procedures.dat");
		procDB.saveDB(AppData.INSTANCE.getProcedureList());
		IDBManager paymentDB = new FileHandler("payments.dat");
		paymentDB.saveDB(AppData.INSTANCE.getPaymentList());
		IDBManager invoiceDB = new FileHandler("invoice.dat");
		invoiceDB.saveDB(AppData.INSTANCE.getInvoiceList());
		IDBManager patientDB = new FileHandler("patient.dat");
		patientDB.saveDB(AppData.INSTANCE.getPatientList());
	}

	public static void saveConfig() {
		Config toSave = new Config();
		IDBManager configDB = new FileHandler("config.dat");
		configDB.saveDB(toSave);
	}

	public static void loadConfig() {
		IDBManager configDB = new FileHandler("config.dat");
		if (configDB.exists()) {
			Config toLoad = (Config) configDB.loadDB();
			toLoad.loadConfig();
		} else {
			new Config().loadConfig();
		}
	}

}

class Config implements Serializable {
	private static final long serialVersionUID = 1L;
	Dentist defaultUser;

	public Config() {
		defaultUser = AppData.INSTANCE.getSavedUser();
	}

	public void loadConfig() {
		AppData.INSTANCE.setSavedUser(defaultUser);
	}

}

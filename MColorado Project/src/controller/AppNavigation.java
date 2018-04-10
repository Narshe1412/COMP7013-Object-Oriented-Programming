package controller;

import java.io.Serializable;
import java.util.List;

import exception.PassException;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import persistence.FileHandler;
import persistence.IDBManager;
import ui.CloseAlertDialog;
import ui.ReloadableNode;

/**
 * Uses the controller singletons to control the state of the UI and simplify
 * the steps taken to update each window
 * 
 * @author Manuel Colorado
 *
 */
public class AppNavigation {
	private enum TypeOfChange {
		USER_CHANGED, PATIENT_CHANGED, INVOICE_ADDED
	}

	private static ReloadableNode app;

	/**
	 * Constructor
	 * 
	 * @param window
	 *            a reference to the main window
	 */
	public AppNavigation(ReloadableNode window) {
		setMainWindow(window);
	}

	/**
	 * Starts the main application
	 */
	public static void showWindow() {
		((Stage) app).show();
	}

	/**
	 * Configures the main window
	 * 
	 * @param window
	 *            a reference to the main window
	 */
	public static void setMainWindow(ReloadableNode window) {
		app = window;
	}

	/**
	 * Obtains the current main window of the system
	 * 
	 * @return a reference pointer to the main window so its methods can be called
	 */
	public static ReloadableNode getMainWindow() {
		return app;
	}

	/**
	 * Updates the state of the application with the new user (Dentist)
	 * 
	 * @param newUser
	 *            a Dentist object that will serve as a new user of the application
	 */
	public static void updateUser(Dentist newUser) {
		AppState.INSTANCE.setCurrentUser(newUser);
		refreshUI(TypeOfChange.USER_CHANGED);
	}

	/**
	 * Updates the current patient in the system and refreshes the main application
	 * with the current patient details
	 * 
	 * @param newPatient
	 *            a Patient object that will be displayed in the app UI
	 */
	public static void updatePatient(Patient newPatient) {
		AppState.INSTANCE.setPreviousPatient(AppState.INSTANCE.getCurrentPatient());
		AppState.INSTANCE.setCurrentPatient(newPatient);
		refreshUI(TypeOfChange.PATIENT_CHANGED);
	}

	/**
	 * Loads a patient in the system
	 * 
	 * @param pPatient
	 *            a Patient object that will be displayed in the app UI
	 */
	public static void loadPatient(Patient pPatient) {
		AppState.INSTANCE.setCurrentPatient(pPatient);
		refreshUI(TypeOfChange.PATIENT_CHANGED);
	}

	/**
	 * Clears the UI of the system to display no patient
	 */
	public static void clearPatient() {
		AppState.INSTANCE.setCurrentPatient(null);
		refreshUI(TypeOfChange.PATIENT_CHANGED);
	}

	/**
	 * Refreshes the UI of the system
	 * 
	 * @param change
	 *            a TypeOfChange indicator to indicate the UI which elements needs
	 *            to be updated
	 */
	public static void refreshUI(TypeOfChange change) {
		app.refreshUI();
	}

	/**
	 * A global method that determines if the app has been changed or not, and ask
	 * the user if the saves must be stored or discard before exiting
	 */
	public static void exitApp() {
		saveConfig();
		if (AppState.INSTANCE.isModified()) {
			new CloseAlertDialog();
		} else {
			Platform.exit();
		}
	}

	/**
	 * Loads the database in the app system
	 * 
	 * @throws PassException
	 *             handles exceptions when loading users as the passwords have been
	 *             hashed and stored encrypted
	 * @throws IOException
	 *             handles exceptions when loading the database
	 */
	@SuppressWarnings("unchecked")
	public static void loadState() throws PassException {

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

	/**
	 * Stores the user portion of the database. Called when users are updated,
	 * passwords are changed or reset
	 */
	public static void saveUsers() {
		IDBManager userDB = new FileHandler("user.dat");
		userDB.saveDB(AppData.INSTANCE.getUserList());
	}

	/**
	 * Saves the current database state in serialization files
	 */
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
		AppState.INSTANCE.setModified(false);
	}

	/**
	 * Saves the Config object with the system configuration that will always be
	 * stored in a local file
	 */
	public static void saveConfig() {
		Config toSave = new Config();
		IDBManager configDB = new FileHandler("config.dat");
		configDB.saveDB(toSave);
	}

	/**
	 * Loads the Config object containing the initial configuration of the system
	 * from a local file
	 */
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

/**
 * Stores the initial configuration for the app
 * 
 * @author Manuel Colorado
 *
 */
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

package controller;

import java.io.Serializable;
import java.sql.SQLException;
import exception.PassException;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import persistence.DentistDAO;
import persistence.FileHandler;
import persistence.IDBManager;
import persistence.IDBOperationRepository;
import persistence.InvoiceDAO;
import persistence.PatientDAO;
import persistence.PaymentDAO;
import persistence.ProcedureDAO;
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
		Platform.exit();

		/**
		 * @deprecated
		 * 
		 * 			if (AppState.INSTANCE.isModified()) { new CloseAlertDialog(); }
		 *             else { Platform.exit(); }
		 */
	}

	/**
	 * Loads the database in the app system
	 * 
	 * @throws PassException
	 *             handles exceptions when loading users as the passwords have been
	 *             hashed and stored encrypted
	 * @throws SQLException
	 *             handles exception connecting to the database
	 */
	public static void loadState() throws PassException, SQLException {

		/**
		 * Load up the list of dentists. Create default users if none exist
		 */
		AppData.INSTANCE.setUserList(new DentistList());
		for (Dentist d : new DentistDAO().getAll()) {
			AppData.INSTANCE.getUserList().add(d);
		}
		if (AppData.INSTANCE.getUserList().isEmpty()) {
			for(Dentist d : Defaults.createDentists()) {
				addDBelement(new DentistDAO(), d);
				AppData.INSTANCE.getUserList().add(d);
			}
		}

		/**
		 * Load the list of Procedures. Create Defaults if none exists
		 */
		AppData.INSTANCE.setProcedureList(new ProcedureList());
		for (Procedure p : new ProcedureDAO().getAll()) {
			AppData.INSTANCE.getProcedureList().add(p);
		}
		if (AppData.INSTANCE.getProcedureList().isEmpty()) {
			AppData.INSTANCE.setProcedureList(Defaults.createProcedures());
		}

		/**
		 * Load the list of Payments.
		 
		AppData.INSTANCE.setPaymentList(new PaymentList());
		for (Payment p : new PaymentDAO().getAll()) {
			AppData.INSTANCE.getPaymentList().add(p);
		}
*/
		/**
		 * Load the list of invoices. Add the related payments and procedures for each
		 * Invoice.
		AppData.INSTANCE.setInvoiceList(new InvoiceList());
		for (Invoice i : new InvoiceDAO().getAll()) {
			AppData.INSTANCE.getInvoiceList().add(i);
			for (Payment p : new PaymentDAO().getAllFromInvoice(i.getInvoiceID())) {
				i.addPayment(AppData.INSTANCE.getPaymentList().getById(p.getPaymentID()));
			}
			for (Procedure p : new ProcedureDAO().getAllFromInvoice(i.getInvoiceID())) {
				i.addProcedure(AppData.INSTANCE.getProcedureList().getById(p.getProcID()));
			}
		}
*/
		/**
		 * Load up the list of patients. Add the related invoices for each patient.
		 
		AppData.INSTANCE.setPatientList(new PatientList());
		try {
			for (Patient p : new PatientDAO().getAll()) {
				AppData.INSTANCE.getPatientList().add(p);
				for (Invoice i : new InvoiceDAO().getAllFromPatient(p.getPatientNo())) {
					p.addInvoice(AppData.INSTANCE.getInvoiceList().getById(i.getInvoiceID()));
				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
	}

	/**
	 * @deprecated SQL will make a persistent state Stores the user portion of the
	 *             database. Called when users are updated, passwords are changed or
	 *             reset
	 */
	public static void saveUsers() {
		IDBManager userDB = new FileHandler("user.dat");
		userDB.saveDB(AppData.INSTANCE.getUserList());
	}

	/**
	 * @deprecated SQL will make a persistent state Saves the current database state
	 *             in serialization files
	 */
	public static void saveState() {
		saveUsers();
		/*
		 * IDBManager procDB = new FileHandler("procedures.dat");
		 * procDB.saveDB(AppData.INSTANCE.getProcedureList()); IDBManager paymentDB =
		 * new FileHandler("payments.dat");
		 * paymentDB.saveDB(AppData.INSTANCE.getPaymentList()); IDBManager invoiceDB =
		 * new FileHandler("invoice.dat");
		 * invoiceDB.saveDB(AppData.INSTANCE.getInvoiceList()); IDBManager patientDB =
		 * new FileHandler("patient.dat");
		 * patientDB.saveDB(AppData.INSTANCE.getPatientList());
		 * AppState.INSTANCE.setModified(false);
		 */ }

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

	/**
	 * 
	 * @param dao
	 * @param element
	 * @return
	 */
	public static <T> boolean updateDBelement(IDBOperationRepository<T> dao, final T element) {
		return dao.update(element);
	}
	
	/**
	 * 
	 * @param dao
	 * @param element
	 * @return
	 */

	public static <T> boolean deleteDBelement(IDBOperationRepository<T> dao, final T element) {
		return dao.remove(element);
	}

	/**
	 * 
	 * @param dao
	 * @param element
	 * @return
	 */
	public static <T> int addDBelement(IDBOperationRepository<T> dao, final T element) {
		return dao.add(element);
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

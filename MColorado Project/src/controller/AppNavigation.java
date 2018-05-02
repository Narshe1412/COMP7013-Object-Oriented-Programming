package controller;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import exception.PassException;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import persistence.FileHandler;
import persistence.IDBManager;
import persistence.MySQLController;
import persistence.TableHandler;
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
	 * @throws SQLException
	 */
	public static void loadState() throws PassException, SQLException {

		IDBManager userDB = new TableHandler("dentist");
		if (userDB.exists()) {
			AppData.INSTANCE.setUserList(new DentistList());
			CachedRowSet crs = (CachedRowSet) userDB.loadDB();
			while (crs.next()) {
				int userNo = crs.getInt("userNo");
				String username = crs.getString("username");
				String password = crs.getString("password");
				String name = crs.getString("name");
				String address = crs.getString("address");
				String phone = crs.getString("phone");
				Dentist d = new Dentist(username, password, name, address, phone);
				d.setUserNo(userNo);
				AppData.INSTANCE.getUserList().add(d);
			}
			if (AppData.INSTANCE.getUserList().isEmpty()) {
				AppData.INSTANCE.setUserList(Defaults.createDentists());
			}
		} else {
			ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to find Dentist database", "");
			exwin.show();
		}

		IDBManager patientDB = new TableHandler("patient");
		if (patientDB.exists()) {
			AppData.INSTANCE.setPatientList(new PatientList());
			CachedRowSet crs = (CachedRowSet) patientDB.loadDB();
			while (crs.next()) {
				int patientNo = crs.getInt("patientNo");
				String name = crs.getString("name");
				String address = crs.getString("address");
				String phone = crs.getString("phone");
				Patient p = new Patient(name, address, phone);
				p.setPatientNo(patientNo);
				AppData.INSTANCE.getPatientList().add(p);
			}
		} else {
			ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to find Patient database", "");
			exwin.show();
		}

		IDBManager invoiceDB = new TableHandler("invoice");
		if (invoiceDB.exists()) {
			AppData.INSTANCE.setInvoiceList(new InvoiceList());
			CachedRowSet crs = (CachedRowSet) invoiceDB.loadDB();
			while (crs.next()) {
				int invoiceID = crs.getInt("invoiceID");
				String invoiceDate = crs.getString("invoiceDate");
				int patientNo = crs.getInt("patientNo");
				Invoice i = new Invoice(invoiceDate);
				i.setInvoiceID(invoiceID);
				AppData.INSTANCE.getInvoiceList().add(i);
				try {
					AppData.INSTANCE.getPatientList().getById(patientNo).addInvoice(i);
				} catch (Exception e) {
					// TODO silent fail for now
					System.out.println("cannot add invoice to app\n" + i);
				}
			}
		}

		IDBManager procDB = new TableHandler("procs");
		if (procDB.exists()) {
			AppData.INSTANCE.setProcedureList(new ProcedureList());
			CachedRowSet crs = (CachedRowSet) procDB.loadDB();
			while (crs.next()) {
				int procId = crs.getInt("procId");
				String procName = crs.getString("procName");
				double procCost = crs.getDouble("procCost");
				boolean disabled = crs.getBoolean("disabled");
				Procedure p = new Procedure(procName, procCost);
				p.setProcID(procId);
				p.setDisabled(disabled);
				AppData.INSTANCE.getProcedureList().add(p);
			}
			if (AppData.INSTANCE.getProcedureList().isEmpty()) {
				AppData.INSTANCE.setProcedureList(Defaults.createProcedures());
			}
		} else {
			ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to find Procedure database", "");
			exwin.show();
		}

		IDBManager invprocDB = new TableHandler("invoiceprocedure");
		if (invprocDB.exists()) {
			CachedRowSet crs = (CachedRowSet) invprocDB.loadDB();
			while (crs.next()) {
				int ipid = crs.getInt("ipid");
				int invoiceID = crs.getInt("invoiceID");
				int procedureID = crs.getInt("procedureID");
				try {
					Procedure proc = AppData.INSTANCE.getProcedureList().getById(procedureID);
					AppData.INSTANCE.getInvoiceList().getById(invoiceID).addProcedure(proc);
				} catch (Exception e) {
					// TODO silent fail for now
					System.out.println(ipid + ":Unable to add proc " + procedureID + " to invoice " + invoiceID);
				}
			}
		}

		IDBManager paymentDB = new TableHandler("payment");
		if (paymentDB.exists()) {
			AppData.INSTANCE.setPaymentList(new PaymentList());
			CachedRowSet crs = (CachedRowSet) paymentDB.loadDB();
			while (crs.next()) {
				int paymentID = crs.getInt("paymentID");
				double paymentAmt = crs.getDouble("paymentAmt");
				String paymentDate = crs.getString("paymentDate");
				int invoiceID = crs.getInt("invoiceID");
				Payment p = new Payment(paymentAmt, paymentDate);
				p.setPaymentID(paymentID);
				AppData.INSTANCE.getPaymentList().add(p);
				try {
					AppData.INSTANCE.getInvoiceList().getById(invoiceID).getIn_paymentList().add(p);
				} catch (Exception e) {
					// TODO silent fail for now
					System.out.println("cannot add payment to app\n" + p);
				}
			}
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

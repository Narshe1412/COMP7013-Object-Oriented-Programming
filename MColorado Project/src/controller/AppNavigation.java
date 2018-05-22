package controller;

import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import persistence.StateLoader;
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
	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param window
	 *            a reference to the main window
	 */
	public AppNavigation(AppController controller) {
		this.controller = controller;
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
	public void exitApp() {
		new StateLoader(controller).saveConfig();
		Platform.exit();
	}



}

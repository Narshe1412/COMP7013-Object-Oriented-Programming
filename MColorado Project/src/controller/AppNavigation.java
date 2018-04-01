package controller;

import javafx.stage.Stage;
import model.*;

public class AppNavigation {
	private enum TypeOfChange {USER_CHANGED, PATIENT_CHANGED, INVOICE_ADDED}
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
		//TODO Make sure that every time we load a new patient all the previous tabs are removed and the proper ones are added to the system

	}
}

package controller;

import model.*;

public class AppNavigation {
	private enum TypeOfChange {USER_CHANGED, PATIENT_CHANGED}
	
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

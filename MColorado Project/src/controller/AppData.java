package controller;

import model.Dentist;
import model.Patient;

public enum AppData {
	INSTANCE;
	
	private Patient currentPatient;
	public Patient getCurrentPatient() {return currentPatient;}
	public void setCurrentPatient(final Patient p) { currentPatient = p; };
	
	private Dentist currentUser;
	public Dentist getCurrentUser() {return currentUser;}
	public void setCurrentUser(final Dentist user) {this.currentUser = user;}

	
}

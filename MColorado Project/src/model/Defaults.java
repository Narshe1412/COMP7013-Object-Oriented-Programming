package model;

import java.util.List;

import exception.PassException;
/**
 * Default values for the system, for testing or initial load purposes
 * @author Manuel Colorado
 *
 */
public class Defaults {
	private static ProcedureList procedures;
	private static List<Dentist> dentists;
	private static PatientList patients;

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

	public static List<Dentist> createDentists() throws PassException {
		dentists = new DentistList();
		dentists.add(new Dentist("admin", "11111111"));
		dentists.add(new Dentist("nurse", "11111111"));
		return dentists;
	}
	
	public static List<Patient> createPatient() {
		patients = new PatientList();
		patients.addNew("John Doe", "23 My Street", "555-2323");	
		patients.addNew("Angela Doe", "Another Street", "111-333");
		patients.addNew("Will Iam Shakesper", "Somewhere in London", "333-111-55");
		return patients;
	}
}

package model;

import java.util.ArrayList;
import java.util.List;
/**
 * Default values for the system, for testing or initial load purposes
 * @author Manuel Colorado
 *
 */
public class Defaults {
	private ArrayList<Procedure> procedures;
	private static List<Dentist> dentists;

	public List<Procedure> createProcedures() {

		procedures = new ArrayList<Procedure>();
		procedures.add(new Procedure("Examination", 20));
		procedures.add(new Procedure("Hygienist Scale and Polish", 60));
		procedures.add(new Procedure("Root planing", 80));
		procedures.add(new Procedure("Panoramic X-ray", 60));
		procedures.add(new Procedure("Fillings", 80));
		procedures.add(new Procedure("Root Canal Treatment", 400));
		procedures.add(new Procedure("Crowns", 800));
		procedures.add(new Procedure("Dentures", 450));
		procedures.add(new Procedure("Teeth Whitening", 250));

		return procedures;
	}

	public static List<Dentist> createDentists() throws Exception {
		dentists = new DentistList();
		dentists.add(new Dentist("admin", "admin"));
		dentists.add(new Dentist("jdoe", "doe"));
		return dentists;
	}
}
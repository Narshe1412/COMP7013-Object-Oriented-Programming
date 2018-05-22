package model;

import java.util.ArrayList;
import java.util.List;

import exception.PassException;
/**
 * Default values for the system, for testing or initial load purposes
 * @author Manuel Colorado
 *
 */
public class Defaults {
	private static List<Procedure> procedures;
	private static List<Dentist> dentists;
	
	public static List<Procedure> createProcedures() {

		procedures = new ArrayList<Procedure>();
		procedures.add(new Procedure("Examination", 20));
		procedures.add(new Procedure("Hygienist Scale and Polish", 60));
		procedures.add(new Procedure("Root planing", 80));
		procedures.add(new Procedure("Panoramic X-ray", 60));
		procedures.add(new Procedure("Fillings", 80));
		procedures.add(new Procedure("Root Canal Treatment", 400));
		procedures.add(new Procedure("Crowns", 800));
		procedures.add(new Procedure("Dentures", 450));
		Procedure disabled = new Procedure("Disabled", 250);
		disabled.setDisabled(true);
		procedures.add(disabled);
		procedures.add(new Procedure("Teeth Whitening", 250));

		return procedures;
	}

	public static List<Dentist> createDentists() throws PassException {
		dentists = new ArrayList<Dentist>();
		dentists.add(new Dentist("admin", "11111111"));
		dentists.add(new Dentist("nurse", "11111111"));
		return dentists;
	}

}

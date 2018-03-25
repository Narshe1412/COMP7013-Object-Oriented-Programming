package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import classes.Patient;
import classes.PatientHistory;
import classes.Procedure;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import panes_menus.MySplitPane;
import panes_menus.MyMenu;
import panes_menus.MyTabPane;

public class Controller {
	private Stage stage;
	private Stage secondStage;
	private static Controller instance;
	private ArrayList<Patient> patients;
	public Controller() {
		instance = this;
		secondStage = new Stage();
		setupPatient();
	}

	public static Controller getInstance() {
		if (instance == null)
			return new Controller();

		return instance;
	}

	public void setStage(Stage st) {
		this.stage = st;
	}

	public Stage getStage() {
		return this.stage;
	}

	public void setScene(Scene scene) {
		this.stage.setScene(scene);
		this.stage.show();
	}

	public void runStart() {
		List<String> choices = new ArrayList<>();
		choices.add("Tabs Windows");
		choices.add("Menu Windows");
		choices.add("Multi Windows");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Tabs Windows", choices);
		dialog.setTitle("Startup");
		dialog.setHeaderText("Window Choice");
		dialog.setContentText("Choose your Window type:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			handleChoice(result.get());
		}
	}

	private void handleChoice(String result) {
		switch (result) {
		case "Tabs Windows":
			new MyTabPane();
			break;
		case "Menu Windows":
			new MyMenu();
			break;
		case "Multi Windows":
			new MySplitPane();
			break;
		}

	}
	
	private void setupPatient(){
		patients = new ArrayList<Patient>();
		Procedure proc = new  Procedure("Tooth Removal","TR01",69.99);
		Procedure proc2 = new  Procedure("Whitening","W01",119.99);
		Procedure proc3 = new  Procedure("Root Canal","RC01",699.99);
		Patient pa = new Patient("Diarmuid","McCarthy","Cork, ireland",21);
		PatientHistory ph = new PatientHistory("2017-01-01",proc,false);
		PatientHistory ph2 = new PatientHistory("2018-01-01",proc2,false);
		pa.addPatientHistory(ph);
		pa.addPatientHistory(ph2);
		patients.add(pa);
		
		Patient pa2 = new Patient("Denis","Murphy","Kerry, ireland",29);
		PatientHistory ph3 = new PatientHistory("2017-09-01",proc,true);
		PatientHistory ph3b = new PatientHistory("2017-10-01",proc,true);
		PatientHistory ph3c = new PatientHistory("2017-11-01",proc,true);
		PatientHistory ph3d = new PatientHistory("2017-12-01",proc,true);
		PatientHistory ph4 = new PatientHistory("2018-03-01",proc3,false);
		pa.addPatientHistory(ph3);
		pa.addPatientHistory(ph3b);
		pa.addPatientHistory(ph3c);
		pa.addPatientHistory(ph3d);
		pa.addPatientHistory(ph4);
		patients.add(pa2);
	}
	
	public ArrayList<Patient> getPatients(){
		return this.patients;
	}
	
	public Patient getPatient(int id){
		for(Patient p:patients){
			if(p.getID()==id){
				return p;
			}
		}
		return null; //not found
	}
	
	public void showSecondStage(Scene scene){
		secondStage.setScene(scene);
		secondStage.show();
	}
}

package classes;

import java.util.ArrayList;

public class Patient {
	private static int PATIENT_ID = 1;
	private int ID;
	private String fName,lName,address;
	private int age;
	private ArrayList<PatientHistory> history;
	
	public Patient(String fn, String ln, String addr, int age){
		this.ID = PATIENT_ID;
		PATIENT_ID++;
		this.age = age;
		this.fName = fn;
		this.lName = ln;
		this.address = addr;
		history = new ArrayList<PatientHistory>();
	}
	
	public int getID(){
		return this.ID;
	}
	public String getName(){
		return this.fName+" "+this.lName;
	}
	
	public int getAge(){
		return this.age;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public ArrayList<PatientHistory> getPatientHistory(){
		return this.history;
	}
	
	public void addPatientHistory(PatientHistory p){
		this.history.add(p);
	}
}

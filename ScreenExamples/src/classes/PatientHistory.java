package classes;

public class PatientHistory {
	private static int PHID = 1;
	private int ID;
	private String treatment_date;
	private Procedure procedure;
	private boolean paid;
	
	public PatientHistory(String date, Procedure proc, boolean pay){
		this.ID = PHID;
		PHID++;
		this.treatment_date = date;
		this.procedure = proc;
		setPaid(pay);
	}

	public int getID(){
		return this.ID;
	}
	public String getTreatmentDate(){
		return this.treatment_date;
	}
	
	public Procedure getProcedure(){
		return this.procedure;
	}
	
	public boolean getPaid(){
		return this.paid;
	}
	
	public void setPaid(boolean pay){
		this.paid = pay;
	}
}

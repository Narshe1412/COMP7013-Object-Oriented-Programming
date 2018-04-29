/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

public class Patient{
	private String patientName;
	private String patientAddress;
	private String patientPhone;
	private int patientId;
	Database db;
	
	public Patient(){}
	public Patient (String patientName,String patientAddress,String patientPhone,int patientId){
		this.patientName = patientName;
		this.patientAddress = patientAddress;
		this.patientPhone = patientPhone;
		this.patientId = patientId;
        }
	public String toString()
	{
		return (patientName+""+patientAddress+"  "+patientPhone +"  "+ patientId);
	}

	
	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getpatientAddress() {
		return patientAddress;
	}

	public void setpatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}

	public String getPatientPhone() {
		return patientPhone;
	}

	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}	
	
	public void save(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "Insert into patient(patientName,patientAddress,patientPhone)values"
    			+ " ('"+this.patientName+"','"+this.patientAddress+"','"+this.patientPhone+"')";
    	System.out.println(sql);
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
    
    public void update(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "update patient"
    			+ " set patientName='"+this.patientName+"',"
    					+ "patientAddress='"+this.patientAddress+"',"
    							+ "patientPhone='"+this.patientPhone+"'"
    			+ " where patientID = "+this.patientId;
    	System.out.println(sql);
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
    
    public void delete(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "delete from patient where patientID = "+this.patientId;
    	System.out.println(sql);
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
    	
}


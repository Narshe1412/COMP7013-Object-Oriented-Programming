/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/**
 * @author Owner
 **/
public class PatientProcedure{
    private int ppID;
	private Procedure proc;
    private String date;
    private Payment pay;
    private int patientID;
    
    Database db;
    
    public PatientProcedure(){}
    
    public PatientProcedure(int pp,int pID,Procedure p, String d, Payment py){
        this.ppID=pp;
        this.patientID = pID;
    	this.proc=p;
        this.date=d;
        this.pay=py;
    }
        
    public Procedure getProcedure(){
        return this.proc;
    }

    public void setPayment(Payment s){
        this.pay= s;
    }
        
    public Payment getPayment(){
        return this.pay;
    }
    
    public String getDate(){
        return this.date;
    }
    
    public int getPPID(){
    	return this.ppID;
    }
    
    public void setPPID(int id){
    	this.ppID = id;
    }
    
    public void save(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "Insert into patientprocedure(patientID,procedureID,paymentID,date)values"
    			+ " ("+this.patientID+","+this.proc.getProcedureId()+",'"+this.pay.getPaymentId()+"','"+this.date+"')";
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
    
    public void update(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "Update patientprocedure setpatientID="+this.patientID+","
    			+ "procedureID="+this.proc.getProcedureId()+","
    					+ "paymentID='"+this.pay.getPaymentId()+"',"
    							+ "date ='"+this.date+"')"; 
		db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
    
    public void delete(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "delete from patientprocedure where ID = "+this.ppID+" and paymentID='"+this.pay.getPaymentId()+"'";
    	System.out.println(sql);
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
}

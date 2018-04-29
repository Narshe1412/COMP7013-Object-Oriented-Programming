/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.io.Serializable;

public class Procedure implements Serializable{
	private String procedureName;
	private double procedureCost;
	private int procedureId;
	Database db;
	
	public Procedure(){}
	
	public Procedure (String procedureName,double procedureCost,int procedureId ){
		this.procedureName = procedureName;
		this.procedureCost = procedureCost;
		this.procedureId = procedureId;
	
}
	public String toString()
	{
		return (procedureName+"  "+procedureName+"  "+ procedureId+"  "+procedureCost );
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public double getProcedureCost() {
		return procedureCost;
	}
	public void setProcedureCost(double procedureCost) {
		this.procedureCost = procedureCost;
	}
	public int getProcedureId() {
		return procedureId;
	}
	public void setProcedureId(int procedureId) {
		this.procedureId = procedureId;
	}
	
	public void save(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "Insert into procedures(procedureName,procedureCost)values"
    			+ " ('"+this.procedureName+"',"+this.procedureCost+")";
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
    
    public void update(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "update procedures"
    			+ " set procedureName='"+this.procedureName+"',"
    					+ "procedureCost="+this.procedureCost
    			+ " where procedureID = "+this.procedureId;
    	System.out.println(sql);
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
    
    public void delete(){
    	db= new Database();
    	db.getDBConnection();
    	String sql= "delete from procedures where procedureID = "+this.procedureId;
    	db.Execute(sql);
    	db.Commit();
    	db.CloseDB();
    }
}


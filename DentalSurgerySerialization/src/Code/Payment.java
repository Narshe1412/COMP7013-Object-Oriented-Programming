/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;
public class Payment{
	   	private String paymentDate;
		private double paymentAmount;
		private String paymentId;
		private boolean isPaid;
		Database db;
		
		public Payment(){}
		public Payment (String paymentDate,double paymentAmount,String payId,boolean ispaid){
			this.paymentDate = paymentDate;
			this.paymentAmount = paymentAmount;
			this.paymentId = payId;
			this.isPaid = isPaid;			
        }
                
		public String toString()
		{
			return (paymentDate+"  "+paymentAmount+"  "+ paymentId+"  "+isPaid );
		}
		public String getPaymentDate() {
			return paymentDate;
		}
		public void setPaymentDate(String paymentDate) {
			this.paymentDate = paymentDate;
		}
		public double getPaymentAmount() {
			return paymentAmount;
		}
		public void setPaymentAmount(double paymentAmount) {
			this.paymentAmount = paymentAmount;
		}
		public String getPaymentId() {
			return this.paymentId;
		}
		public void setPaymentId(String payId) {
			this.paymentId = payId;
		}
		
		public boolean isPaid() {
			return isPaid;
		}
		public void setPaid(boolean isPaid) {
			this.isPaid = isPaid;
		}
		public void save(){
	    	db= new Database();
	    	db.getDBConnection();
	    	String sql= "Insert into payment(paymentAmount,PaymentID,isPaid)values"
	    			+ " ("+this.paymentAmount+",'"+this.paymentId+"',"+this.isPaid+")";
	    	db.Execute(sql);
	    	db.Commit();
	    	db.CloseDB();
	    }
	    
	    public void update(){
	    	db= new Database();
	    	db.getDBConnection();
	    	String sql= "update payment"
	    			+ " set paymentAmount="+this.paymentAmount+","
	    					+ "paymentDate='"+this.paymentDate+"',"
	    					+ "isPaid="+this.isPaid
	    			+ " where paymentID = '"+this.paymentId+"'";
	    	System.out.println(sql);
	    	db.Execute(sql);
	    	db.Commit();
	    	db.CloseDB();
	    }
	    
	    public void delete(){
	    	db= new Database();
	    	db.getDBConnection();
	    	String sql= "delete from payment where paymentID = '"+this.paymentId+"'";
	    	db.Execute(sql);
	    	db.Commit();
	    	db.CloseDB();
	    }
}



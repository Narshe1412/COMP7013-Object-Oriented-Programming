package Code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    Connection conn;
    Statement st;
    public ResultSet rs;
    
    public Database(){
    }
    
    //Make the connection to the database
    public  void  getDBConnection() {
        //System.out.println("Open1");
        try {
            Class.forName ("com.mysql.jdbc.Driver").newInstance();
            									//conn:type://ip(localhost is 127.0.0.1):port/databasename
            conn = DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/dentist",
            									"root",//username
            									"");//password
            //conn = DriverManager.getConnection (url, "root", "root");
            st = conn.createStatement();
            //conn.setAutoCommit(false);
        }//cathc all the exceptions
        catch (SQLException | ClassNotFoundException | 
        		InstantiationException | IllegalAccessException ex) {
           System.out.println("Database is not available");
           ex.printStackTrace();
        }
    }                                                            
    
    //close the database connection
    public void CloseDB(){
        System.out.println("Close");
        try{
           conn.close();
        }catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
    public ResultSet getResultSet(){
        return rs;
    }
    
    public void QueryDB(String Query){
        try{
            rs = st.executeQuery(Query);
        }catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    // this will execute a query which may cause some changes to the database, such as an update, insert or delete
    // it does not return any resultset
    public void Execute(String Query){
        try{
            st.executeUpdate(Query);
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
    public void Commit(){
        try{
            conn.commit();
        }catch(SQLException e){
            System.err.println("Unable to commit changes");
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
    public int rowCount(){
        int count = 0;
         try{
            rs.last();
            count = rs.getRow();
            rs.beforeFirst();
        }catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return count;
    }
}

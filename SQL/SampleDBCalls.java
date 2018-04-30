package Code;

import java.sql.SQLException;
import java.util.ArrayList;

public class SampleDBCalls {

	public static void main(String[] args) {
		Database db = new Database();
    	db.getDBConnection();
    	String query = "select * from patient";
    	System.out.println(query);
    	db.QueryDB(query);
    	try {
			while(db.rs.next()){
				//create your object from teh resultset here	
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		}
    	db.CloseDB();
	}
}

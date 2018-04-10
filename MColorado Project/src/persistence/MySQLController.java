//TODO Refactoring
package persistence;

import java.sql.*;



public class MySQLController implements IDBManager{

	public MySQLController() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB_URL = "jdbc:mysql://dbserver:3306/world";

			Connection con = DriverManager.getConnection( DB_URL ,"","");
			Statement s = con.createStatement();
			s.execute("create table TEST12345 ( firstcolumn integer )");
			s.execute("insert into TEST12345 values(1)");
			s.execute("select firstcolumn from TEST12345");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createDB() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object loadDB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveDB(Object database) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}
}



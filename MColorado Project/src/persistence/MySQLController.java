package persistence;

import java.sql.*;

public class MySQLController {

	private Connection con;
	private final String DB_URL;
	private String database;

	public MySQLController() {
		DB_URL = "jdbc:mysql://localhost:3306/bocabites";
		database = "bocabites";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MySQLController(String url, String port, String database) {
		DB_URL = "jdbc:mysql://" + url + port + "/" + database;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean openConnection() {
		try {
			setCon(DriverManager.getConnection(DB_URL, "root", ""));
			return true;
		} catch (SQLException e) {
			setCon(null);
			return false;
		}
	}

	public boolean closeConnection() {
		try {
			getCon().close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public void createDatabase() {

		try {
			openConnection();
			Statement s = getCon().createStatement();
			s.execute("create database + " + database);
			s.execute("use " + database);
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Object loadDB() {
		try {
			Statement s = getCon().createStatement();
			s.execute("create table ( firstcolumn integer )");
			s.execute("insert into TEST12345 values(1)");
			s.execute("select firstcolumn from TEST12345");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean saveDB(Object database) {
		// TODO Auto-generated method stub
		return false;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

}

package persistence;

import java.sql.*;

public abstract class MySQLController {

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
			getCon().setAutoCommit(false);
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

	public abstract Object loadDB();

	public abstract boolean saveDB(Object database);

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

}

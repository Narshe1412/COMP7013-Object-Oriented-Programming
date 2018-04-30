package persistence;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.NotImplemented;

import exception.DBException;

public class TableHandler extends MySQLController implements IDBManager {

	private String table;

	public TableHandler(String tableName) {
		this.table = tableName;
	}

	@Override
	public void createDB() {
		String query = "CREATE TABLE " + table + " IF NOT EXISTS;";
		try {
			executeQuery(query);
		} catch (DBException e) {
		}
	}

	/**
	 * @param query
	 * @throws DBException
	 */
	private ResultSet executeQuery(String query) throws DBException {
		openConnection();
		ResultSet rs;
		try {
			Statement s = getCon().createStatement();
			rs = s.executeQuery(query);
		} catch (SQLException e) {
			rs = null;
			throw new DBException(e, "Unable to access the database");
		} finally {
			closeConnection();
		}
		return rs;
	}

	/**
	 * Verifies if the table exits in the database
	 * 
	 * @return true if found the table, false otherwise
	 */
	public boolean exists() {
		try {
			openConnection();
			DatabaseMetaData meta = getCon().getMetaData();
			ResultSet res = meta.getTables(null, null, table, new String[] { "TABLE" });
			while (res.next()) {
				closeConnection();
				return true;
			}
			return false;
		} catch (SQLException e) {
			closeConnection();
			return false;
		}
	}

	@Override
	public Object loadDB() {
		String query = "SELECT * FROM " + table + ";";
		ResultSet rs;
		try {
			rs = executeQuery(query);
		} catch (DBException e) {
			rs = null;
		}
		return rs;
	}

	@Override
	public boolean saveDB(Object database) {
		// Not Implemented
		return false;
	}

}

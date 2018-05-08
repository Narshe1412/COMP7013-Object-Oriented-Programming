package persistence;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import exception.DBException;

class TableHandler extends MySQLController implements IDBManager {

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
	private ResultSet executeQuery(final String query) throws DBException {
		ResultSet rs;
		try {
			Statement s = getCon().createStatement();
			rs = s.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			rs = null;
			throw new DBException(e, "Unable to access the database");
		}
		return rs;
	}
	
	public CachedRowSet executeStatement(final PreparedStatement pstmt) {
		//TODO handle queries with variables
		
		try {
			openConnection();
			ResultSet rs;
			rs = pstmt.executeQuery();
			RowSetFactory factory = RowSetProvider.newFactory();
			CachedRowSet crs = factory.createCachedRowSet();
			crs.populate(rs);
			return crs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		
		return null;
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
		} catch (SQLException | NullPointerException e) {
			DBException.fatalDbErrorDialog(e);
		} 
		return false;
	}

	

	@Override
	public Object loadDB() {
		openConnection();
		String query = "SELECT * FROM " + table + ";";
		ResultSet rs;
		try {
			rs = executeQuery(query);
			RowSetFactory factory = RowSetProvider.newFactory();
			CachedRowSet crs = factory.createCachedRowSet();
			crs.populate(rs);
			return crs;
		} catch (DBException | SQLException e) {
			DBException.fatalDbErrorDialog(e, "Error loading table " + table);
		} finally {
			closeConnection();
		}
		return null;
	}

	@Override
	public boolean saveDB(Object database) {
		// Not Implemented
		return false;
	}

}

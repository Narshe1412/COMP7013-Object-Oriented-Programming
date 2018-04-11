package persistence;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableHandler extends MySQLController implements IDBManager {

	private String table;

	public TableHandler(String tableName) {
		this.table = tableName;
	}

	@Override
	public void createDB() {
		// TODO Auto-generated method stub

	}

	/**
	 * Verifies if the table exits in the database
	 * @return true if found the table, false otherwise
	 */
	public boolean exists() {
		openConnection();
		try {
			DatabaseMetaData meta = getCon().getMetaData();
			ResultSet res = meta.getTables(null, null, table, new String[] { "TABLE" });
			while (res.next()) {
				closeConnection();
				return true;
			}
		} catch (SQLException e) {
			closeConnection();
			return false;
		}
		closeConnection();
		return false;
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

}

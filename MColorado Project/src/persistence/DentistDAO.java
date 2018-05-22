package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import exception.PassException;
import model.Dentist;

public class DentistDAO implements IDBOperationRepository<Dentist> {

	TableHandler userDB;

	public DentistDAO() {
		userDB = new TableHandler("dentist");
	}

	@Override
	public int add(Dentist contents) {
		if (userDB.exists()) {
			try {
				String sql = "INSERT INTO dentist (userNo, username, password, name, address, phone) "
						+ "VALUES (NULL, ?, ?, ?, ?, ?);";
				userDB.openConnection();
				PreparedStatement pstmt = userDB.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, contents.getUsername());
				pstmt.setString(2, contents.getHashedPassword());
				pstmt.setString(3, contents.getName());
				pstmt.setString(4, contents.getAddress());
				pstmt.setString(5, contents.getPhone());

				int userID = userDB.executeUpdate(pstmt);
				if (userID > 0) {
					contents.setUserNo(userID);
					return userID;
				} else {
					throw new SQLException("Unable to create user.");
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				userDB.closeConnection();
			}
		}
		return -1;
	}

	@Override
	public Dentist getByID(int id) {
		if (userDB.exists()) {
			try {
				String sql = "SELECT * FROM dentist WHERE userNo = ?";
				userDB.openConnection();
				PreparedStatement pstmt = userDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);
				CachedRowSet crs = (CachedRowSet) userDB.executeStatement(pstmt);
				while (crs.next()) {
					int userNo = crs.getInt("userNo");
					String username = crs.getString("username");
					String password = crs.getString("password");
					String name = crs.getString("name");
					String address = crs.getString("address");
					String phone = crs.getString("phone");
					Dentist d = new Dentist(username, password, name, address, phone);
					d.setUserNo(userNo);
					return d;
				}
			} catch (SQLException | PassException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				userDB.closeConnection();
			}
		}
		return null;
	}
	
	public Dentist getByUsername(String dentistUsername) {
		if (userDB.exists()) {
			try {
				String sql = "SELECT * FROM dentist WHERE username = ? LIMIT 1";
				userDB.openConnection();
				PreparedStatement pstmt = userDB.getCon().prepareStatement(sql);
				pstmt.setString(1, dentistUsername);
				CachedRowSet crs = (CachedRowSet) userDB.executeStatement(pstmt);
				while (crs.next()) {
					int userNo = crs.getInt("userNo");
					String username = crs.getString("username");
					String password = crs.getString("password");
					String name = crs.getString("name");
					String address = crs.getString("address");
					String phone = crs.getString("phone");
					Dentist d = new Dentist(username, password, name, address, phone);
					d.setUserNo(userNo);
					return d;
				}
			} catch (SQLException | PassException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				userDB.closeConnection();
			}
		}
		return null;
	}

	@Override
	public Iterable<Dentist> getAll() {
		ArrayList<Dentist> returnedList = new ArrayList<>();
		if (userDB.exists()) {
			CachedRowSet crs = (CachedRowSet) userDB.loadDB();
			try {
				while (crs.next()) {
					int userNo = crs.getInt("userNo");
					String username = crs.getString("username");
					String password = crs.getString("password");
					String name = crs.getString("name");
					String address = crs.getString("address");
					String phone = crs.getString("phone");
					boolean deleted = crs.getBoolean("deleted");
					Dentist d = new Dentist(name, address, phone, username);
					d.setHashedPassword(password);
					d.setUserNo(userNo);
					if(!deleted) {
						returnedList.add(d);	
					}
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	@Override
	public boolean update(Dentist contents) {
		if (userDB.exists()) {
			try {
				String sql = "UPDATE dentist SET " + "username = ?, " + "password = ?, " + "name = ?, "
						+ "address = ?, " + "phone = ? " + "WHERE dentist.userNo = ?;";
				userDB.openConnection();
				PreparedStatement pstmt = userDB.getCon().prepareStatement(sql);
				pstmt.setString(1, contents.getUsername());
				pstmt.setString(2, contents.getHashedPassword());
				pstmt.setString(3, contents.getName());
				pstmt.setString(4, contents.getAddress());
				pstmt.setString(5, contents.getPhone());
				pstmt.setInt(6, contents.getUserNo());
				if (userDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				userDB.closeConnection();
			}
		}
		return false;
	}

	@Override
	public boolean remove(Dentist contents) {
		if (userDB.exists()) {
			try {
				String sql = "UPDATE dentist SET deleted = 1 WHERE dentist.userNo = ?";
				userDB.openConnection();
				PreparedStatement pstmt = userDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, contents.getUserNo());
				if (userDB.executeUpdate(pstmt) > 0) {
					return true;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			} finally {
				userDB.closeConnection();
			}
		}
		return false;
	}

}

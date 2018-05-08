package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	public boolean add(Dentist contents) {
		// TODO Auto-generated method stub
		return false;
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
					Dentist d = new Dentist(username, password, name, address, phone);
					d.setUserNo(userNo);
					returnedList.add(d);
				}
			} catch (SQLException | PassException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Dentist database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	@Override
	public boolean update(Dentist contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Dentist contents) {
		// TODO Auto-generated method stub
		return false;
	}

}

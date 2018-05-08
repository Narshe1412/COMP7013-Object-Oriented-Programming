package persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import exception.ExceptionDialog;
import model.Payment;

public class PaymentDAO implements IDBOperationRepository<Payment> {
	TableHandler paymentDB;

	public PaymentDAO() {
		paymentDB = new TableHandler("payment");

	}

	@Override
	public boolean add(Payment contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Payment getByID(int id) {
		if (paymentDB.exists()) {

			try {
				String sql = "SELECT * FROM payment WHERE paymentID = ?";
				paymentDB.openConnection();
				PreparedStatement pstmt = paymentDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);

				CachedRowSet crs = (CachedRowSet) paymentDB.executeStatement(pstmt);
				while (crs.next()) {
					int paymentID = crs.getInt("paymentID");
					double paymentAmt = crs.getDouble("paymentAmt");
					String paymentDate = crs.getString("paymentDate");
					Payment p = new Payment(paymentAmt, paymentDate);
					p.setPaymentID(paymentID);
					return p;
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Payment database", "");
				exwin.show();
			} catch (NullPointerException e) {
				// Resultset is empty, no need to action
			} finally {
				paymentDB.closeConnection();
			}
		}
		return null;
	}

	@Override
	public Iterable<Payment> getAll() {
		ArrayList<Payment> returnedList = new ArrayList<>();
		if (paymentDB.exists()) {
			CachedRowSet crs = (CachedRowSet) paymentDB.loadDB();
			try {
				while (crs.next()) {
					int paymentID = crs.getInt("paymentID");
					double paymentAmt = crs.getDouble("paymentAmt");
					String paymentDate = crs.getString("paymentDate");
					int invoiceID = crs.getInt("invoiceID");
					Payment p = new Payment(paymentAmt, paymentDate);
					p.setPaymentID(paymentID);
					returnedList.add(p);
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Payment database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	public Iterable<Payment> getAllFromInvoice(final int id) {
		ArrayList<Payment> returnedList = new ArrayList<>();
		if (paymentDB.exists()) {

			try {
				String sql = "SELECT * FROM payment WHERE invoiceID = ?";
				paymentDB.openConnection();
				PreparedStatement pstmt = paymentDB.getCon().prepareStatement(sql);
				pstmt.setInt(1, id);

				CachedRowSet crs = (CachedRowSet) paymentDB.executeStatement(pstmt);
				while (crs.next()) {
					int paymentID = crs.getInt("paymentID");
					double paymentAmt = crs.getDouble("paymentAmt");
					String paymentDate = crs.getString("paymentDate");
					int invoiceID = crs.getInt("invoiceID");
					Payment p = new Payment(paymentAmt, paymentDate);
					p.setPaymentID(paymentID);
					returnedList.add(p);
					if (invoiceID == id) {
						returnedList.add(p);
					}
				}
			} catch (SQLException e) {
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Payment database", "");
				exwin.show();
			} catch (NullPointerException e) {
				// Resultset is empty, no need to action
			} finally {
				paymentDB.closeConnection();
			}
		}
		return returnedList;
	}

	@Override
	public boolean update(Payment contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Payment contents) {
		// TODO Auto-generated method stub
		return false;
	}

}

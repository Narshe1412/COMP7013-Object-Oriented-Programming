package persistence;

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
	public Iterable<Payment> getByID(int id) {
		// TODO Auto-generated method stub
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
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Invoice database", "");
				exwin.show();
			}
		}
		return returnedList;
	}

	public Iterable<Payment> getAllFromInvoice(final int id) {
		ArrayList<Payment> returnedList = new ArrayList<>();
		if (paymentDB.exists()) {
			String sql = null; //TODO SQL
			CachedRowSet crs = (CachedRowSet) paymentDB.executeStatement(sql);
			try {
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
				ExceptionDialog exwin = new ExceptionDialog("Critical error", "Unable to load Invoice database", "");
				exwin.show();
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

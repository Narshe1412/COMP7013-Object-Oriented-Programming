package exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class DBException extends Exception {

	private static final long serialVersionUID = 1L;

	public DBException(Exception e, String message) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		ExceptionDialog win = new ExceptionDialog("Database Error", message, errors.toString());
		win.show();
	}
}

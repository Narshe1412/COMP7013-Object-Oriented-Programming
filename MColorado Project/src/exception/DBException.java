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
	//TODO javadoc
	/**
	 * @param e
	 */
	public static void fatalDbErrorDialog(Exception e) {
		ExceptionDialog exWin = new ExceptionDialog("Fatal Error", "Unable to connect to database. \n\nShutting down...", e);
		exWin.showAndWait();
		System.exit(0);
	}

	//TODO javadoc
	public static void fatalDbErrorDialog(Exception e, String message) {
		ExceptionDialog exWin = new ExceptionDialog("Fatal Error", message + "\n\nShutting down...", e);
		exWin.showAndWait();
		System.exit(0);		
	}
}

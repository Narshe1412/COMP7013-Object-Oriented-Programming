package exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Creates an exception hanlder for generic unhandled exceptions
 * 
 * @author Manuel Colorado
 *
 */
public class UncaughtExcepHandler implements UncaughtExceptionHandler {
	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		StringWriter errors = new StringWriter();
		arg1.printStackTrace(new PrintWriter(errors));

		ExceptionDialog win = new ExceptionDialog("Critical Error", "The program encountered an error and must close.",
				errors.toString());
		win.show();
	}
}

class AppThread extends Thread {
	public AppThread() {
		setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));

				ExceptionDialog win = new ExceptionDialog("Critical Error",
						"The program encountered an error and must close.", errors.toString());
				win.show();
			}
		});
	}
}
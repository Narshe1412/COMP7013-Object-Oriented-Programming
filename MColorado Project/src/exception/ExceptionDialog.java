package exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Creates a dialog to show the exception stack trace to the user
 * 
 * @author Manuel Colorado
 *
 */
public class ExceptionDialog extends Alert {

	TextArea textArea;

	/**
	 * Constructor
	 * 
	 * @param header
	 *            String that will appear as header of the exception window
	 * @param context
	 *            String that will describe to the user what happened with the
	 *            exception
	 * @param e
	 *            Exception object containing the exception details
	 */
	public ExceptionDialog(String header, String context, Exception e) {
		this(header, context, "");

		// Extracts the stack trace
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		// Sets the text of the exception dialog as the stack trace
		setExceptionText(errors.toString());
	}

	/**
	 * Constructor
	 * 
	 * @param headerText
	 *            String that will appear as header of the exception window
	 * @param contextText
	 *            String that will describe to the user what happened with the
	 *            exception
	 * @param exceptionText
	 *            Text that will be passed as parameter describing the exception
	 *            stack trace
	 */
	public ExceptionDialog(String headerText, String contextText, String exceptionText) {
		super(AlertType.ERROR);
		setTitle("Exception Dialog");
		setHeaderText(headerText);
		setContentText(contextText);

		Label label = new Label("The exception stacktrace was:");

		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setWrapText(true);
		setExceptionText(exceptionText);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		getDialogPane().setExpandableContent(expContent);
	}

	/**
	 * Sets the exception expandable field
	 * 
	 * @param exTring
	 *            String that contains the text for the stack trace
	 */
	private void setExceptionText(String exTring) {
		textArea.setText(exTring);
	}
}

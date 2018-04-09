package exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionDialog extends Alert{
	
	TextArea textArea;
	
	public ExceptionDialog(String header, String context, Exception e) {
		this(header, context, "");
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));

		setExceptionText(errors.toString());
	}
	
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
	
	private void setExceptionText(String exTring) {
		textArea.setText(exTring);
	}
}

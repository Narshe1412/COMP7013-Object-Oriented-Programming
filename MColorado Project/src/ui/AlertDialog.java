package ui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Facade to simplify Alert dialog creation
 * 
 * @author Manuel Colorado
 *
 */
public class AlertDialog extends Alert {

	/**
	 * Creates an alert dialog with custom icon
	 * 
	 * @param type
	 *            Type of alert dialog to be created, uses AlertType built it enum
	 * @param title
	 *            String with title for the alert dialog, can be null
	 * @param header
	 *            String with header for the alert dialog, if null, the whole header
	 *            section is ignored
	 * @param content
	 *            String with text message that would be displayed in the alert box11
	 */
	public AlertDialog(AlertType type, String title, String header, String content) {
		super(type);
		super.setTitle(title);
		super.setHeaderText(header);
		super.setContentText(content);
		// Custom icon.
		Stage icon = (Stage) this.getDialogPane().getScene().getWindow();
		icon.getIcons().add(new Image(this.getClass().getResource("/assets/smile.png").toString()));
	}
}
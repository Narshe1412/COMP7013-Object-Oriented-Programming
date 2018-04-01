package ui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertDialog extends Alert{

	public AlertDialog(AlertType type, String title, String header, String content) {
		super(type);
		super.setTitle(title);
		super.setHeaderText(header);
		super.setContentText(content);
		// Add a custom icon.
		Stage icon = (Stage) this.getDialogPane().getScene().getWindow();
		icon.getIcons().add(new Image(this.getClass().getResource("/assets/smile.png").toString()));
	}
}
package ui;

import javafx.scene.control.Alert;

public class AlertDialog extends Alert{

	public AlertDialog(AlertType type, String title, String header, String content) {
		super(type);
		super.setTitle(title);
		super.setHeaderText(header);
		super.setContentText(content);
	}
}
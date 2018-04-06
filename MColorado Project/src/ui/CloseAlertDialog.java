package ui;

import java.util.Optional;

import controller.AppNavigation;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class CloseAlertDialog extends Alert{

	public CloseAlertDialog() {
		super(AlertType.WARNING);
		setTitle("Save and exit?");
		setHeaderText("Unsaved changes");
		setContentText("There are unsaved changes in the system, do you want to continue?");
		
		ButtonType btnOK = new ButtonType("Exit without saving");
		ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType btnSave = new ButtonType("Save and exit");
		
		getButtonTypes().setAll(btnSave, btnOK, btnCancel);
		
		Optional<ButtonType> result = showAndWait();
		if(result.get() == btnSave) {
			AppNavigation.saveState();
			Platform.exit();
		} else if (result.get() == btnOK) {
			Platform.exit();
		}
	}
}

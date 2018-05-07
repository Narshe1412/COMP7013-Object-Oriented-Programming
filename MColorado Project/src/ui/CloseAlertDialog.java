package ui;

import java.util.Optional;

import controller.AppNavigation;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 * Creates an alert window when the application is going to close.
 * 
 * @author Manuel Colorado
 *
 * @deprecated No longer in use by the application
 */
public class CloseAlertDialog extends Alert {

	public CloseAlertDialog() {
		super(AlertType.WARNING);
		setTitle("Save and exit?");
		setHeaderText("Unsaved changes");
		setContentText("There are unsaved changes in the system, do you want to continue?");

		ButtonType btnOK = new ButtonType("Exit without saving");
		ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType btnSave = new ButtonType("Save and exit");

		getButtonTypes().setAll(btnSave, btnOK, btnCancel);

		/**
		 * Gets the button clicked and proceeds accordingly
		 * <li>
		 * <ul>
		 * If OK is pressed, exits the application without recording the state
		 * </ul>
		 * <ul>
		 * If Save and Exit button is pressed, saves the state and exits the application
		 * </ul>
		 * <ul>
		 * If Cancel is pressed, does nothing (the event has been consumed previously
		 * </ul>
		 * </li>
		 * 
		 */
		Optional<ButtonType> result = showAndWait();
		if (result.get() == btnSave) {
			AppNavigation.saveState();
			Platform.exit();
		} else if (result.get() == btnOK) {
			Platform.exit();
		}
	}
}

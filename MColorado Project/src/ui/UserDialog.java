package ui;

import java.util.Optional;

import controller.AppState;
import exception.ExceptionDialog;
import exception.PassException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Dentist;

/**
 * Creates a dialog window to capture the details of a new user (Dentist)
 * 
 * @author Manuel Colorado
 * @version 1.0
 */
public class UserDialog extends Dialog<Dentist> {
	TextField userName;
	TextField userAddress;
	TextField userPhone;
	TextField user;
	Dentist toEdit;

	/**
	 * Constructor
	 * 
	 * @param toEdit
	 *            Dentist object that will be modified during this dialog. If null,
	 *            will be used to create a new user Dentis on the system
	 */
	public UserDialog(Dentist toEdit) {
		super();
		this.toEdit = toEdit;

		setTitle("User Details");
		setHeaderText("Please enter the user details:");
		DialogPane dialogPane = getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		user = new TextField();
		userName = new TextField();
		userAddress = new TextField();
		userPhone = new TextField();

		if (toEdit != null) {
			user.setText(toEdit.getUsername());
			userName.setText(toEdit.getName());
			userAddress.setText(toEdit.getAddress());
			userPhone.setText(toEdit.getPhone());
		}
		dialogPane.setContent(new VBox(10, new Label("Username:"), user, new Label("Name of the practicioner:"),
				userName, new Label("Address:"), userAddress, new Label("Phone:"), userPhone));
		Platform.runLater(user::requestFocus);

		final Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
		// Event that handles how the OK button behaves
		okButton.addEventFilter(ActionEvent.ACTION, ae -> {
			String txtUser = user.getText().trim();
			String txtName = userName.getText().trim();
			if (!Validator.stringValidator(txtName, 2, 250) || !Validator.stringValidator(txtUser, 2, 20)) {
				Validator.setStringValidation(txtName, 2, 250, userName, "Name of the user cannot be blank.");
				Validator.setStringValidation(txtUser, 2, 20, user,
						"Username needs to be between 2 and 20 alphanumeric characters");
				ae.consume(); // Consume OK button event due to failed validation
			}
		});
		// Add dialog icon
		Stage icon = (Stage) this.getDialogPane().getScene().getWindow();
		icon.getIcons().add(new Image(this.getClass().getResource("/assets/smile.png").toString()));
	}

	/**
	 * Validates and obtain the results from the edit form
	 * 
	 * @return a Dentist object with the details that have been introduced in the
	 *         UserDialog
	 */
	public Dentist getEdit() {
		setResultConverter((ButtonType button) -> {
			if (button == ButtonType.OK) {
				try {
					// Checks if the username introduced does not match another user in the system
					// or matches the current username
					if (AppState.INSTANCE.getUserList().find(user.getText().trim()) == null
							|| toEdit.getUsername() == user.getText().trim()) {
						// Validates the required fields username, name and phone
						if (Validator.stringValidator(userName.getText().trim(), 2, 250)
								&& Validator.stringValidator(user.getText().trim(), 2, 20)) {
							if (userPhone.getText().trim().equals("")
									|| Validator.unsignedIntValidator(userPhone.getText())) {
								return new Dentist(userName.getText(), userAddress.getText(), userPhone.getText(),
										user.getText().trim(), "11111111");
							} else {
								AlertDialog alert = new AlertDialog(AlertType.WARNING, "Phone Error",
										"Phone not numeric", "Phone needs to be a numeric value");
								alert.showAndWait();
							}

						}
					} else {
						AlertDialog alert = new AlertDialog(AlertType.WARNING, "Username Taken", "Username taken",
								"Chosen username is already taken in the system");
						alert.showAndWait();
					}
				} catch (PassException e) {
					ExceptionDialog exWin = new ExceptionDialog("Critical Error",
							"There was a problem creating the new user.", e);
					exWin.show();
				}
			}
			return null;
		});
		Optional<Dentist> result = showAndWait();

		result.ifPresent((Dentist results) -> {
			if (toEdit == null) {
				// Adds a new user to the system with the details that were introduced if no
				// initial Dentist object was passed
				toEdit = AppState.INSTANCE.getUserList().addNew(results);
			} else {
				// Edit the user details with the fields that were introduced on the system
				if (AppState.INSTANCE.getUserList().find(user.getText().trim()) == null) {
					toEdit.setName(userName.getText());
					toEdit.setAddress(userAddress.getText());
					toEdit.setPhone(userPhone.getText());
					toEdit.setUsername(user.getText());
				} else {
					AlertDialog alert = new AlertDialog(AlertType.WARNING, "Username Taken", "Username taken",
							"Chosen username is already taken in the system");
					alert.showAndWait();
				}
			}
		});
		return toEdit;
	}

}

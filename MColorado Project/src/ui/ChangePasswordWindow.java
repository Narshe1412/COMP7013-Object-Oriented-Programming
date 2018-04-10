package ui;

import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import exception.ExceptionDialog;
import exception.PassException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Dentist;

/**
 * Creates a new window that allows the user to change the password
 * 
 * @author Manuel Colorado
 *
 */
public class ChangePasswordWindow extends Stage {

	PasswordField txtOldPassword;
	PasswordField txtNewPassword;
	PasswordField txtRepeatPassword;

	/**
	 * Constructor
	 */
	public ChangePasswordWindow() {

		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));

		GridPane textFields = new GridPane();
		textFields.setAlignment(Pos.CENTER);
		textFields.setHgap(10);
		textFields.setVgap(10);
		textFields.setPadding(new Insets(10));
		Label oldPassword = new Label("Old Password:");
		txtOldPassword = new PasswordField();
		Label newPassword = new Label("New Password:");
		txtNewPassword = new PasswordField();
		Label repeatPassword = new Label("Repeat New Password:");
		txtRepeatPassword = new PasswordField();

		textFields.add(oldPassword, 0, 0);
		textFields.add(txtOldPassword, 1, 0);
		textFields.add(newPassword, 0, 1);
		textFields.add(txtNewPassword, 1, 1);
		textFields.add(repeatPassword, 0, 2);
		textFields.add(txtRepeatPassword, 1, 2);

		root.setCenter(textFields);

		StackPane center = new StackPane();
		Button btnSave = new Button("Save");
		btnSave.setOnAction(event -> savePassword());
		btnSave.setDefaultButton(true);
		center.getChildren().add(btnSave);
		root.setBottom(center);

		Scene scene = new Scene(root);
		setScene(scene);
		setTitle("Change Password");
		getIcons().add(new Image("/assets/smile.png"));
		setResizable(false);
	}

	/**
	 * Attempts to save the new password for the user
	 */
	private void savePassword() {
		Dentist user = AppState.INSTANCE.getCurrentUser();
		String oldPass = txtOldPassword.getText().trim();
		String newPass = txtNewPassword.getText().trim();
		String repeatPass = txtRepeatPassword.getText().trim();

		// Verify that the old password string is between 8 and 16 characters
		if (!Validator.stringValidator(oldPass, 8, 16)) {
			txtOldPassword.setTooltip(new Tooltip("Password needs to be between 8 and 16 characters"));
			txtOldPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px ;");
			txtOldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!oldValue.equals(newValue) && Validator.stringValidator(newValue, 8, 16)) {
					txtOldPassword.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
				} else {
					txtOldPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
				}
			});
		} else {
			try {
				// Verify if the old password matches the one in the database
				if (user.verifyPassword(oldPass)) {
					// Verify if the new password and repeat password fields are between 8 and 16
					// characters
					if (Validator.stringValidator(newPass, 8, 16) && Validator.stringValidator(repeatPass, 8, 16)) {
						if (newPass.equals(repeatPass)) {
							user.setPassword(newPass);
							AppData.INSTANCE.setSavedUser(null); // Password cleared
							AppNavigation.saveUsers();
							close();
						} else {
							AlertDialog passMismatch = new AlertDialog(AlertType.ERROR, "Wrong Details",
									"Password mismatch", "The new password introduced does not match.");
							passMismatch.showAndWait();
						}
					} else {
						// Add styling to fields that are not between 8 and 16
						if (!Validator.stringValidator(newPass, 8, 16)) {
							txtNewPassword.setTooltip(new Tooltip("Password needs to be between 8 and 16 characters"));
							txtNewPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
							txtNewPassword.textProperty().addListener((observable, oldValue, newValue) -> {
								if (!oldValue.equals(newValue) && Validator.stringValidator(newValue, 8, 16)) {
									txtNewPassword.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
								} else {
									txtNewPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
								}
							});
						}

						if (!Validator.stringValidator(repeatPass, 8, 16)) {
							txtRepeatPassword
									.setTooltip(new Tooltip("Password needs to be between 8 and 16 characters"));
							txtRepeatPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
							txtRepeatPassword.textProperty().addListener((observable, oldValue, newValue) -> {
								if (!oldValue.equals(newValue) && Validator.stringValidator(newValue, 8, 16)) {
									txtRepeatPassword.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
								} else {
									txtRepeatPassword.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
								}
							});
						}
					}

				} else {
					AlertDialog passWrong = new AlertDialog(AlertType.ERROR, "Wrong Details", "Wrong password",
							"The old password is incorrect.");
					passWrong.showAndWait();
				}
			} catch (PassException e) {
				ExceptionDialog exWin = new ExceptionDialog("Critical Error",
						"Error when saving and encrypting the new password.", e);
				exWin.show();
			}
		}

	}
}

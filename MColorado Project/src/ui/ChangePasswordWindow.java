package ui;

import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Dentist;

public class ChangePasswordWindow extends Stage {

	PasswordField txtOldPassword;
	PasswordField txtNewPassword;
	PasswordField txtRepeatPassword;

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

		Button btnSave = new Button("Save");
		btnSave.setOnAction(event -> savePassword());
		btnSave.setDefaultButton(true);
		root.setBottom(btnSave);

		Scene scene = new Scene(root);
		setScene(scene);
		setTitle("Change Password");
		getIcons().add(new Image("/assets/smile.png"));
		setResizable(false);
	}

	private void savePassword() {
		Dentist user = AppState.INSTANCE.getCurrentUser();

		try {
			if (user.verifyPassword(txtOldPassword.getText().trim())) {
				if (txtNewPassword.getText().trim().equals(txtRepeatPassword.getText().trim())) {
					user.setPassword(txtNewPassword.getText().trim());
					AppData.INSTANCE.setSavedUser(null); // Password cleared
					AppNavigation.saveUsers();
					close();
				} else {
					AlertDialog passMismatch = new AlertDialog(AlertType.ERROR, "Wrong Details", "Password mismatch",
							"The new password introduced does not match.");
					passMismatch.showAndWait();
				}
			} else {
				AlertDialog passWrong = new AlertDialog(AlertType.ERROR, "Wrong Details", "Wrong password",
						"The old password is incorrect.");
				passWrong.showAndWait();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

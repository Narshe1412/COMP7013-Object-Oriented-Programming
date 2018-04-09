package ui;

import java.util.Optional;

import controller.AppData;
import exception.ExceptionDialog;
import exception.PassException;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import model.Dentist;

public class UserDialog extends Dialog<Dentist> {
	TextField userName;
	TextField userAddress;
	TextField userPhone;
	TextField user;
	Dentist toEdit;

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
		dialogPane.setContent(new VBox(10, user, userName, userAddress, userPhone));
		Platform.runLater(user::requestFocus);
	}

	public Dentist getEdit() {
		setResultConverter((ButtonType button) -> {
			if (button == ButtonType.OK) {
				try {
					if (AppData.INSTANCE.getUserList().find(user.getText().trim()) == null) {
						return new Dentist(userName.getText(), userAddress.getText(), userPhone.getText(),
								user.getText().trim(), "1111");
					} else {
						AlertDialog alert = new AlertDialog(AlertType.WARNING, "Username Taken", "Username taken",
								"Chosen username is already taken in the system");
						alert.showAndWait();
					}

				} catch (PassException e) {
					ExceptionDialog exWin = new ExceptionDialog("Critical Error","There was a problem creating the new user.",e);
					exWin.show();
				}
			}
			return null;
		});
		Optional<Dentist> result = showAndWait();
		result.ifPresent((Dentist results) -> {
			if (toEdit == null) {
				toEdit = AppData.INSTANCE.getUserList().addNew(results);
			} else {
				if (AppData.INSTANCE.getUserList().find(user.getText().trim()) == null) {
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

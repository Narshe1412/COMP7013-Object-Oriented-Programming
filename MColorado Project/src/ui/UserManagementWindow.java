package ui;

import controller.AppData;
import controller.AppState;
import exception.ExceptionDialog;
import exception.PassException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Dentist;

public class UserManagementWindow extends Stage {

	private ObservableList<Dentist> userList;
	private TableView<Dentist> table;
	private AppMenu parent;

	public UserManagementWindow(AppMenu parent) {
		this.parent = parent;
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));

		table = new TableView<Dentist>();

		userList = FXCollections.observableArrayList(AppData.INSTANCE.getUserList());
		table.setItems(userList);

		TableColumn<Dentist, String> colUser = new TableColumn<Dentist, String>("Username");
		colUser.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
		TableColumn<Dentist, String> colName = new TableColumn<Dentist, String>("Name");
		colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
		TableColumn<Dentist, String> colAddress = new TableColumn<Dentist, String>("Address");
		colAddress.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
		TableColumn<Dentist, String> colPhone = new TableColumn<Dentist, String>("Phone");
		colPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

		table.getColumns().add(colUser);
		table.getColumns().add(colName);
		table.getColumns().add(colAddress);
		table.getColumns().add(colPhone);

		root.setCenter(table);

		VBox buttons = new VBox(10);
		buttons.setPadding(new Insets(10));
		Button btnAdd = new Button("Add");
		btnAdd.setOnAction(event -> addUser());
		Button btnEdit = new Button("Edit");
		btnEdit.setOnAction(event -> editUser());
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(event -> deleteUser());
		Button btnReset = new Button("Reset \nPassword");
		btnReset.setOnAction(event -> resetUserPassword());

		buttons.getChildren().addAll(btnAdd, btnEdit, btnDelete, btnReset);

		root.setRight(buttons);

		Scene scene = new Scene(root, 480, 600);
		setScene(scene);
		setTitle("User Management");
		getIcons().add(new Image("/assets/smile.png"));
		setOnCloseRequest(event -> onClose(event));
	}

	private void onClose(WindowEvent event) {
		event.consume();
		parent.unloadUserManagement();
	}

	private void resetUserPassword() {
		Dentist d = table.getSelectionModel().getSelectedItem();
		if (d != null) {
			try {
				AppData.INSTANCE.getUserList().find(d.getUsername()).setPassword("1111");
				AlertDialog alert = new AlertDialog(AlertType.CONFIRMATION, "Password reset",
						"The password has been reset for user " + d.getUsername(),
						"New temporary password is \"1111\"");
				alert.showAndWait();
			} catch (PassException e) {
				ExceptionDialog exWin = new ExceptionDialog("Critical Error", "Unable to reset user password.", e);
				exWin.show();
			}

		}

	}

	private void deleteUser() {
		Dentist d = table.getSelectionModel().getSelectedItem();
		if (d != null) {
			if (AppState.INSTANCE.getCurrentUser().getName().equals(d.getName())) {
				AlertDialog alert = new AlertDialog(AlertType.WARNING, "Unable to delete", null,
						"Cannot delete current user.");
				alert.showAndWait();
			} else {
				AppData.INSTANCE.getUserList().remove(d);
				userList.remove(d);
				AppState.INSTANCE.setModified(true);
			}
		}
	}

	private void editUser() {
		Dentist d = table.getSelectionModel().getSelectedItem();
		if (d != null) {
			int row = userList.indexOf(d);

			Dentist toEdit = AppData.INSTANCE.getUserList().find(d.getUsername());
			UserDialog dialog = new UserDialog(toEdit);
			userList.set(row, dialog.getEdit());
		}
		AppState.INSTANCE.setModified(true);
	}

	private void addUser() {
		UserDialog dialog = new UserDialog(null);
		Dentist dentist = dialog.getEdit();
		userList.add(dentist);
		AppState.INSTANCE.setModified(true);
	}

}

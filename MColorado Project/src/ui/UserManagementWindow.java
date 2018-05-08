package ui;

import controller.AppData;
import controller.AppNavigation;
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
import persistence.DentistDAO;

/**
 * Creates a window to manage the users of the system
 * 
 * @author Manuel Colorado
 *
 */
public class UserManagementWindow extends Stage {

	private ObservableList<Dentist> userList;
	private TableView<Dentist> table;
	private AppMenu parent;

	/**
	 * Creates the window to manage the users of the system
	 * 
	 * @param parent
	 *            Parent window passed as parameter to call methods on the parent
	 */
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

	/**
	 * Captures the window close event to prevent the default close and use the
	 * custom one
	 * 
	 * @param event
	 *            the OnCloseRequest event to be handled
	 */
	private void onClose(WindowEvent event) {
		// Prevents the default close event
		event.consume();
		// Calls the custom close event
		parent.unloadUserManagement();
	}

	/**
	 * Resets the current user password by setting it to 11111111, this value will
	 * be captured in the login window forcing the user to reset his password
	 */
	private void resetUserPassword() {
		Dentist d = table.getSelectionModel().getSelectedItem();
		if (d != null) {
			try {
				AppData.INSTANCE.getUserList().find(d.getUsername()).setPassword("11111111");
				AppNavigation.updateDBelement(new DentistDAO(), d);
				// Warns the user the password has been reset and what's the temporary password
				AlertDialog alert = new AlertDialog(AlertType.CONFIRMATION, "Password reset",
						"The password has been reset for user " + d.getUsername(),
						"New temporary password is \"11111111\"");
				alert.showAndWait();
			} catch (PassException e) {
				ExceptionDialog exWin = new ExceptionDialog("Critical Error", "Unable to reset user password.", e);
				exWin.show();
			}
		}
	}

	/**
	 * Deletes the selected user from the system. Won't proceed if the user to be
	 * deleted is the current user logged in the system
	 */
	private void deleteUser() {
		Dentist d = table.getSelectionModel().getSelectedItem();
		if (d != null) {
			if (AppState.INSTANCE.getCurrentUser().getName().equals(d.getName())) {
				AlertDialog alert = new AlertDialog(AlertType.WARNING, "Unable to delete", null,
						"Cannot delete current user.");
				alert.showAndWait();
			} else {
				if (AppNavigation.deleteDBelement(new DentistDAO(), d)) {
					AppData.INSTANCE.getUserList().remove(d);
					userList.remove(d);
				}
			}
		}
	}

	/**
	 * Edits the selected user by calling a instance of the UserDialog object
	 */
	private void editUser() {
		Dentist d = table.getSelectionModel().getSelectedItem();
		if (d != null) {
			int row = userList.indexOf(d);

			Dentist toEdit = AppData.INSTANCE.getUserList().find(d.getUsername());
			UserDialog dialog = new UserDialog(toEdit);
			Dentist edited = dialog.getEdit();
			if (AppNavigation.updateDBelement(new DentistDAO(), edited)) {
				userList.set(row, edited);
			}
		}
	}

	/**
	 * Adds a new user to the system by calling an instance of the UserDialog object
	 */
	private void addUser() {
		UserDialog dialog = new UserDialog(null);
		Dentist dentist = dialog.getEdit();
		if (dentist != null) {
			if (AppNavigation.addDBelement(new DentistDAO(), dentist) > 0) {
				userList.add(dentist);
			}
		}
	}

}

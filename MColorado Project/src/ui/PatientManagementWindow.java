package ui;

import java.util.List;
import java.util.Optional;

import controller.AppController;
import controller.AppNavigation;
import controller.AppState;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Invoice;
import model.Patient;

/**
 * Creates a new window to manages the list of patients in the system
 * 
 * @author Manuel Colorado
 *
 */
public class PatientManagementWindow extends Stage {

	private ObservableList<Patient> patientList;
	private TableView<Patient> table;
	private AppMenu parent;
	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            a reference to the parent window so this pane can call methods of
	 *            the parent
	 */
	public PatientManagementWindow(AppMenu parent, AppController controller) {
		this.controller = controller;
		this.parent = parent;
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));

		table = new TableView<Patient>();
		patientList = FXCollections.observableArrayList(controller.getAllPatients());
		TableColumn<Patient, String> colName = new TableColumn<Patient, String>("Name");
		colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
		TableColumn<Patient, String> colAddress = new TableColumn<Patient, String>("Address");
		colAddress.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
		TableColumn<Patient, String> colPhone = new TableColumn<Patient, String>("Phone");
		colPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

		table.getColumns().add(colName);
		table.getColumns().add(colAddress);
		table.getColumns().add(colPhone);
		table.setItems(patientList);

		// Handle double click event on the table
		table.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				if (click.getClickCount() == 2) {
					openPatient();
				}
			}
		});

		root.setCenter(table);

		VBox buttons = new VBox(10);
		buttons.setPadding(new Insets(10));
		Button btnOpen = new Button("Open");
		btnOpen.setOnAction(event -> openPatient());
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(event -> deletePatient());

		buttons.getChildren().addAll(btnOpen, btnDelete);

		root.setRight(buttons);

		Scene scene = new Scene(root, 480, 600);
		setScene(scene);
		setTitle("User Management");
		getIcons().add(new Image("/assets/smile.png"));
		setOnCloseRequest(event -> onClose(event));
	}

	/**
	 * Prevents the onCloseRequest default event
	 * 
	 * @param event
	 *            onCloseRequest event to be hanlded
	 */
	private void onClose(WindowEvent event) {
		// Consumes the event to prevent the default
		event.consume();
		// Launches the custom unload process
		parent.unloadPatientWindow();
	}

	/**
	 * Loads the current selected patient into the main window
	 */
	private void openPatient() {
		Patient p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			Patient currentPatientDataFromDB = controller.getPatient(p.getPatientNo());
			AppNavigation.loadPatient(currentPatientDataFromDB);
		}
		parent.unloadPatientWindow();
	}

	/**
	 * Deletes selected item from the application state
	 */
	private void deletePatient() {
		Patient p = table.getSelectionModel().getSelectedItem();
		boolean deleteHappen = false;
		if (p != null) {
			// Deletes the Patient if no invoices are associated to it
			List<Invoice> invoiceList = controller.getInvoicesFromPatient(p);
			if (invoiceList.isEmpty()) {
				deleteHappen = deleteWorkflow(p);
			} else {
				boolean isPaid = true;
				for (Invoice i : invoiceList) {
					if (!i.isPaid() && isPaid) {
						isPaid = false;
					}
				}
				// Verifies if there's an invoice not yet paid and warns the user in case the
				// deletion should not proceed
				if (!isPaid) {
					Alert warning = new Alert(AlertType.INFORMATION);
					warning.setTitle("Invoices");
					warning.setHeaderText("Unpaid invoices");
					warning.setContentText(
							"There are unpaid invoices for this customer in the system, do you want to continue?");

					ButtonType btnOK = new ButtonType("Yes", ButtonData.YES);
					ButtonType btnCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
					warning.getButtonTypes().setAll(btnOK, btnCancel);

					Optional<ButtonType> result = warning.showAndWait();
					if (result.get() == btnOK) {
						deleteHappen = deleteWorkflow(p);
					}
				} else {
					deleteHappen = deleteWorkflow(p);
				}

			}

			// Clears the patient from the main window if the deletion process happened
			if (AppState.INSTANCE.getCurrentPatient().getPatientNo() == p.getPatientNo() && deleteHappen) {
				AppNavigation.clearPatient();
			}
		}
	}

	/**
	 * Captures if the deletion process has completed to refresh the window
	 * 
	 * @param p
	 *            a Patient object that will be deleted
	 * @return true if the patient was deleted
	 */
	private boolean deleteWorkflow(Patient p) {
		if (controller.deletePatient(p)) {
			patientList.remove(p);
			return true;
		}
		return false;		
	}
}
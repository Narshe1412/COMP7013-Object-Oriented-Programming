package ui;

import java.util.Optional;

import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Invoice;
import model.Patient;

public class PatientManagementWindow extends Stage {

	private ObservableList<Patient> patientList;
	private TableView<Patient> table;
	private AppMenu parent;

	public PatientManagementWindow(AppMenu parent) {
		this.parent = parent;
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));

		table = new TableView<Patient>();

		patientList = FXCollections.observableArrayList(AppData.INSTANCE.getPatientList());
		table.setItems(patientList);

		TableColumn<Patient, String> colName = new TableColumn<Patient, String>("Name");
		colName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
		TableColumn<Patient, String> colAddress = new TableColumn<Patient, String>("Address");
		colAddress.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));
		TableColumn<Patient, String> colPhone = new TableColumn<Patient, String>("Phone");
		colPhone.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

		table.getColumns().add(colName);
		table.getColumns().add(colAddress);
		table.getColumns().add(colPhone);
		
		table.setOnMouseClicked(event -> openPatient());

		root.setCenter(table);

		VBox buttons = new VBox(10);
		buttons.setPadding(new Insets(10));
		Button btnFilter = new Button("Filter");
		btnFilter.setOnAction(event -> filterPatient());
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

	private void filterPatient() {
		// TODO Auto-generated method stub
		
	}

	private void onClose(WindowEvent event) {
		event.consume();
		parent.unloadPatientWindow();
	}

	private void openPatient() {
		Patient p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			AppNavigation.loadPatient(AppData.INSTANCE.getPatientList().getById(p.getPatientNo()));
		}
		parent.unloadPatientWindow();
	}

	private void deletePatient() {
		Patient p = table.getSelectionModel().getSelectedItem();
		boolean deleteHappen = false;
		if (p != null) {
			if (p.getP_invoiceList().isEmpty()) {
				deleteHappen = deleteWorkflow(p);
			} else {
				boolean isPaid = true;
				for (Invoice i : p.getP_invoiceList()) {
					if (!i.isPaid() && isPaid) {
						isPaid = false;
					}
				}

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

			if (AppState.INSTANCE.getCurrentPatient().getPatientNo() == p.getPatientNo() && deleteHappen) {
				AppNavigation.clearPatient();
			}
		}
	}

	private boolean deleteWorkflow(Patient p) {
		AppData.INSTANCE.getPatientList().remove(p);
		patientList.remove(p);
		AppState.INSTANCE.setModified(true);
		return true;
	}

}

package ui;

import controller.AppController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Procedure;

/**
 * Creates a window to manage the Procedures in the system
 * 
 * @author Manuel Colorado
 *
 */
public class ProcedureManagementWindow extends Stage {

	private ObservableList<Procedure> procList;
	private TableView<Procedure> table;
	private AppMenu parent;
	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            The AppMenu parent object for reference to call methods of the
	 *            parent
	 */
	public ProcedureManagementWindow(AppMenu parent, AppController controller) {
		this.controller = controller;
		this.parent = parent;
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));

		table = new TableView<Procedure>();

		procList = FXCollections.observableArrayList(controller.getAllProcedures());
		// Remove disabled procedures from the final list
		for (Procedure proc : procList) {
			if (proc.isDisabled()) {
				procList.remove(proc);
			}
		}
		table.setItems(procList);

		TableColumn<Procedure, Integer> colID = new TableColumn<Procedure, Integer>("Id");
		colID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProcID()).asObject());
		TableColumn<Procedure, Double> colAmount = new TableColumn<Procedure, Double>("Amount $");
		colAmount.setCellValueFactory(data -> data.getValue().getProcCost().asObject());
		colAmount.setMinWidth(120);
		TableColumn<Procedure, String> colName = new TableColumn<Procedure, String>("Type of Procedure");
		colName.setCellValueFactory(data -> data.getValue().getProcName());
		colName.setMinWidth(200);

		table.getColumns().add(colID);
		table.getColumns().add(colName);
		table.getColumns().add(colAmount);

		root.setCenter(table);

		VBox buttons = new VBox(10);
		buttons.setPadding(new Insets(10));
		Button btnAdd = new Button("Add");
		btnAdd.setOnAction(event -> addProcedure());
		Button btnEdit = new Button("Edit");
		btnEdit.setOnAction(event -> editProcedure());
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(event -> deleteProcedure());
		buttons.getChildren().addAll(btnAdd, btnEdit, btnDelete);

		root.setRight(buttons);

		Scene scene = new Scene(root, 480, 600);
		setScene(scene);
		setTitle("Procedure Management");
		getIcons().add(new Image("/assets/smile.png"));

		setOnCloseRequest(event -> onClose(event));
	}

	/**
	 * Captures onClose event to call custom process instead of simply hiding the
	 * window
	 * 
	 * @param event
	 *            reference to the OnClose event from the window
	 */
	private void onClose(WindowEvent event) {
		// Prevent the standard closure of the window
		event.consume();

		// Call the custom closure of the window defined on the parent object
		parent.unloadProcedureManagement();
	}

	/**
	 * Deletes the selected Procedure from the application and table
	 */
	private void deleteProcedure() {
		Procedure p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			controller.deleteProcedure(p);
			procList.remove(p);
		}

	}

	/**
	 * Edit the selected procedure with the new information provided by a
	 * ProcedureDialog
	 */
	private void editProcedure() {
		Procedure p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			int row = procList.indexOf(p);
			Procedure toEdit = p;
			ProcedureDialog dialog = new ProcedureDialog(toEdit, controller);
			procList.set(row, dialog.getEdit());
		}
	}

	/**
	 * Add a new Procedure with the information provided by a ProcedureDialog
	 */
	private void addProcedure() {
		ProcedureDialog dialog = new ProcedureDialog(null, controller);
		Procedure newProc = dialog.getEdit();
		if (newProc != null) {
			procList.add(newProc);
		}
	}
}

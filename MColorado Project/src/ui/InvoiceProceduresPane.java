package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Invoice;
import model.Procedure;

/**
 * Creates a new pane to control the procedures related to an invoice
 * 
 * @author Manuel Colorado
 *
 */
public class InvoiceProceduresPane extends Pane {

	@SuppressWarnings("unused")
	private Button btnAddTreatment, btnRemoveTreatment;
	private TableView<Procedure> table;
	private ObservableList<Procedure> procList;
	private Invoice invoice;
	private InvoiceTitlePane title;
	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param inv
	 *            the invoice that will be displayed
	 * @param title
	 *            the parent passed by parameter so it can be called the methods to
	 *            refresh the pane
	 */
	public InvoiceProceduresPane(Invoice inv, InvoiceTitlePane title, AppController controller) {
		this.controller = controller;
		this.title = title;
		this.invoice = inv;
		HBox root = new HBox(10);
		root.setPadding(new Insets(10, 10, 10, 10));

		table = new TableView<Procedure>();
		procList = FXCollections.observableArrayList(controller.getProceduresByInvoice(inv));
		table.setItems(procList);

		TableColumn<Procedure, Double> colAmount = new TableColumn<Procedure, Double>("Amount");
		colAmount.setCellValueFactory(data -> data.getValue().getProcCost().asObject());
		colAmount.setMinWidth(120);
		TableColumn<Procedure, String> colName = new TableColumn<Procedure, String>("Type of Procedure");
		colName.setCellValueFactory(data -> data.getValue().getProcName());
		colName.setMinWidth(200);

		table.getColumns().add(colName);
		table.getColumns().add(colAmount);

		root.getChildren().add(table);
		HBox.setHgrow(table, Priority.ALWAYS);

		VBox buttons = new VBox(10);
		Button btnAddTreatment = new Button("Add Procedure");
		btnAddTreatment.setMaxWidth(Double.MAX_VALUE);
		btnAddTreatment.setOnAction(event -> onClickBtnAddProcedure());

		Button btnRemoveTreatment = new Button("Remove Procedure");
		btnRemoveTreatment.setMaxWidth(Double.MAX_VALUE);
		btnRemoveTreatment.setOnAction(event -> deleteProcedure());

		buttons.getChildren().add(btnAddTreatment);
		buttons.getChildren().add(btnRemoveTreatment);

		root.getChildren().add(buttons);

		table.setMaxHeight(160);
		table.setMinWidth(350);
		table.setMaxWidth(350);
		getChildren().add(root);
	}

	/**
	 * Adds a new procedure to the invoice
	 * 
	 * @param p
	 *            a Procedure object that will be added to the system
	 */
	public void addProcedure(Procedure p) {
		procList.add(p); 
		controller.addProcedureToInvoice(p, invoice);
		title.refresh();
	}

	/**
	 * Delete the selected procedure from the invoice
	 */
	public void deleteProcedure() {
		Procedure p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			procList.remove(p); 
			controller.deleteProcedureFromInvoice(p, invoice);
		}
		title.refresh();
	}

	/**
	 * Creates a new dialog with the list of procedures to be added to the invoice
	 */
	public void onClickBtnAddProcedure() {
		List<String> choices = new ArrayList<>();
		for (Procedure proc : controller.getAllProcedures()) {
			// Removes the disabled procedures from the list
			if (!proc.isDisabled()) {
				choices.add(proc.getProcName().get());
			}
		}
		if (choices.size() > 0) {
			ChoiceDialog<String> dialog = new ChoiceDialog<String>(choices.get(0), choices);

			dialog.setTitle("Add");
			dialog.setHeaderText("Dental Procedures");
			dialog.setContentText("Add a new treatment for this patient: ");
			Stage icon = (Stage) dialog.getDialogPane().getScene().getWindow();
			icon.getIcons().add(new Image(this.getClass().getResource("/assets/smile.png").toString()));

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(proc -> {
				addProcedure(controller.getProcedureByName(proc));
			});

		} else {
			AlertDialog alert = new AlertDialog(AlertType.WARNING, "No Procedures", null,
					"There are no procedures registered in the system.");
			alert.showAndWait();
		}
	}
}

package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Invoice;
import model.Payment;
import model.Procedure;

public class InvoiceProceduresPane extends Pane {
	
	private Button btnAddTreatment;
	private Button btnRemoveTreatment;
	private TableView<Procedure> table;
	private ObservableList<Procedure> procList;

	public InvoiceProceduresPane(Invoice inv) {
		HBox root = new HBox(10);
		root.setPadding(new Insets(10, 10, 10, 10));
		
		table = new TableView<Procedure>();
		procList = FXCollections.observableArrayList(inv.getIn_procList());
		table.setItems(procList);

		TableColumn<Procedure, Double> colAmount = new TableColumn<Procedure, Double>("Amount (€)");
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
		Button btnRemoveTreatment = new Button("Remove Procedure");
		btnRemoveTreatment.setMaxWidth(Double.MAX_VALUE);

		buttons.getChildren().add(btnAddTreatment);
		buttons.getChildren().add(btnRemoveTreatment);

		root.getChildren().add(buttons);

		table.setMaxHeight(160);
		table.setMinWidth(350);
		table.setMaxWidth(350);
		getChildren().add(root);
		
		
	}

}


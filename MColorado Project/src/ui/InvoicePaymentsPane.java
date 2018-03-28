package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Invoice;
import model.Payment;

public class InvoicePaymentsPane extends Pane {

	private Button btnAddPayment;
	private Button btnRemovePayment;
	private TableView<Payment> table;
	private ObservableList<Payment> paymentList;

	public InvoicePaymentsPane(Invoice inv) {
		HBox root = new HBox(10);
		root.setPadding(new Insets(10, 10, 10, 10));

		table = new TableView<Payment>();
		paymentList = FXCollections.observableArrayList(inv.getIn_paymentList());
		table.setItems(paymentList);

		TableColumn<Payment, Double> colAmount = new TableColumn<Payment, Double>("Amount");
		colAmount.setCellValueFactory(data -> data.getValue().getPaymentAmt().asObject());
		colAmount.setMinWidth(120);
		TableColumn<Payment, String> colDate = new TableColumn<Payment, String>("Payment Date");
		colDate.setCellValueFactory(data -> data.getValue().getPaymentStringDate());
		colDate.setMinWidth(200);

		table.getColumns().add(colDate);
		table.getColumns().add(colAmount);
		

		root.getChildren().add(table);

		VBox buttons = new VBox(10);
		Button btnAddPayment = new Button("Pay");
		btnAddPayment.setMaxWidth(Double.MAX_VALUE);
		Button btnRemovePayment = new Button("Delete");
		btnRemovePayment.setMaxWidth(Double.MAX_VALUE);

		
		buttons.getChildren().add(btnAddPayment);
		buttons.getChildren().add(btnRemovePayment);

		root.getChildren().add(buttons);
		table.setMaxHeight(160);
		table.setMinWidth(350);
		table.setMaxWidth(350);
		getChildren().add(root);
	}

}

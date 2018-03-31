package ui;

import java.util.Optional;

import controller.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
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
	private InvoiceTitlePane title;
	private Invoice invoice;

	public InvoicePaymentsPane(Invoice inv, InvoiceTitlePane title) {
		this.invoice = inv;
		this.title = title;

		HBox root = new HBox(10);
		root.setPadding(new Insets(10, 10, 10, 10));

		table = new TableView<Payment>();
		paymentList = FXCollections.observableArrayList(invoice.getIn_paymentList());

		TableColumn<Payment, Double> colAmount = new TableColumn<Payment, Double>("Amount");
		colAmount.setCellValueFactory(data -> data.getValue().getPaymentAmt().asObject());
		colAmount.setMinWidth(120);
		TableColumn<Payment, String> colDate = new TableColumn<Payment, String>("Payment Date");
		colDate.setCellValueFactory(data -> data.getValue().getPaymentStringDate());
		colDate.setMinWidth(200);

		table.getColumns().add(colDate);
		table.getColumns().add(colAmount);

		table.setItems(paymentList);

		root.getChildren().add(table);

		VBox buttons = new VBox(10);
		Button btnAddPayment = new Button("Pay");
		btnAddPayment.setMaxWidth(Double.MAX_VALUE);
		btnAddPayment.setOnAction(event -> {
			TextInputDialog dialog = new TextInputDialog("0");
			dialog.setTitle("Add");
			dialog.setHeaderText("Payments");
			dialog.setContentText("How much the customer has paid? ");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(amount -> {
				pay(new Payment(Double.parseDouble(result.get().trim())));
			});

		});
		Button btnRemovePayment = new Button("Delete");
		btnRemovePayment.setMaxWidth(Double.MAX_VALUE);
		btnRemovePayment.setOnAction(event -> deletePayment());

		buttons.getChildren().add(btnAddPayment);
		buttons.getChildren().add(btnRemovePayment);

		root.getChildren().add(buttons);
		table.setMaxHeight(160);
		table.setMinWidth(350);
		table.setMaxWidth(350);
		getChildren().add(root);
	}

	public void pay(Payment p) {
		paymentList.add(p); // TODO review binding
		invoice.addPayment(p);
		System.out.println(paymentList);
		System.out.println(invoice.getIn_paymentList());
		title.refresh();
	}

	public void deletePayment() {
		Payment p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			int row = paymentList.indexOf(p);
			paymentList.remove(p); // TODO review binding
			invoice.removePayment(p);
			System.out.println(paymentList);
			System.out.println(invoice.getIn_paymentList());
		}
		title.refresh();
	}

}

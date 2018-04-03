package ui;

import java.util.Optional;

import controller.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
			if (invoice.isPaid()) {
				AlertDialog alert = new AlertDialog(AlertType.INFORMATION, "Invoice paid", null, "This invoice has already been paid. You cannot add more payments against it.");
				alert.showAndWait();
			} else {
				double remaining = invoice.calculateInvoiceAmt();
				TextInputDialog dialog = new TextInputDialog(remaining + "");
				dialog.setTitle("Add");
				dialog.setHeaderText("Payments");
				dialog.setContentText("How much the customer has paid? ");
				Stage icon = (Stage) dialog.getDialogPane().getScene().getWindow();
				icon.getIcons().add(new Image(this.getClass().getResource("/assets/smile.png").toString()));

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(e -> {
					double amount = Double.parseDouble(result.get().trim());
					if (amount > 0) {
						if(amount <= remaining) {
							pay(new Payment(amount));
						} else {
							AlertDialog alert = new AlertDialog(AlertType.INFORMATION, "Amount too big", null, "The amount introduced surpasses the remaining amount to be paid for this invoice.");
							alert.showAndWait();
						}
							
					}
				});
			}
		});
		Button btnRemovePayment = new Button("Delete");
		btnRemovePayment.setMaxWidth(Double.MAX_VALUE);
		btnRemovePayment.setOnAction(event -> {
			deletePayment();	
			//btnAddPayment.setDisable(invoice.isPaid());
			});

		buttons.getChildren().add(btnAddPayment);
		buttons.getChildren().add(btnRemovePayment);

		root.getChildren().add(buttons);
		table.setMaxHeight(160);
		table.setMinWidth(350);
		table.setMaxWidth(350);
		getChildren().add(root);
	}

	public void pay(Payment p) {
		// TODO review binding
		AppData.INSTANCE.getPaymentList().add(p);
		paymentList.add(p); 
		invoice.addPayment(p);
		invoice.calculateInvoicePaid();
		title.refresh();
	}

	public void deletePayment() {
		Payment p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			int row = paymentList.indexOf(p);
			AppData.INSTANCE.getPaymentList().remove(p);
			paymentList.remove(p); // TODO review binding
			invoice.removePayment(p);
		}
		title.refresh();
	}
}

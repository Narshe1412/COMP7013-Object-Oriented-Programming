package ui;

import java.time.LocalDate;
import java.util.Optional;

import controller.AppController;
import controller.AppData;
import controller.AppState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Invoice;
import model.Payment;

/**
 * Creates a pane that controls the payments referent to a specific invoice
 * 
 * @author Manuel Colorado
 *
 */
public class InvoicePaymentsPane extends Pane {

	@SuppressWarnings("unused")
	private Button btnAddPayment, btnRemovePayment;
	private TableView<Payment> table;
	private ObservableList<Payment> paymentList;
	private InvoiceTitlePane title;
	private Invoice invoice;
	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param inv
	 *            Invoice object passed by parameter with all the details that will
	 *            be displayed on screen
	 * @param title
	 *            The parent pane to provide updates and call refresh method
	 */
	public InvoicePaymentsPane(Invoice inv, InvoiceTitlePane title, AppController controller) {
		this.controller = controller;
		this.invoice = inv;
		this.title = title;

		HBox root = new HBox(10);
		root.setPadding(new Insets(10, 10, 10, 10));

		table = new TableView<Payment>();
		paymentList = FXCollections.observableArrayList(controller.getPaymentFromInvoice(inv));

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
			// Prevent adding more payments if the invoice is paid
			if (invoice.isPaid()) {
				AlertDialog alert = new AlertDialog(AlertType.INFORMATION, "Invoice paid", null,
						"This invoice has already been paid. You cannot add more payments against it.");
				alert.showAndWait();
			} else {
				// Call the dialog to add new payments
				double remaining = invoice.calculateInvoiceAmt();
				showPaymentDialog(remaining);
			}
		});
		Button btnRemovePayment = new Button("Delete");
		btnRemovePayment.setMaxWidth(Double.MAX_VALUE);
		btnRemovePayment.setOnAction(event -> {
			deletePayment();
			// btnAddPayment.setDisable(invoice.isPaid());
		});

		buttons.getChildren().add(btnAddPayment);
		buttons.getChildren().add(btnRemovePayment);

		root.getChildren().add(buttons);
		table.setMaxHeight(160);
		table.setMinWidth(350);
		table.setMaxWidth(350);
		getChildren().add(root);
	}

	/** Class that will be used as a return value for the Dialog call */
	private class PaymentData {
		String value;
		LocalDate date;

		public PaymentData(String value, LocalDate date) {
			this.value = value;
			this.date = date;
		}
	}

	/**
	 * Obtains a payment value using the dialog to add a new payment
	 * 
	 * @param remaining
	 *            a value containing the remaining amount that needs to be paid
	 */
	private void showPaymentDialog(double remaining) {
		Dialog<PaymentData> payDialog = new Dialog<>();
		payDialog.setTitle("Add Payment");
		payDialog.setHeaderText("Please specify payment amount and date");
		DialogPane dialogPane = payDialog.getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		TextField textField = new TextField(remaining + "");
		DatePicker datePicker = new DatePicker(LocalDate.now());

		dialogPane.setContent(new VBox(10, textField, datePicker));
		Platform.runLater(textField::requestFocus);
		payDialog.setResultConverter((ButtonType button) -> {
			if (button == ButtonType.OK) {
				return new PaymentData(textField.getText(), datePicker.getValue());
			}
			return null;
		});
		Optional<PaymentData> result = payDialog.showAndWait();
		result.ifPresent((PaymentData results) -> {
			double amount = Double.parseDouble(results.value.trim());
			if (amount > 0) {
				if (amount <= remaining) {
					pay(new Payment(amount, results.date));
				} else {
					AlertDialog alert = new AlertDialog(AlertType.INFORMATION, "Amount too big", null,
							"The amount introduced surpasses the remaining amount to be paid for this invoice.");
					alert.showAndWait();
				}
			}
		});
	}

	/**
	 * Adds a new payment to the invoice
	 * 
	 * @param p
	 *            A new payment to add to the system
	 */
	public void pay(Payment p) {
		controller.addPaymentToInvoice(p, invoice);
		paymentList.add(p);
		title.refresh();
	}

	/**
	 * Deletes the selected payment from the system
	 */
	public void deletePayment() {
		Payment p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			controller.deletePaymentFromInvoice(p, invoice);
			paymentList.remove(p); // TODO review binding
		}
		title.refresh();
	}
}

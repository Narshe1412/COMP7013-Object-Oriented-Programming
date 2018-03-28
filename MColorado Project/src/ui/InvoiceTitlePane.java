package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Invoice;

public class InvoiceTitlePane extends StackPane {
	
	private Label lblDate;
	private TextField txtDate;
	private Label lblTotalInvoice;
	private TextField txtTotalInvoice;
	private Label lblPaidInvoice;
	private TextField txtPaidInvoice;
	private Label lblRemainInvoice;
	private TextField txtRemainInvoice;
	private Invoice invoice;

	public InvoiceTitlePane(Invoice inv) {
		this.invoice = inv;
		
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setPadding(new Insets(10, 10, 10, 10));
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(25);
		root.getColumnConstraints().add(column);
		root.getColumnConstraints().add(column);
		root.getColumnConstraints().add(column);
		root.getColumnConstraints().add(column);

		lblDate = new Label("Invoice Date: ");
		txtDate = new TextField();
		txtDate.setDisable(true);
		txtDate.setStyle("-fx-opacity: 1.0;");
		lblTotalInvoice = new Label("Total: ");
		txtTotalInvoice = new TextField();
		txtTotalInvoice.setStyle("-fx-opacity: 1.0;");
		txtTotalInvoice.setDisable(true);
		txtTotalInvoice.setAlignment(Pos.CENTER_RIGHT);
		lblPaidInvoice = new Label("Amount paid: ");
		txtPaidInvoice = new TextField();
		txtPaidInvoice.setAlignment(Pos.CENTER_RIGHT);
		txtPaidInvoice.setDisable(true);
		txtPaidInvoice.setStyle("-fx-opacity: 1.0;");
		lblRemainInvoice = new Label("Amount remaining: ");
		txtRemainInvoice = new TextField();
		txtRemainInvoice.setAlignment(Pos.CENTER_RIGHT);
		txtRemainInvoice.setDisable(true);
		txtRemainInvoice.setStyle("-fx-opacity: 1.0;");

		root.add(lblDate, 0, 0);
		root.add(txtDate, 0, 1);
		root.add(lblTotalInvoice, 1, 0);
		root.add(txtTotalInvoice, 1, 1);
		root.add(lblPaidInvoice, 2, 0);
		root.add(txtPaidInvoice, 2, 1);
		root.add(lblRemainInvoice, 3, 0);
		root.add(txtRemainInvoice, 3, 1);
		refresh();
		
		getChildren().add(root);
	}
	
	public void refresh() {
		txtDate.setText(invoice.getStringDate());
		txtTotalInvoice.setText(invoice.getInvoiceAmt() + " €");
		txtPaidInvoice.setText(invoice.calculateInvoicePaid() + " €");
		txtRemainInvoice.setText(invoice.calculateInvoiceAmt() + " €");
	}


}

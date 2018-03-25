package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class InvoiceControlsPane extends Pane {

	private Button btnNew;
	private Button btnOpen;
	private Label lblAmountDue;
	private Text txtAmountDue;
	private Label lblAmountTotal;
	private Text txtAmountTotal;
	private Button btnPay;

	public InvoiceControlsPane() {

		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25, 25, 25, 25));
		btnNew = new Button("New\nInvoice");
		root.add(btnNew, 0,1);
		btnOpen = new Button("Open\nInvoice");
		root.add(btnOpen, 2, 1);
		lblAmountTotal = new Label("Total amount:");
		root.add(lblAmountTotal, 0, 2, 2, 1);
		txtAmountTotal = new Text("0.00€");
		root.add(txtAmountTotal, 2, 2);
		lblAmountDue = new Label("Amount due:");
		root.add(lblAmountDue, 0, 3, 2, 1);
		txtAmountDue = new Text("0.00€");
		root.add(txtAmountDue, 2, 3);
		btnPay = new Button("PAY");
		root.add(btnPay, 1, 5, 1, 1);
		
		getChildren().add(root);
	}

}

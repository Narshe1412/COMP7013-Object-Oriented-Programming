package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Invoice;

public class InvoiceProceduresPane extends Pane {
	
	private Invoice invoice;

	public InvoiceProceduresPane(Invoice inv) {
		this.invoice = inv;
		HBox root = new HBox();
		root.getChildren().add(new Label("Procedures pane"));
		getChildren().add(root);
	}

}

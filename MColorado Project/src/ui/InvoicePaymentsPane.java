package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Invoice;

public class InvoicePaymentsPane extends Pane {

	public InvoicePaymentsPane(Invoice inv) {
		HBox root = new HBox();
		root.getChildren().add(new Label("Payments pane"));
		getChildren().add(root);
	}

}

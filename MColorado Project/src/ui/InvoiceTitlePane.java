package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Invoice;

public class InvoiceTitlePane extends Pane {

	public InvoiceTitlePane(Invoice inv) {
		HBox root = new HBox();
		root.getChildren().add(new Label("Invoice title"));
		getChildren().add(root);
	}


}

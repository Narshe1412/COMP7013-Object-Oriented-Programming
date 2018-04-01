package ui;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

class TabMain extends Tab {
	
	private HomeWindow parent;
	private PatientDetailsPane details;
	private InvoiceControlsPane invoices;
	
	public TabMain(HomeWindow parent) {
		this.parent = parent;
		setText("Patient Details");
		SplitPane root = new SplitPane();

		details = new PatientDetailsPane();
		invoices = new InvoiceControlsPane(parent);
		
		root.getItems().addAll(details, invoices);
		setContent(root);
		setClosable(false);
	}
	
	public void refreshUI() {
		details.refresh();
		invoices.refresh();
	}
	
}

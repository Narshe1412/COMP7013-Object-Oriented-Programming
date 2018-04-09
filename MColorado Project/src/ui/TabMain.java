package ui;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

class TabMain extends Tab implements ReloadableNode{
	
	@SuppressWarnings("unused")
	private HomeWindow parent;
	private PatientDetailsPane details;
	private InvoiceControlsPane invoices;
	
	public TabMain(HomeWindow parent) {
		this.parent = parent;
		setText("Patient Details");
		SplitPane root = new SplitPane();

		details = new PatientDetailsPane(parent);
		invoices = new InvoiceControlsPane(parent);
		
		root.getItems().addAll(details, invoices);
		setContent(root);
		setClosable(false);
	}
	
	public void refreshUI() {
		details.refreshUI();
		invoices.refresh();
	}
	
}

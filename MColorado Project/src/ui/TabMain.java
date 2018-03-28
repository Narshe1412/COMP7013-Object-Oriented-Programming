package ui;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

class TabMain extends Tab {
	
	public TabMain() {
		createContent();
	}
	
	private void createContent() {
		setText("Patient Details");
		SplitPane root = new SplitPane();
		root.getItems().addAll(new PatientDetailsPane(), new InvoiceControlsPane());
		setContent(root);
	}
	
}

package ui;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

class TabMain extends Tab {
	
	private HomeWindow parent;
	
	public TabMain(HomeWindow parent) {
		this.parent = parent;
		createContent();
	}
	
	private void createContent() {
		setText("Patient Details");
		SplitPane root = new SplitPane();
		root.getItems().addAll(new PatientDetailsPane(), new InvoiceControlsPane(parent));
		setContent(root);
	}
	
}

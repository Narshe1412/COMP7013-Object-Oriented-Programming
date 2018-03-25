package ui;

import controller.AppState;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import model.Patient;

class TabMain extends Tab {
	
	public TabMain() {
		createContent();
	}
	
	private void createContent() {
		setText("Patient Details");
		Patient tabPatient = AppState.INSTANCE.getCurrentPatient();
		SplitPane root = new SplitPane();

		root.getItems().addAll(new PatientDetailsPane(), new InvoiceControlsPane());
		//setContent(new Text("This is the main tab"));
		setContent(root);
		//setContent(new InvoiceControlsPane());
	}
	
}

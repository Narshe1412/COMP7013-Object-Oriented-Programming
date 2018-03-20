package ui;

import controller.AppData;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import model.Patient;

class TabMain extends Tab {
	
	public TabMain() {
		createContent();
	}
	
	private void createContent() {
		setText("Patient Details");
		Patient tabPatient = AppData.INSTANCE.getCurrentPatient();
		setContent(new Text("This is the main tab"));
	}
	
}

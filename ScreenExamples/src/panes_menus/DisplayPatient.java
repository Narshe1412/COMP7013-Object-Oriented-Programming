package panes_menus;

import java.util.ArrayList;
import java.util.List;

import classes.Patient;
import classes.PatientHistory;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class DisplayPatient extends BorderPane {
	private Patient selectedPatient;
	Label details;

	public DisplayPatient(int id) {
		selectedPatient = Controller.getInstance().getPatient(id);

		this.setCenter(new DisplayHistory());
		this.setTop(new DisplayDetails());

	}

	private class DisplayDetails extends StackPane {
		private Label details;

		private DisplayDetails() {
			details = new Label("");
			details.setText("Name: " + selectedPatient.getName() + "\nAddress: " + selectedPatient.getAddress()
					+ "\nAge: " + selectedPatient.getAge());
		}

	}

	// display the patient history in 2 sections
	private class DisplayHistory extends SplitPane {
		private ListView list;
		private TextArea textArea;

		@SuppressWarnings("unchecked")
		private DisplayHistory() {
			BorderPane sp2 = new BorderPane();
			Button b = new Button("List History");
			b.setOnAction(e -> loadHistory());// lambda

			list = new ListView<>();
			list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					//get the patient selected and open a details window
					
					int index = newValue.indexOf("-");
					int id = Integer.parseInt(newValue.substring(0, index));
					for (PatientHistory p : selectedPatient.getPatientHistory()) {
						if (p.getID() == id) {
							String text = "Treatment Date: " + p.getTreatmentDate() + "\n" + "Procedure:"
									+ p.getProcedure().getName() + "\n" + "Price:€" + p.getProcedure().getPrice()
									+ "\nPaid:" + p.getPaid();
							textArea.setText(text);
						}
					}
				}
			});

			sp2.setTop(b);
	        sp2.setCenter(list);
	        
			this.getItems().addAll(sp2, textArea);

		}

		private void loadHistory() {
			List<String> patiuentHistory = new ArrayList<String>();

			for (PatientHistory p : selectedPatient.getPatientHistory()) {
				patiuentHistory.add(p.getID() + "-- " + p.getTreatmentDate());
			}

			ObservableList<String> myList = FXCollections.observableArrayList(patiuentHistory);
			list.setItems(myList);
		}
	}
}

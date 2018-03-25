package panes_menus;

import java.util.ArrayList;
import java.util.List;

import Scenes.MyScene;
import classes.Patient;
import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MySplitPane extends SplitPane {
	private ListView<HBoxCell> list; // this is like my home page

	public MySplitPane() {
		MyScene scene = new MyScene(this, Color.BLUE);
		StackPane sp1 = new StackPane();
		HBox h1 = new HBox();
		Button menuButton = new Button("Menu Window");
		menuButton.setOnAction(actionEvent -> new MyMenu()); // or get a
																// singleton
																// instance of
																// my tab pane?
		Button tabButton = new Button("Tab Window");
		tabButton.setOnAction(actionEvent -> new MyTabPane()); // or get a
																// singleton
																// instance of
																// my tab pane?

		h1.getChildren().addAll(tabButton, menuButton);
		sp1.getChildren().add(h1);

		BorderPane sp2 = new BorderPane();
		Button b = new Button("List Patients");

		b.setOnAction(e -> loadPatients());// lambda
		list = new ListView<>();
		sp2.setTop(b);
		sp2.setCenter(list);

		this.getItems().addAll(sp1, sp2);

		Controller.getInstance().setScene(scene);
	}

	private void loadPatients() {// this is getting my list of patients from the
									// controller
		List<HBoxCell> patientNames = new ArrayList<>();

		for (Patient p : Controller.getInstance().getPatients()) {
			patientNames.add(new HBoxCell(p.getID() + "-- " + p.getName() + " / " + p.getAge() + " / " + p.getAddress(),
					"View", p.getID()));
		}

		ObservableList<HBoxCell> myList = FXCollections.observableList(patientNames);
		list.setItems(myList);
	}

	public class HBoxCell extends HBox {
		Label label = new Label();
		Button button = new Button();

		HBoxCell(String labelText, String buttonText, int id) {
             super();

             label.setText(labelText);
             label.setMaxWidth(Double.MAX_VALUE);
             HBox.setHgrow(label, Priority.ALWAYS);

             button.setText(buttonText);
             button.setOnAction(e -> showPatient(id));
             this.getChildren().addAll(label, button);
        }
	}

	private void showPatient(int id){
		MyScene scene = new MyScene(new DisplayPatient(id),Color.YELLOW);
		Controller.getInstance().showSecondStage(scene);
	}
}

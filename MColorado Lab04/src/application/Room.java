package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Room extends Pane {
	private final ToggleGroup togGrp;
	private RadioButton radioOn;
	private RadioButton radioOff;

	public Room(String title) {

		VBox root = new VBox();
		root.getChildren().add(new Label(title));
		
		BorderPane alarmGroup = new BorderPane();
		Insets padds = new Insets(10);
		alarmGroup.setPadding(padds);

		VBox radioGroup = new VBox(5);
		radioGroup.setPadding(padds);
		togGrp = new ToggleGroup();
		radioOn = new RadioButton("On");
		radioOn.setToggleGroup(togGrp);
		radioOff = new RadioButton("Off");
		radioOff.setToggleGroup(togGrp);
		radioOff.setSelected(true);
		radioOff.setOnAction(event -> setStyle(null));
		radioGroup.getChildren().addAll(radioOn, radioOff);
		alarmGroup.setLeft(radioGroup);
		alarmGroup.visibleProperty().bind(State.INSTANCE.getAlarmEnabled());

		BorderPane btnPane = new BorderPane();
		btnPane.setPadding(padds);
		Button btnIntruder = new Button(" \nIntruder\n ");
		btnIntruder.setOnAction(event -> checkIntruder());
		btnPane.setCenter(btnIntruder);
		alarmGroup.setRight(btnPane);

		root.getChildren().add(alarmGroup);
		getChildren().add(root);
		
	}

	private void checkIntruder() {
		if(togGrp.getSelectedToggle().equals(radioOn)) {
			setStyle("-fx-background-color: red");
		};
	}
}

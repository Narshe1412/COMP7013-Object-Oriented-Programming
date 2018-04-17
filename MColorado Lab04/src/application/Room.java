package application;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Room extends Pane {
	private final ToggleGroup togGrp;
	private RadioButton radioOn;
	private RadioButton radioOff;
	private MediaPlayer mediaPlayer;

	public Room(String title, String borderCss) {
		String musicFile = "src/alarm.mp3"; 
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		
		VBox root = new VBox();
		root.setPadding(new Insets(10));
		setStyle(borderCss);
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
		radioOff.setOnAction(event -> disableAlarm());
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

	private void disableAlarm() {
		setStyle(null);
		mediaPlayer.stop();
	}

	private void checkIntruder() {
		if(togGrp.getSelectedToggle().equals(radioOn)) {
			setStyle("-fx-background-color: red");
			mediaPlayer.play();
		};
	}
}

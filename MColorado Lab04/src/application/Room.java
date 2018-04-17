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
/**
 * Creates a room object with inherited behaviour from Pane
 * @author Manuel Colorado
 *
 */
public class Room extends Pane {
	private final ToggleGroup togGrp;
	private RadioButton radioOn;
	private RadioButton radioOff;
	private MediaPlayer mediaPlayer;

	/**
	 * Constructor
	 * @param title String that will denote the title of the room
	 * @param borderCss String that will configure the CSS of the border of the room
	 */
	public Room(String title, String borderCss) {
		// Configure music file that will be used as alarm
		String musicFile = "src/alarm.mp3"; 
		Media sound = new Media(new File(musicFile).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		
		// Creates the content of the room element
		VBox root = new VBox();
		root.setPadding(new Insets(10));
		setStyle(borderCss);
		root.getChildren().add(new Label(title));
		
		BorderPane alarmGroup = new BorderPane();
		Insets padds = new Insets(10);
		alarmGroup.setPadding(padds);

		// Radio field
		VBox radioGroup = new VBox(5);
		radioGroup.setPadding(padds);
		togGrp = new ToggleGroup();
		radioOn = new RadioButton("On");
		radioOn.setToggleGroup(togGrp);
		radioOff = new RadioButton("Off");
		radioOff.setToggleGroup(togGrp);
		radioOff.setSelected(true);
		// If the OFF button is clicked, cancel all the alarm settings
		radioOff.setOnAction(event -> disableAlarm());
		radioGroup.getChildren().addAll(radioOn, radioOff);
		alarmGroup.setLeft(radioGroup);
		// Only show the alarm controls if the Alarm State is enabled
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

	/**
	 * Return all controls to default settings
	 */
	private void disableAlarm() {
		setStyle(null);
		mediaPlayer.stop();
	}

	/**
	 * Check if the alarm is enabled. If so, plays the alarm and marks the color as appropriate
	 */
	private void checkIntruder() {
		if(togGrp.getSelectedToggle().equals(radioOn)) {
			setStyle("-fx-background-color: red");
			mediaPlayer.play();
		};
	}
}

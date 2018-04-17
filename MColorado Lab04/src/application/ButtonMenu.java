package application;

import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Creates a menu with two buttons to control if the alarm system is enabled or
 * not
 * 
 * @author Manuel Colorado
 *
 */
public class ButtonMenu extends Pane {

	private Button btnOnoff;
	private Button btnExit;
	private BooleanProperty alarmEnabled;

	/**
	 * Constructor
	 */
	public ButtonMenu() {
		// Obtain singleton value
		alarmEnabled = State.INSTANCE.getAlarmEnabled();
		HBox root = new HBox();

		btnOnoff = new Button("On / Off");
		// Swap from on to off and vice versa
		btnOnoff.setOnAction(event -> toggleAlarm());
		// Adds a listener over the alarmenabled property
		// It will mark the text as black if disabled, red as enabled
		alarmEnabled.addListener((observable, oldval, newval) -> {
			if (newval) {
				btnOnoff.setStyle("-fx-text-fill: red;");
			} else {
				btnOnoff.setStyle("-fx-text-fill: black;");
			}
		});

		// Close the application
		btnExit = new Button("Exit");
		btnExit.setOnAction(event -> doExit(event));
		
		root.getChildren().addAll(btnOnoff, new Label("              "), btnExit);
		root.setAlignment(Pos.CENTER);
		getChildren().add(root);
	}

	/**
	 * Captures the exit request to warn the user before the event is closed
	 * 
	 * @param event
	 *            captures the event of clicking the window
	 */
	private void doExit(ActionEvent event) {
		event.consume();
		// Creates the alert
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close confirmation");
		alert.setContentText("Do you want to exit?");

		Optional<ButtonType> result = alert.showAndWait();
		// If OK button is clicked, close the app. OTherwise, don't
		if (result.get() == ButtonType.OK) {
			Platform.exit();
		}
	}

	/**
	 * Swaps the value of the alarm property
	 */
	private void toggleAlarm() {
		setAlarmEnabled(!getAlarmEnabled().get());
	}

	/**
	 * Getter for AlarmEnabled observable property
	 * 
	 * @return the value of the obvservable boolean property alarmEnabled
	 */
	public BooleanProperty getAlarmEnabled() {
		return alarmEnabled;
	}

	/**
	 * Setter for AlarmEnabled property
	 * 
	 * @param alarmEnabled
	 *            boolean value that will be changed for the booleanproperty
	 *            alarmEnabled
	 */
	public void setAlarmEnabled(boolean alarmEnabled) {
		this.alarmEnabled.setValue(alarmEnabled);
	}
}

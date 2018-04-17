package application;

import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ButtonMenu extends Pane {

	private Button btnOnoff;
	private Button btnExit;
	private BooleanProperty alarmEnabled;

	public ButtonMenu() {
		
		alarmEnabled = State.INSTANCE.getAlarmEnabled();
		HBox root = new HBox();

		btnOnoff = new Button("On / Off");
		btnOnoff.setOnAction(event -> toggleAlarm());
		alarmEnabled.addListener((observable, oldval, newval) -> {
			if (newval) {
				btnOnoff.setStyle("-fx-text-fill: red;");
			} else {
				btnOnoff.setStyle("-fx-text-fill: black;");
			}
		});
		
		btnExit = new Button("Exit");
		btnExit.setOnAction(event -> doExit(event));

		root.getChildren().addAll(btnOnoff, btnExit);

		getChildren().add(root);
	}

	private void doExit(ActionEvent event) {
		event.consume();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Close confirmation");
		alert.setContentText("Do you want to exit?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    Platform.exit();
		} 
	}

	private void toggleAlarm() {
		setAlarmEnabled(!getAlarmEnabled().get());
	}

	public BooleanProperty getAlarmEnabled() {
		return alarmEnabled;
	}

	public void setAlarmEnabled(boolean alarmEnabled) {
		this.alarmEnabled.setValue(alarmEnabled);
	}
}

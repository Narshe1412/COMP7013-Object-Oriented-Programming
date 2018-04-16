package application;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
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
		btnExit.setOnAction(event -> Platform.exit());

		root.getChildren().addAll(btnOnoff, btnExit);

		getChildren().add(root);
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

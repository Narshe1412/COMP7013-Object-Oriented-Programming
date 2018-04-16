package application;

import javafx.beans.property.BooleanProperty;

public enum State {
	INSTANCE;
	
	private BooleanProperty alarmEnabled;

	public BooleanProperty getAlarmEnabled() {
		return alarmEnabled;
	}

	public void setAlarmEnabled(BooleanProperty alarmEnabled) {
		this.alarmEnabled = alarmEnabled;
	}
}

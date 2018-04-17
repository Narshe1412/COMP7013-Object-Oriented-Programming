package application;

import javafx.beans.property.BooleanProperty;

/**
 * Singleton object to control state of the application
 * @author Manuel Colorado
 *
 */
public enum State {
	INSTANCE;
	
	private BooleanProperty alarmEnabled;

	/**
	 * Verify if the alarm is enabled on the system
	 * @return a Boolean observable property with true value if the alarm is enabled, false otherwise
	 */
	public BooleanProperty getAlarmEnabled() {
		return alarmEnabled;
	}

	/**
	 * Set the state of the application 
	 * @param alarmEnabled an boolean observable property that controls if the alarm is enabled on the system
	 */
	public void setAlarmEnabled(BooleanProperty alarmEnabled) {
		this.alarmEnabled = alarmEnabled;
	}
}

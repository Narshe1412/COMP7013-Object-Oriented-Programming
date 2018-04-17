package application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.PasswordField;

/**
 * Extends a JavaFX PasswordField to add custom behavior for the pin
 * 
 * @author Manuel Colorado
 *
 */
public class PinField extends PasswordField {

	private BooleanProperty validPassword;
	private final String passwordToCheck = "2018";

	/**
	 * Obtains an observable boolean property that determines if the password is
	 * valid or not
	 * 
	 * @return True if the password matches, false otherwise
	 */
	public BooleanProperty getValidPassword() {
		return validPassword;
	}

	/**
	 * Sets the valid password property of the system
	 * 
	 * @param enable
	 *            a boolean value that will be converted into a Boolean observable
	 *            property
	 */
	public void setValidPassword(final boolean enable) {
		this.validPassword = new SimpleBooleanProperty(enable);
	}

	/**
	 * Constructor
	 */
	public PinField() {
		this.setMaxWidth(80);
		setValidPassword(false);
		setPromptText("Your password");
		// When modified, check if password is valid
		textProperty().addListener((obs, oldValue, newValue) -> {
			if (!oldValue.equals(newValue)) {
				checkPw(newValue);
			}
		});
	}

	/**
	 * Verifies if the password matches the one
	 * 
	 * @param value
	 *            a string value that will be checked against the password to check
	 */
	private void checkPw(String value) {
		if (getText().trim().equals(passwordToCheck)) {
			validPassword.set(true);
		}
	}
}

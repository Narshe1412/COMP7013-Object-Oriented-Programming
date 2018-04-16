package application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.PasswordField;

public class PinField extends PasswordField {

	private BooleanProperty validPassword;

	public BooleanProperty getValidPassword() {
		return validPassword;
	}

	public void setValidPassword(final boolean enable) {
		this.validPassword = new SimpleBooleanProperty(enable);
	}

	public PinField() {
		setValidPassword(false);
		setPromptText("Your password");
		textProperty().addListener((obs, oldValue, newValue) -> {
			if (!oldValue.equals(newValue)) {
				checkPw(newValue);
			}
		});
	}

	private void checkPw(String value) {
		if (getText().trim().equals("2018")) {
			validPassword.set(true);
		}
	}
}

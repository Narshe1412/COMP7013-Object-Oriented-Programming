package ui;

import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Dentist;
/**
 * 
 * @author Manuel Colorado
 *
 */
public class LoginWindow extends Stage {

	/**
	 * Creates a Login Window for the system
	 */
	public LoginWindow() {
		/**
		 * Sets up the root pane for the UI
		 */
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25, 25, 25, 25));

		/**
		 * Title
		 */
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		root.add(scenetitle, 0, 0, 2, 1);

		/**
		 * Username fields
		 */
		Label lblUser = new Label("User Name:");
		root.add(lblUser, 0, 1);
		TextField txtUsername = new TextField();
		root.add(txtUsername, 1, 1);

		/**
		 * Password fields
		 */
		Label lblPassword = new Label("Password:");
		root.add(lblPassword, 0, 2);
		PasswordField txtPassword = new PasswordField();
		root.add(txtPassword, 1, 2);

		/**
		 * Login button
		 */
		Button btnLogin = new Button("   Login   ");
		// Sets as default button for the pane
		btnLogin.setDefaultButton(true);
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btnLogin);
		root.add(hbBtn, 1, 4);

		/**
		 * Check box for remembering the username for the system
		 */
		CheckBox cbRememberMe = new CheckBox("Remember Me");
		HBox hbRemember = new HBox(10);
		hbRemember.setAlignment(Pos.BOTTOM_RIGHT);
		hbRemember.getChildren().add(cbRememberMe);
		root.add(hbRemember, 1, 5);

		/**
		 * Text box to show errors during login
		 */
		final Text errorMessage = new Text();
		root.add(errorMessage, 1, 7);
		errorMessage.setFill(Color.FIREBRICK);

		/**
		 * Event handler for clicking the button
		 */
		btnLogin.setOnAction((event) -> {
			// Checks if username or password fields are empty
			if (txtUsername.getText().trim().equals("") || txtPassword.getText().trim().equals("")) {
				errorMessage.setText("Please enter username and password.");
				txtPassword.setText("");
				txtUsername.requestFocus();
			} else {
				// If they're not empty, attempt to find the user in the list of users from the system
				try {
					Dentist user = AppData.INSTANCE.getUserList().find(txtUsername.getText().trim());
					// If the user is not found
					if (user != null) {
						// If the user is found, but password does not match
						if (user.verifyPassword(txtPassword.getText().trim())) {
							if (cbRememberMe.isSelected()) {
								AppData.INSTANCE.setSavedUser(user);
							}
							AppState.INSTANCE.setCurrentUser(user);
							// If user is eligible for password reset
							if (user.verifyPassword("1111")) {
								ChangePasswordWindow window = new ChangePasswordWindow();
								window.showAndWait();
								if (user.verifyPassword("1111")) {
									AlertDialog alert = new AlertDialog(AlertType.ERROR, "Critical Error", "Password reset not completed", "You cannot continue with an insecure password. The application will now close");
									alert.showAndWait();
									Platform.exit();
								}
							}
							AppNavigation.setMainWindow(new HomeWindow());
							AppNavigation.showWindow();
							close();
						} else {
							errorMessage.setText("Wrong username or password.");
							txtPassword.setText("");
							txtPassword.requestFocus();
						}
					} else {
						errorMessage.setText("Wrong username.");
						txtPassword.setText("");
						txtPassword.requestFocus();
					}
				} catch (Exception e) {
					// TODO EXCEPTION
					e.printStackTrace();
				}
			}
		});
		// Clears the text from empty spaces
		txtUsername.textProperty().addListener((event) -> {
			if (errorMessage.getText().trim().equals("")) {
				errorMessage.setText("");
			}
		});
		// Clears the password from empty spaces
		txtUsername.textProperty().addListener((event) -> {
			if (errorMessage.getText().trim().equals("")) {
				errorMessage.setText("");
			}
		});

		// Creates the window and sets up the scene
		Scene scene = new Scene(root, 300, 250);
		setScene(scene);
		setTitle("Login");
		setResizable(false);
	};
}

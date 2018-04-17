package application;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
/**
 * Main window
 * @author Manuel Colorado
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Get singleton property to check if system is working
		State.INSTANCE.setAlarmEnabled(new SimpleBooleanProperty(false));

		// Load UI
		BorderPane root = new BorderPane();
		StackPane topMenu = new StackPane();

		// Create a new custom password field
		PinField pin = new PinField();
		topMenu.getChildren().add(pin);
		
		// It's visible only if the password has not been entered
		pin.visibleProperty().bind(pin.getValidPassword().not());

		// Create a menu if the password was correct
		ButtonMenu menu = new ButtonMenu();
		topMenu.getChildren().add(menu);
		// Visible if the password is correct
		menu.visibleProperty().bind(pin.getValidPassword());
		root.setTop(topMenu);

		// House only visible if password is correct
		House house = new House();
		root.setCenter(house);
		house.visibleProperty().bind(pin.getValidPassword());

		// Load the stage
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}

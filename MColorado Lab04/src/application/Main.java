package application;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Main window
 * 
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
		VBox pingrp = new VBox(5);
		pingrp.setAlignment(Pos.CENTER);
		PinField pin = new PinField();
		pingrp.getChildren().addAll(new Label("Enter your pin:"), pin);
		topMenu.getChildren().add(pingrp);

		// It's visible only if the password has not been entered
		pingrp.visibleProperty().bind(pin.getValidPassword().not());

		// Create a menu if the password was correct
		ButtonMenu menu = new ButtonMenu();
		topMenu.getChildren().add(menu);
		// Visible if the password is correct
		menu.visibleProperty().bind(pin.getValidPassword());
		root.setTop(topMenu);
		BorderPane.setAlignment(topMenu, Pos.CENTER);

		// House only visible if password is correct
		House house = new House();
		root.setCenter(house);
		house.visibleProperty().bind(pin.getValidPassword());

		// Load the stage
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		// Handles on close event
		primaryStage.setOnCloseRequest(event -> {
			event.consume();
			menu.doExit();
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}

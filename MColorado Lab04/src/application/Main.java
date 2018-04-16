package application;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		State.INSTANCE.setAlarmEnabled(new SimpleBooleanProperty(false));

		BorderPane root = new BorderPane();
		StackPane topMenu = new StackPane();

		PinField pin = new PinField();
		topMenu.getChildren().add(pin);
		pin.visibleProperty().bind(pin.getValidPassword().not());

		ButtonMenu menu = new ButtonMenu();
		topMenu.getChildren().add(menu);
		menu.visibleProperty().bind(pin.getValidPassword());

		root.setTop(topMenu);

		House house = new House();
		root.setCenter(house);
		house.visibleProperty().bind(pin.getValidPassword());

		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}

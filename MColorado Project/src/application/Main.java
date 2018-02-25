package application;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.LoginWindow;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//TODO REMOVE AFTER TESTING
		LoginWindow.showWindow();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}

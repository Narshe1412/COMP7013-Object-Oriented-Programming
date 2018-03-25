package application;
	
import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setWidth(600);
		primaryStage.setHeight(500);
		Controller.getInstance().setStage(primaryStage);
		Controller.getInstance().runStart();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

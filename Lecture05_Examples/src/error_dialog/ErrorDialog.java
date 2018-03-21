package error_dialog;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ErrorDialog extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Look, an Error Dialog");
		alert.setContentText("Ooops, there was an error!");

		alert.showAndWait();
		
	}
}

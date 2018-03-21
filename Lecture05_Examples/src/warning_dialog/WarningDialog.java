package warning_dialog;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class WarningDialog extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning Dialog");
		alert.setHeaderText("Look, a Warning Dialog");
		alert.setContentText("Careful with the next step!");

		alert.showAndWait();
	}
}

package custom_confirm_dialog;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class CustomConfirmDialog extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Look, a Confirmation Dialog");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Information Dialog");
			alert2.setHeaderText(null);
			alert2.setContentText("You clicked ok. Exiting in 5 seconds");

			alert2.showAndWait();
		} else {
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Information Dialog");
			alert2.setHeaderText(null);
			alert2.setContentText("You clicked cancel :(. Exiting in 5 seconds");

			alert2.showAndWait();
		}
		alert.showAndWait();
	}
}

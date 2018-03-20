package application;

import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;
import ui.AppMenu;
import ui.HomeWindow;
import ui.LoginDialog;
import ui.LoginWindow;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();*/
		
		//TODO REMOVE AFTER TESTING
		LoginWindow.showWindow();
		HomeWindow.showWindow();
		
/*		LoginDialog login = new LoginDialog();
		Optional<Pair<String, String>> result = login.showAndWait();

		result.ifPresent(usernamePassword -> {
		    if(usernamePassword.getKey().equalsIgnoreCase("test") && usernamePassword.getValue().equalsIgnoreCase("test")) {
		    	
				
		    } else {
				System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
			}
		});*/
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}

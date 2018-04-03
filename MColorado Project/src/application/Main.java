package application;

import java.util.List;

import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Defaults;
import model.Dentist;
import model.Invoice;
import model.Patient;
import model.Payment;
import model.Procedure;
import persistence.FileHandler;
import persistence.IDBManager;
import ui.HomeWindow;
import ui.InitialLoadWindow;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		/*BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();*/
		
		//TODO REMOVE AFTER TESTING
		InitialLoadWindow loader = new InitialLoadWindow();
		
		
		
		AppState.INSTANCE.setCurrentPatient(AppData.INSTANCE.getPatientList().get(0));

		
		//TESTING HOME WINDOW
		AppState.INSTANCE.setCurrentUser(AppData.INSTANCE.getUserList().get(0));
		AppNavigation app = new AppNavigation(new HomeWindow());
		app.showWindow();


		//LoginWindow loginWindow = new LoginWindow();
		//loginWindow.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}



/*		LoginDialog login = new LoginDialog();
Optional<Pair<String, String>> result = login.showAndWait();

result.ifPresent(usernamePassword -> {
    if(usernamePassword.getKey().equalsIgnoreCase("test") && usernamePassword.getValue().equalsIgnoreCase("test")) {
    	
		
    } else {
		System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
	}
});*/
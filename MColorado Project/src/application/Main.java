package application;

import controller.AppData;
import controller.AppState;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Defaults;
import ui.LoginWindow;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();*/
		
		//TODO REMOVE AFTER TESTING
		AppData.INSTANCE.setUserList(Defaults.createDentists());
		AppData.INSTANCE.setPatientList(Defaults.createPatient());
		AppState.INSTANCE.setCurrentPatient(AppData.INSTANCE.getPatientList().get(0));
		LoginWindow loginWindow = new LoginWindow();
		loginWindow.show();

		

		
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
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.InitialLoadWindow;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		

		InitialLoadWindow loader = new InitialLoadWindow();
		
		
		




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
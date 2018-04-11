package application;

import exception.UncaughtExcepHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import persistence.MySQLController;
import persistence.TableHandler;
import ui.InitialLoadWindow;
/**
 * Loads the application
 * @author Manuel Colorado
 *
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Set up the default exception handler for unhandled exceptions
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExcepHandler());
		@SuppressWarnings("unused")
		InitialLoadWindow loader = new InitialLoadWindow();
		TableHandler test = new TableHandler("test");
		System.out.println(test.exists());
	}

	public static void main(String[] args) {
		launch(args);
	}
}

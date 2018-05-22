package application;

import controller.AppController;
import exception.UncaughtExcepHandler;
import javafx.application.Application;
import javafx.stage.Stage;
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
		InitialLoadWindow loader = new InitialLoadWindow(new AppController());
	}

	public static void main(String[] args) {
		launch(args);
	}
}

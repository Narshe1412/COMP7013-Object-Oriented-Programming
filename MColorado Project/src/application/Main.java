package application;

import exception.UncaughtExcepHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.InitialLoadWindow;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExcepHandler());
		@SuppressWarnings("unused")
		InitialLoadWindow loader = new InitialLoadWindow();

	}

	public static void main(String[] args) {
		launch(args);
	}
}

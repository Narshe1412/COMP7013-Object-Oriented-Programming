package ui;

import controller.AppState;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeWindow {
	private HomeWindow() {};
	
	public static void showWindow() {
		BorderPane root = new BorderPane();
		
		TabPane tabs = new TabPane();
		tabs.getTabs().add(new TabMain());//TODO
		root.setCenter(tabs);
		
		AppMenu menuBar = new AppMenu();
		menuBar.prefWidthProperty().bind(root.widthProperty());
	    root.setTop(menuBar);
		/*MenuBar menuBar = new MenuBar();
	    menuBar.prefWidthProperty().bind(Controller.getInstance().getStage().widthProperty());
	    root.setTop(menuBar);
*/
		//TODO temp
	    root.setBottom(new Label("Welcome user: " + AppState.INSTANCE.getCurrentUser().getUsername()));
		Stage homeWindow = new Stage();
		Scene scene = new Scene(root, 400, 400);
		homeWindow.setScene(scene);
		homeWindow.setTitle("Main");
		homeWindow.setResizable(false);
		homeWindow.show();
	} 
}

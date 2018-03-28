package ui;

import controller.AppState;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeWindow {
	private HomeWindow() {};
	
	public static void showWindow() {
		BorderPane root = new BorderPane();
		
		TabPane tabs = new TabPane();
		//TODO fix the app workflow as soon as all tabs have been done
		//tabs.getTabs().add(new TabMain());
		tabs.getTabs().add(new TabInvoice(0));
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
		Scene scene = new Scene(root, 640, 480);
		homeWindow.setScene(scene);
		homeWindow.setTitle("Boca bites™");
		homeWindow.getIcons().add(new Image("/assets/smile.png"));
		homeWindow.setResizable(false);
		homeWindow.show();
	} 
}

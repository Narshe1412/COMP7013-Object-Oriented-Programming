package ui;

import controller.AppState;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomeWindow extends Stage{
	
	private TabPane tabs;
	
	public HomeWindow() {
		BorderPane root = new BorderPane();
		
		tabs = new TabPane();
		//TODO fix the app workflow as soon as all tabs have been done
		addNewTab(new TabMain(this));
		addNewTab(new TabInvoice(0));
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
	    
		
		Scene scene = new Scene(root, 640, 480);
		setScene(scene);
		setTitle("Boca bites™");
		getIcons().add(new Image("/assets/smile.png"));
		setResizable(false);
	};
	
	public void addNewTab(Tab t) {
		tabs.getTabs().add(t);
		tabs.getSelectionModel().select(t);
	}
}

package ui;

import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InitialLoadWindow extends Stage{

	public InitialLoadWindow() throws Exception {
		super();
		
		BorderPane root = new BorderPane();
		
		root.setCenter(new ImageView("/assets/smile.png"));
		root.setBottom(new Text("Loading Boca bites� database"));
		
		Scene scene = new Scene(root, 400, 400);
		
		setTitle("Loading Boca bites� ...");
		getIcons().add(new Image("/assets/smile.png"));
		setResizable(false);
		setScene(scene);
		show();
		AppNavigation.loadConfig();
		AppNavigation.loadState();
		
		
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), delay -> {
			loadMain();
			close();
		}));
		timeline.play();
		
	}
	
	
	private void loadMain() {
		//AppState.INSTANCE.setCurrentPatient(AppData.INSTANCE.getPatientList().get(0));
		
		if (AppData.INSTANCE.getSavedUser() == null) {
			LoginWindow loginWindow = new LoginWindow();
			loginWindow.show();
		} else {
			AppState.INSTANCE.setCurrentUser(AppData.INSTANCE.getSavedUser());
			AppNavigation.setMainWindow(new HomeWindow());
			AppNavigation.showWindow();
		}
	}
}

package ui;

import controller.AppController;
import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Creates the loading window for the application
 * 
 * @author Manuel Colorado
 * @version 1.0
 */
public class InitialLoadWindow extends Stage {

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 *             Handles an exception that can be thrown if the database load
	 *             fails
	 * 
	 */
	public InitialLoadWindow() throws Exception {
		super();

		BorderPane root = new BorderPane();
		ImageView textLogo = new ImageView("/assets/textlogo.png");
		textLogo.setFitWidth(250);
		textLogo.setFitHeight(80);
		BorderPane.setAlignment(textLogo, Pos.CENTER);
		root.setTop(textLogo);

		ImageView scaledImageView = new ImageView("/assets/logo.png");
		scaledImageView.setFitWidth(300);
		scaledImageView.setFitHeight(300);
		root.setCenter(scaledImageView);

		root.setBottom(new Text("Loading Boca bites database"));

		Scene scene = new Scene(root, 400, 400);

		setTitle("Loading Boca bites");
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

	/**
	 * Loads the database and config objects
	 */
	private void loadMain() {
		if (AppData.INSTANCE.getSavedUser() == null) {
			LoginWindow loginWindow = new LoginWindow();
			loginWindow.show();
		} else {
			AppState.INSTANCE.setCurrentUser(AppData.INSTANCE.getSavedUser());
			AppNavigation.setMainWindow(new HomeWindow(new AppController()));
			AppNavigation.showWindow();
		}
	}
}

// TODO Add Progress bar animation
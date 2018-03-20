package ui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginWindow {
	
	private LoginWindow() {};
	
	public static void showWindow() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(20));
		GridPane group = new GridPane();
		group.setHgap(5);
		group.setVgap(5);
		group.add(new Label("Username:"), 0, 0);
		group.add(new TextField(), 1, 0);
		group.add(new Label("Password:"), 0,1);
		group.add(new PasswordField(), 1, 1);

		Button loginBtn = new Button("Login");
		StackPane center = new StackPane();
		center.getChildren().add(loginBtn);
		
		root.setTop(group);
		root.setBottom(center);
		
		Stage loginWindow = new Stage();
		Scene scene = new Scene(root, 240, 130);
		loginWindow.setScene(scene);
		loginWindow.setTitle("Login");
		loginWindow.setResizable(false);
		loginWindow.show();
	}
	
}

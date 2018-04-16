package application;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class House extends Pane{

	public House() {
		GridPane root = new GridPane();
		root.setPadding(new Insets(10));
		
		Room sitting = new Room("Sitting Room");
		Room living = new Room("Living Room");
		Room bedroom = new Room("Bed Room");
		Room kitchen = new Room("Kitchen");
		
		root.add(sitting, 0, 0);
		root.add(bedroom, 1, 0);
		root.add(living, 0, 1);
		root.add(kitchen, 1, 1);
		
		getChildren().add(root);
	}
}

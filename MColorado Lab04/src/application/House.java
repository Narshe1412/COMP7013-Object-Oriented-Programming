package application;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class House extends Pane{

	public House() {
		GridPane root = new GridPane();
		root.setPadding(new Insets(10));
		root.setHgap(10); //horizontal gap in pixels => that's what you are asking for
		root.setVgap(10); //vertical gap in pixels
		
		Room sitting = new Room("Sitting Room", "-fx-border-color: black");
		Room living = new Room("Living Room", "-fx-border-color: red");
		Room bedroom = new Room("Bed Room", "-fx-border-color: blue");
		Room kitchen = new Room("Kitchen", "-fx-border-color: green");
		
		root.add(sitting, 0, 0);
		root.add(bedroom, 1, 0);
		root.add(living, 0, 1);
		root.add(kitchen, 1, 1);
		
		getChildren().add(root);
	}
}

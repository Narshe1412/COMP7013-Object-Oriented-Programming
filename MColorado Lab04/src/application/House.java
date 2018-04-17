package application;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Creates the house that will host all the rooms that needs to be checked for intruders
 * @author Manuel Colorado
 *
 */
public class House extends Pane{

	public House() {
		GridPane root = new GridPane();
		root.setPadding(new Insets(10));
		root.setHgap(10); 
		root.setVgap(10); 
		
		// Adding rooms
		Room sitting = new Room("Sitting Room", "-fx-border-color: black");
		Room living = new Room("Living Room", "-fx-border-color: red");
		Room bedroom = new Room("Bed Room", "-fx-border-color: blue");
		Room kitchen = new Room("Kitchen", "-fx-border-color: green");
		
		// Room locations
		root.add(sitting, 0, 0);
		root.add(bedroom, 1, 0);
		root.add(living, 0, 1);
		root.add(kitchen, 1, 1);
		
		getChildren().add(root);
	}
}

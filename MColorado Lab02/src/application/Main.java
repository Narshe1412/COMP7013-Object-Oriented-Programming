package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.paint.Color;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Create root Scene with a borderpane + insets of 30
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(30));

			// Create a regular hexagon and pentagon with my custom class
			RegularPolygon hexagon = new RegularPolygon(6, 100);
			RegularPolygon pentagon = new RegularPolygon(5, 100);

			// Create a square with rounded edges, using Rectangle class
			Rectangle roundedSquare = new Rectangle(100, 100);
			roundedSquare.setArcHeight(20);
			roundedSquare.setArcWidth(20);

			// Add hexagon and pentagon to top and bottom in borderpane using custom subpane
			// wrapper
			root.setTop(new SubPane(hexagon));
			root.setBottom(new SubPane(pentagon));

			// Add a default rectangle using custom subpane
			root.setLeft(new SubPane(new Rectangle(70, 100)));

			// Create an X shape calling the custom method, using custom subpane wrapper
			root.setRight(new SubPane(createEx(RandomColor.getRandom())));

			// Add rounded square again with custom subpane
			root.setCenter(new SubPane(roundedSquare));

			// Create the window with size 500x400
			Scene scene = new Scene(root, 500, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			// Set window title and initial coords
			primaryStage.setTitle("Primary Window");
			primaryStage.setX(150);
			primaryStage.setY(100);
			// Show window
			primaryStage.show();

			// Create secondary window
			SecondWindow secondaryStage = new SecondWindow(300, 500);
			// Show secondary window
			secondaryStage.showWindow(new Stage());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group createEx(Color fill) {
		// This method creates a group of two rectangles and set up their coords
		// So they stay in a X shape by setting the rotation and using the fill
		// parameter for their colors
		// Returns the group so it can be added as node
		Group group = new Group();
		Rectangle original = new Rectangle(20, 100);
		original.setRotate(45);
		original.setFill(fill);
		group.getChildren().add(original);
		Rectangle rotated = new Rectangle(20, 100);
		rotated.setRotate(135);
		rotated.setFill(fill);
		group.getChildren().add(rotated);
		return group;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

class SubPane extends StackPane {
	// This class simply adds custom random color to all elements of the subpane
	public SubPane(Node element) {
		getChildren().add(element);
		if (element instanceof Shape) {
			((Shape) element).setFill(RandomColor.getRandom());
		}
	}

}

final class RandomColor {
	// Static class that can be used to generate random colors
	public static Color getRandom() {
		return Color.color(Math.random(), Math.random(), Math.random());
	}
}
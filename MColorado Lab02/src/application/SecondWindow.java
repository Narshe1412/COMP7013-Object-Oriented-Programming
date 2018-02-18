package application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class SecondWindow {
	private final double height;
	private final double width;

	public SecondWindow(double height, double width) {
		this.height = height;
		this.width = width;
	}

	public void showWindow(Stage parent) {
		try {
			// Create a borderpane as root to place our shapes for the second window
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(30));

			// Create a stackpane as group to place and center both shape and label
			StackPane topGroup = new StackPane();
			// Create a regular circle and set fills and borders
			Circle circle = new Circle(100);
			circle.setFill(Color.WHITE);
			circle.setStroke(Color.BLACK);
			circle.setStrokeWidth(10);
			// Add the circle to the pane
			topGroup.getChildren().add(circle);
			// Add a generic label to the pane
			topGroup.getChildren().add(new Label("Circle"));

			// Create a stackpane as group to place and center both shape and label
			StackPane bottomGroup = new StackPane();
			// Create a square using the Rectangle class and set up fill, rounded corners
			// and border setting
			Rectangle square = new Rectangle(200, 200);
			square.setFill(Color.WHITE);
			square.setStroke(Color.BLACK);
			square.setStrokeWidth(10);
			square.setArcHeight(20);
			square.setArcWidth(20);
			square.setStrokeLineCap(StrokeLineCap.ROUND);
			// Add square to the stackpane
			bottomGroup.getChildren().add(square);
			// Add generic label to the pane
			bottomGroup.getChildren().add(new Label("Square"));

			// Set up the grouped elements in each of the positions within the borderpane
			root.setTop(topGroup);
			root.setBottom(bottomGroup);

			// Create the secondary window
			Scene scene = new Scene(root, height, width);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			parent.setScene(scene);
			// Set title and starting coords
			parent.setTitle("Secondary Window");
			parent.setX(700);
			parent.setY(100);
			// Show secondary window
			parent.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

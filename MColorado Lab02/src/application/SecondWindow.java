package application;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class SecondWindow {
	private final double height;
	private final double width;
	
	public SecondWindow (double height, double width) {
		this.height = height;
		this.width = width;
	}
	
	public void showWindow(Stage parent) {
		try {
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(30));

			StackPane topGroup = new StackPane();	
			Circle circle = new Circle (100);
			circle.setFill(Color.WHITE);
			circle.setStroke(Color.BLACK);
			circle.setStrokeWidth(10);
			topGroup.getChildren().add(circle);
			topGroup.getChildren().add(new Label("Circle"));
			

			StackPane bottomGroup = new StackPane();
			Rectangle square = new Rectangle(200, 200);
			square.setFill(Color.WHITE);
			square.setStroke(Color.BLACK);
			square.setStrokeWidth(10);
			square.setArcHeight(20);
			square.setArcWidth(20);
			square.setStrokeLineCap(StrokeLineCap.ROUND);
			bottomGroup.getChildren().add(square);
			bottomGroup.getChildren().add(new Label("Square"));
			
			root.setTop(topGroup);
			root.setBottom(bottomGroup);

			Scene scene = new Scene(root, height, width);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			parent.setScene(scene);
			parent.setTitle("Secondary Window");
			parent.setX(700);
			parent.setY(100);
			parent.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

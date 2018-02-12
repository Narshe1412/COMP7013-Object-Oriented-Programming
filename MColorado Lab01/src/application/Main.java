package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// create GridPane, centered, with 10 padding and a gap of 20
			GridPane root = new GridPane();
			root.setAlignment(Pos.CENTER);
			root.setPadding(new Insets(10));
			root.setHgap(20);

			Circle circle = new Circle();
			// fixed size circle
			// 		circle.setRadius(75);
			// Circle radius is set to the half of the grid size,
			// which is half of the pane, because we have two columns
			// minus 15 (as padding+gap is 30)
			circle.radiusProperty().bind(root.widthProperty().divide(4).subtract(15));
			// Add some random color
			circle.setFill(Color.color(Math.random(), Math.random(), Math.random()));

			Rectangle square = new Rectangle();
			// fixed size square
			// 		square.setWidth(150);
			// 		square.setHeight(150);
			// Square height and width are bound to grid size halved, as we have two colums
			// minus 30, which makes for the padding + gap
			square.heightProperty().bind(root.widthProperty().divide(2).subtract(30));
			square.widthProperty().bind(root.widthProperty().divide(2).subtract(30));
			// Add some random color
			square.setFill(Color.color(Math.random(), Math.random(), Math.random()));

			// add two shapes to positions 0 and 1 in the first column
			root.add(circle, 0, 0);
			root.add(square, 1, 0);

			// Stage set up
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			// Set max height to the window, so the shapes don't mess up after being bound
			primaryStage.setMinHeight(250);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

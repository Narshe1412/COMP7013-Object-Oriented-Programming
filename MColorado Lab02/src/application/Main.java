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
			BorderPane root = new BorderPane();
			root.setPadding(new Insets(30));

			RegularPolygon hexagon = new RegularPolygon(6, 100);
			RegularPolygon pentagon = new RegularPolygon(5, 100);
			Rectangle roundedSquare = new Rectangle(100, 100);
			roundedSquare.setArcHeight(20);
			roundedSquare.setArcWidth(20);

			root.setTop(new SubPane(hexagon));
			root.setBottom(new SubPane(pentagon));
			root.setLeft(new SubPane(new Rectangle(70, 100)));
			root.setRight(new SubPane(createEx(RandomColor.getRandom())));
			root.setCenter(new SubPane(roundedSquare));

			Scene scene = new Scene(root, 500, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Group createEx(Color fill) {
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
	public SubPane(Node element) {
		getChildren().add(element);
		if (element instanceof Shape) {
			((Shape) element).setFill(RandomColor.getRandom());
		}
	}

}

final class RandomColor {
	public static Color getRandom() {
		return Color.color(Math.random(), Math.random(), Math.random());
	}
}
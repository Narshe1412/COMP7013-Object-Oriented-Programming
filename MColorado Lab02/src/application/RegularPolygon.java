package application;

import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

public class RegularPolygon extends Polygon {

	public RegularPolygon(int amountOfSides, double size) {
		ObservableList<Double> list = this.getPoints();

		double centerX = size / 2, centerY = size / 2;
		double radius = size * 0.4;

		// Add points to the polygon list
		for (int i = 0; i < amountOfSides; i++) {
			list.add(centerX + radius * Math.cos(2 * i * Math.PI / amountOfSides));
			list.add(centerY - radius * Math.sin(2 * i * Math.PI / amountOfSides));
		}
	}
}

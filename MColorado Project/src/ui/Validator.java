package ui;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * Validates different input rules in the client side
 * 
 * @author Manuel Colorado
 * @version 1.0
 */
public class Validator {

	/**
	 * Verifies if the input matches the length specified by parameter
	 * 
	 * @param test
	 *            String value that will be tested by size
	 * @param min
	 *            Minimum string size that will be checked
	 * @param max
	 *            Maximum string size that will be checked
	 * @return true if the string size matches the values passed by parameter, false
	 *         otherwise
	 */
	public static boolean stringValidator(final String test, final int min, final int max) {
		return (test.length() >= min && test.length() <= max);
	}

	/**
	 * Creates a listener in a TextField node that will check changes and validate
	 * using the StringValidator such changes
	 * 
	 * @param test
	 *            the string that will be tested in the validator
	 * @param min
	 *            minimum size of the string that will be tested
	 * @param max
	 *            maximum size of the string that will be tested
	 * @param node
	 *            the node that contains the string that will be tested
	 * @param tooltip
	 *            the tooltip that will be set up in the node if validation fails
	 */
	public static void setStringValidation(final String test, final int min, final int max, TextField node,
			final String tooltip) {

		if (!stringValidator(test, min, max)) {
			setRed(node, tooltip);
			node.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!oldValue.equals(newValue) && stringValidator(newValue, min, max)) {
					setGreen(node);
				} else {
					setRed(node);
				}
			});
		}
	}

	/**
	 * Verifies if the string is a value that can be converted to a Double number
	 * 
	 * @param test
	 *            String value that will be tested
	 * @return true if the string can be parsed into a Double, false otherwise
	 */
	public static boolean doubleValidator(final String test) {
		try {
			Double.parseDouble(test);
		} catch (NumberFormatException e) {
			return false;
		}
		return (Double.parseDouble(test) >= 0);
	}

	/**
	 * Sets up a listener to a TextField passed by parameter that will check for
	 * changes in the TextProperty of the node and change the field between red and
	 * green depending if the value is valid
	 * 
	 * @param test
	 *            The text that will be tested if it's double or not
	 * @param node
	 *            The node that will be changed if the test fails
	 * @param tooltip
	 *            A string representing the tooltip that will be added to the node
	 */
	public static void setDoubleValidation(final String test, TextField node, String tooltip) {
		if (!doubleValidator(test)) {
			setRed(node, tooltip);
			node.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!oldValue.equals(newValue) && doubleValidator(newValue)) {
					setGreen(node);
				} else {
					setRed(node);
				}
			});
		}
	}

	/**
	 * Verifies if the string is a value that can be converted to a positive Integer
	 * 
	 * @param test
	 *            String that will be evaluated
	 * @return true if the string can be parsed to an Integer greater than 0, false
	 *         otherwise
	 */
	public static boolean unsignedIntValidator(String test) {
		try {
			int i = Integer.parseInt(test);
			if (i < 0)
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Sets up a listener to a TextField passed by parameter that will check for
	 * changes in the TextProperty of the node and change the field between red and
	 * green depending if the value is valid
	 * 
	 * @param test
	 *            The text that will be tested if it's positive integer or not
	 * @param node
	 *            The node that will be changed if the test fails
	 * @param tooltip
	 *            A string representing the tooltip that will be added to the node
	 */
	public static void setSignedIntValidation(final String test, TextField node, String tooltip) {
		if (!unsignedIntValidator(test)) {
			setRed(node, tooltip);
			node.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!oldValue.equals(newValue) && unsignedIntValidator(newValue)) {
					setGreen(node);
				} else {
					setRed(node);
				}
			});
		}
	}

	/**
	 * Sets the border of the node passed by parameter red (failed validation)
	 * 
	 * @param n
	 *            A JavaFX node
	 */
	public static void setRed(Node n) {
		n.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
	}

	/**
	 * Sets the border of the node passed by parameter red (failed validation) and
	 * set up a tooltip, if compatible
	 * 
	 * @param n
	 *            A JavaFX node
	 * @param tooltip
	 *            A string of text that will be included as node tooltip
	 */

	public static void setRed(Node n, String tooltip) {
		if (n instanceof TextField) {
			((TextField) n).setTooltip(new Tooltip(tooltip));
		}
		n.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
	}

	/**
	 * Sets the border of the node passed by parameter green (passed validation)
	 * 
	 * @param n
	 *            A JavaFX node
	 */
	public static void setGreen(Node n) {
		n.setStyle("-fx-border-color: green ; -fx-border-width: 1px ;");
	}
}

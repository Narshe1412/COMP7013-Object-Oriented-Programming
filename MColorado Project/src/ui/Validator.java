package ui;

/**
 * Validates different input rules in the client side
 * 
 * @author Manuel Colorado
 *
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
		return true;
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

}

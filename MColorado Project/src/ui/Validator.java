package ui;

public class Validator {

	public static boolean stringValidator(String test, int min, int max) {
		if (test.length() >= min && test.length() <= max) {
			return true;
		}
		return false;
	}

	public static boolean doubleValidator(String test) {
		try {
			Double.parseDouble(test);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

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

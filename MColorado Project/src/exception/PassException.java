package exception;

/**
 * Custom exception to extends the regular exception functionality with a details field
 * @author Manuel Colorado
 *
 */
public class PassException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a custom exception with an additional details field
	 * @param details
	 */
	public PassException(String details) {
		super(details);
	}
}

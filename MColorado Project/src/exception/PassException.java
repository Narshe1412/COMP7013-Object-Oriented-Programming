package exception;

@SuppressWarnings("serial")
public class PassException extends Exception {
	public PassException(String details) {
		super(details);
	}
}

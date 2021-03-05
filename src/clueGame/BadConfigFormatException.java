package clueGame;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Invalid format on Config files");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}
}

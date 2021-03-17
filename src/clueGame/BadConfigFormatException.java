package clueGame;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Invalid format on Config files");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
		logError(message);
	}
	
	private void logError(String message) {
		try {
			FileWriter writer = new FileWriter("ErrorLog.txt");
			writer.write(message);
			writer.write(Arrays.toString(super.getStackTrace()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package clueGame;

import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Invalid format on Config files");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
		logError(message);
	}
	
	private void logError(String Message) {
		try {
			FileWriter writer = new FileWriter("ErrorLog.txt");
			writer.write(Message);
			writer.write(super.getStackTrace().toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

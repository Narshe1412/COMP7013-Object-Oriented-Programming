package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import exception.ExceptionDialog;

/**
 * Handles the serialization of the application object into a file
 * 
 * @author Manuel Colorado
 *
 */
public class FileController {

	/**
	 * Creates a file using the path passed by parameter
	 * 
	 * @param path
	 *            a string representing an absolute or relative path on the system
	 */
	public final void createFile(final String path) {
		File file = new File(path);
		try {
			if (file.exists()) {
				ExceptionDialog exWin = new ExceptionDialog("Unable to create file", "File already exits",
						"Unable to overwrite required file.");
				exWin.show();
			} else {
				file.createNewFile();
			}
		} catch (IOException e) {
			ExceptionDialog exWin = new ExceptionDialog("Unable to create file",
					"An unexpected error occured when attempting to create a new file.", e);
			exWin.show();
		}
	}

	/**
	 * Loads a file using the path passed by parameter
	 * 
	 * @param path
	 *            a string representing an absolute or relative path on the system
	 * @return an object obtained from the serialization of the file
	 */
	public final Object loadFile(final String path) {
		// Loads the file from path, and return the object that is stored
		File file = new File(path);
		Object loadedInfo = null;
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(path);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedInfo = ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				// This exception shouldn't ever execute as we do file.exists() before the try
				// block, however the file may disappear in the meantime, so included here as
				// safeguard
				ExceptionDialog exWin = new ExceptionDialog("File Not Found", "Unable to open the required file.", e);
				exWin.show();
			} catch (IOException | ClassNotFoundException e) { // Other IO exceptions handled here
				ExceptionDialog exWin = new ExceptionDialog("Unable to Open file",
						"An unexpected error when attempting to load the file.", e);
				exWin.show();
			}
		}
		return loadedInfo;
	}

	/**
	 * Saves a file used the path passed by parameter
	 * 
	 * @param path
	 *            a string representing an absolute or relative path on the system
	 * @param info
	 *            an object that will be serialized into the file
	 * @return true if the save operation was successful, false otherwise
	 */
	public final boolean saveFile(final String path, final Object info) {
		// Saves the file to the path provided and returns true if successful
		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(info);
			oos.close();
			return true;
		} catch (IOException e) { // IO Exceptions are handled here
			ExceptionDialog exWin = new ExceptionDialog("Unable to Save file",
					"An unexpected error when attempting to save the file.", e);
			exWin.show();
			return false;
		}
	}
}

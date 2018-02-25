package persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileController {

	public void CreateFile(final String path) {
		File file = new File(path);

		try {
			if (file.exists()) {
				System.out.println("File already exists");
			} else {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public Object LoadFile(final String path) {
		// Loads the file from path, and return the object that is stored
		File file = new File(path);
		Object loadedInfo = null;
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(path);
				ObjectInputStream ois = new ObjectInputStream(fis);
				loadedInfo = ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) { // This exception shouldn't ever execute as we do file.exists() before
												// the
												// try block, however the file may disappear in the meantime, so
												// included
												// here as safeguard
				e.printStackTrace();
				System.out.println("File not found.");
				System.out.println(e.getMessage());
			} catch (IOException | ClassNotFoundException e) { // Other IO exceptions handled here
				e.printStackTrace();
				System.out.println("Error when opening the file.");
				System.out.println(e.getMessage());
			}
		}
		return loadedInfo;
	}

	public boolean SaveFile(final String path, final Object info) {
		// Saves the file to the path provided and returns true if successful
		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(info);
			oos.close();
			return true;
		} catch (IOException e) { // IO Exceptions are handled here
			e.printStackTrace();
			System.out.println(e.getMessage());
			return false;
		}
	}
}

package persistence;

import java.io.File;

/**
 * Facade to handle all the serialization in the system
 * 
 * @author Manuel Colorado
 *
 */
public class FileHandler extends FileController implements IDBManager {
	private final String path;

	/**
	 * Constructor
	 * 
	 * @param path
	 *            stores the string path so it remains consistent for all operations
	 */
	public FileHandler(final String path) {
		this.path = path;
	}

	/**
	 * Returns the value if the file exists or not
	 */
	@Override
	public boolean exists() {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * Creates a file for serialization
	 */
	@Override
	public void createDB() {
		createFile(path);
	}

	/**
	 * Saves the object information in the stored path
	 */
	@Override
	public Object loadDB() {
		return loadFile(path);
	}

	/**
	 * Attempts to store the information of the object in the stored path
	 */
	@Override
	public boolean saveDB(final Object database) {
		return saveFile(path, database);
	}

}

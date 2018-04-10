//TODO Refactoring
package persistence;

import java.io.File;

public class FileHandler extends FileController implements IDBManager {
	private final String path;
	
	public FileHandler(final String path) {
		this.path = path;
	}
	
	@Override
	public boolean exists() {
		File file = new File(path);
		return file.exists();
	}

	@Override
	public void createDB() {
		createFile(path);
	}

	@Override
	public Object loadDB() {
		return loadFile(path);
	}

	@Override
	public boolean saveDB(final Object database) {
		return saveFile(path, database);
	}

}

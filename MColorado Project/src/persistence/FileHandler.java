package persistence;

public class FileHandler extends FileController implements IDBManager {
	private final String path;
	
	public FileHandler(final String path) {
		this.path = path;
	}

	@Override
	public void createDB() {
		CreateFile(path);
	}

	@Override
	public Object loadDB() {
		return LoadFile(path);
	}

	@Override
	public boolean saveDB(final Object database) {
		return SaveFile(path, database);
	}

}

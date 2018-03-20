package persistence;

public interface IDBManager {

	void createDB();
	Object loadDB();
	boolean saveDB(Object database);
}
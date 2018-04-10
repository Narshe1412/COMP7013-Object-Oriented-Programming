//TODO Refactoring
package persistence;

public interface IDBManager {

	public void createDB();
	public Object loadDB();
	public boolean saveDB(Object database);
	public boolean exists();
}
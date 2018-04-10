package persistence;

/**
 * Interface to provide Creation, Deletion, Restoration operations of the data
 * to the system
 * 
 * @author Manuel Colorado
 *
 */
public interface IDBManager {

	public void createDB();

	public Object loadDB();

	public boolean saveDB(Object database);

	public boolean exists();
}
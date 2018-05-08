
package persistence;

/**
 * Interface that implements CRUD operations for the system
 * 
 * @author Manuel Colorado
 *
 * @param <T>
 *            The model Object that will be used for the operation
 */
public interface IDBOperationRepository<T> {

	boolean add(T contents);

	T getByID(int id);

	Iterable<T> getAll();

	boolean update(T contents);

	boolean remove(T contents);

}

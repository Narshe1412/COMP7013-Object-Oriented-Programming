//TODO Refactoring
package persistence;

public interface IDBOperationRepository<T> {
	
	// Implements CRUD operations
	boolean add(T contents);
	Iterable<T> getByID(int id);
	Iterable<T> getAll();
	boolean update(T contents);
	boolean remove(T contents);

}

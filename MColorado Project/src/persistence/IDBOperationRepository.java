package controller;

public interface IDBOperations {
	
	public void createDB();
	public Object loadDB();
	public boolean saveDB(Object database);

	public int add(String table, Object contents);
	public Object get(String table, int id);
	public Object getAll(String table);
	public Object update(String table, int id, Object contents);
	public int remove(String table, int id);

}

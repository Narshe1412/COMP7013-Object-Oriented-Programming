package persistence;

import java.io.Serializable;
import java.sql.SQLException;

import controller.AppController;
import controller.AppState;
import exception.PassException;
import model.Defaults;
import model.Dentist;
import model.Procedure;

public class StateLoader {
	
	private AppController controller;
	public StateLoader(AppController controller) {
		this.controller = controller;
	}
	/**
	 * Loads the database in the app system
	 * 
	 * @throws PassException
	 *             handles exceptions when loading users as the passwords have been
	 *             hashed and stored encrypted
	 * @throws SQLException
	 *             handles exception connecting to the database
	 */
	public void loadState() throws PassException, SQLException {

		/**
		 * Create default users if none exist
		 */
		if (controller.getAllDentist().isEmpty()) {
			for (Dentist d : Defaults.createDentists()) {
				controller.addDentist(d);
			}
		}
		
		if (controller.getAllProcedures().isEmpty()) {
			for (Procedure p: Defaults.createProcedures()) {
				controller.addProcedure(p);
			}
		}
	}
	
	/**
	 * Saves the Config object with the system configuration that will always be
	 * stored in a local file
	 */
	public void saveConfig() {
		Config toSave = new Config();
		IDBManager configDB = new FileHandler("config.dat");
		configDB.saveDB(toSave);
	}

	/**
	 * Loads the Config object containing the initial configuration of the system
	 * from a local file
	 */
	public void loadConfig() {
		IDBManager configDB = new FileHandler("config.dat");
		if (configDB.exists()) {
			Config toLoad = (Config) configDB.loadDB();
			toLoad.loadConfig();
		} else {
			new Config().loadConfig();
		}
	}
}

/**
 * Stores the initial configuration for the app
 * 
 * @author Manuel Colorado
 *
 */
class Config implements Serializable {
	private static final long serialVersionUID = 1L;
	Dentist defaultUser;

	public Config() {
		defaultUser = AppState.INSTANCE.getSavedUser();
	}

	public void loadConfig() {
		AppState.INSTANCE.setSavedUser(defaultUser);
	}

}


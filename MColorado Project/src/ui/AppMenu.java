package ui;

import java.io.IOException;
import controller.AppController;
import controller.AppNavigation;
import controller.AppState;
import exception.ExceptionDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * Menu bar for the application UI
 * 
 * @author Manuel Colorado
 *
 */
public class AppMenu extends MenuBar {
	/**
	 * Fields to provide access to the menu options in the different event handlers
	 * of this class
	 */
	private HomeWindow parent;
	private ProcedureManagementWindow procMng;
	private UserManagementWindow userMng;
	private PatientManagementWindow patMng;
	private AppController controller;

	/**
	 * Creates a new menu. Parent class is passed as parameter so it can call parent
	 * methods to display and refresh UI elements
	 * 
	 * @param homeWindow
	 *            the parent stage
	 */
	public AppMenu(HomeWindow homeWindow, AppController controller) {
		this.parent = homeWindow;
		this.controller = controller;
		prefWidthProperty().bind(parent.widthProperty());
		getMenus().addAll(fileMenu(), patientMenu(), reportsMenu(), adminMenu());
	}

	/**
	 * The File menu for the application
	 * 
	 * @return A Menu object with the contents of the file menu
	 */
	private Menu fileMenu() {
		Menu self = new Menu("File");

		/**
		 * Quits the application
		 */
		MenuItem quitMenu = new MenuItem("Exit");
		quitMenu.setOnAction(actionEvent -> AppNavigation.exitApp());

		self.getItems().addAll(/*loadMenu, saveMenu, new SeparatorMenuItem(),*/ quitMenu);
		return self;
	}

	/**
	 * The Patient menu for the application
	 * 
	 * @return A Menu object with the contents of the patient menu
	 */
	private Menu patientMenu() {
		Menu self = new Menu("Patient");
		MenuItem findPatientMenu = new MenuItem("Find Patient...");
		/**
		 * Loads the Patient management window
		 */
		findPatientMenu.setOnAction(event -> loadPatientWindow());

		self.getItems().addAll(findPatientMenu);
		return self;
	}

	/**
	 * The Reports menu for the application
	 * 
	 * @return A Menu object with the contents of the reports menu
	 */
	private Menu reportsMenu() {
		Menu self = new Menu("Reports");
		MenuItem patientsReportMenu = new MenuItem("Patients Full Report");
		/** Loads the window with the full patient report for the app */
		patientsReportMenu.setOnAction(event -> {
			ReportPatientWindow win = new ReportPatientWindow(controller);
			win.show();
		});

		MenuItem appReportMenu = new MenuItem("Full Application Report");
		/** Loads the window with the full application report (data model) */
		appReportMenu.setOnAction(event -> {
			ReportAppWindow win = new ReportAppWindow(controller);
			win.show();
		});
		
		MenuItem debtReportMenu = new MenuItem("Unpaid Invoices Report");
		/** Loads the window with the unpaid invoices report */
		debtReportMenu.setOnAction(event -> {
			ReportDebtWindow win = new ReportDebtWindow(controller);
			win.show();
		});
		
		self.getItems().addAll(patientsReportMenu, appReportMenu, debtReportMenu);
		return self;
	}

	/**
	 * The Administration menu for the application
	 * 
	 * @return A Menu object with the contents of the Admin menu
	 */
	private Menu adminMenu() {
		Menu self = new Menu("Admin");
		
		MenuItem editProcedureMenu = new MenuItem("Manage Procedures...");
		editProcedureMenu.setOnAction(event -> loadProcedureManagement());
		
		MenuItem editStaffMenu = new MenuItem("Manage Users...");
		editStaffMenu.setOnAction(event -> loadUserManagement());
		
		MenuItem changePasswordMenu = new MenuItem("Change Password...");
		changePasswordMenu.setOnAction(event -> changePassword());
		
		MenuItem logoutMenu = new MenuItem("Change User...");
		logoutMenu.setOnAction(event -> logoutUser());

		self.getItems().addAll(changePasswordMenu, logoutMenu, new SeparatorMenuItem(), editProcedureMenu,
				editStaffMenu);
		return self;
	}

	/**
	 * Loads the User Management window. If it's already open, set focus to that window
	 */
	private void loadUserManagement() {
		if (userMng == null) {
			userMng = new UserManagementWindow(this);
			userMng.show();
		} else {
			userMng.show();
			userMng.requestFocus();
		}
	}

	/**
	 * Unloads the user management window from view and memory
	 */
	public void unloadUserManagement() {
		userMng.close();
		userMng = null;
	}

	/**
	 * Loads the Procedure Management window. If it's already open, set focus to that window
	 */
	private void loadProcedureManagement() {
		if (procMng == null) {
			procMng = new ProcedureManagementWindow(this, controller);
			procMng.show();
		} else {
			procMng.show();
			procMng.requestFocus();
		}
	}

	/**
	 * Unload the procedure management window from view and memory
	 */
	public void unloadProcedureManagement() {
		procMng.close();
		procMng = null;
	}

	/**
	 * Load the patient management window. If it's already open, set focus
	 */
	private void loadPatientWindow() {
		if (patMng == null) {
			patMng = new PatientManagementWindow(this, controller);
			patMng.show();
		} else {
			patMng.show();
			patMng.requestFocus();
		}
	}

	/**
	 * Unloads the patient management window from view and memory
	 */
	public void unloadPatientWindow() {
		patMng.close();
		patMng = null;
	}

	/**
	 * Logs out current user from the system. Empties the currentUser setting from config and saves the config file.
	 * Closes and reopen the application to allow a new login
	 * @exception IOException if the config file cannot be saved
	 * @exception Exception if something went wrong closing the file or accessing the Application State
	 */
	private void logoutUser() {
		try {
			AppState.INSTANCE.setSavedUser(null);
			AppNavigation.saveConfig();
			new InitialLoadWindow();
			parent.close();
		} catch (IOException e) {
			ExceptionDialog exwin = new ExceptionDialog("I/O Error",
					"There was a problem saving application config file.", e);
			exwin.show();
		} catch (Exception e) {
			ExceptionDialog exwin = new ExceptionDialog("Critical error",
					"There was an unexpected error handling application window", e);
			exwin.show();
		}

	}

	/**
	 * Loads the change password window to allow current user to use a new password
	 */
	private void changePassword() {
		ChangePasswordWindow window = new ChangePasswordWindow();
		window.show();
	}
}

package ui;

import java.io.IOException;

import controller.AppData;
import controller.AppNavigation;
import exception.ExceptionDialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class AppMenu extends MenuBar {
	private HomeWindow parent;
	private ProcedureManagementWindow procMng;
	private UserManagementWindow userMng;
	private PatientManagementWindow patMng;

	public AppMenu(HomeWindow homeWindow) {
		this.parent = homeWindow;
		prefWidthProperty().bind(parent.widthProperty());
		getMenus().addAll(fileMenu(), patientMenu(), reportsMenu(), adminMenu());
	}

	private Menu fileMenu() {
		Menu self = new Menu("File");
		MenuItem loadMenu = new MenuItem("Load DB...");
		loadMenu.setOnAction(event -> {
			try {
				AppNavigation.loadState();
			} catch (Exception e) {

			}
		});

		MenuItem saveMenu = new MenuItem("Save DB...");
		// saveMenu.setDisable(true);
		saveMenu.setOnAction(event -> {
			AppNavigation.saveState();
		});

		MenuItem quitMenu = new MenuItem("Exit");
		quitMenu.setOnAction(actionEvent -> AppNavigation.exitApp());
		self.getItems().addAll(loadMenu, saveMenu, new SeparatorMenuItem(), quitMenu);
		return self;
	}

	private Menu patientMenu() {
		Menu self = new Menu("Patient");
		MenuItem findPatientMenu = new MenuItem("Find Patient...");
		MenuItem patientMgtMenu = new MenuItem("Manage Patients");
		patientMgtMenu.setOnAction(event -> loadPatientWindow());
		self.getItems().addAll(findPatientMenu, patientMgtMenu);
		return self;
	}



	private Menu reportsMenu() {
		Menu self = new Menu("Reports");
		MenuItem patientsReportMenu = new MenuItem("Patients Full Report");
		patientsReportMenu.setOnAction(event -> {
			ReportPatientWindow win = new ReportPatientWindow();
			win.show();
		});
		MenuItem appReportMenu = new MenuItem("Full Application Report");
		appReportMenu.setOnAction(event -> {
			ReportAppWindow win = new ReportAppWindow();
			win.show();
		});
		MenuItem debtReportMenu = new MenuItem("Unpaid Invoices Report");
		debtReportMenu.setOnAction(event -> {
			ReportDebtWindow win = new ReportDebtWindow();
			win.show();
		});
		self.getItems().addAll(patientsReportMenu, appReportMenu, debtReportMenu);
		return self;
	}

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

	private void loadUserManagement() {
		if (userMng == null) {
			userMng = new UserManagementWindow();
			userMng.show();
		} else {
			userMng.show();
			userMng.requestFocus();
		}
	}

	private void loadProcedureManagement() {
		if (procMng == null) {
			procMng = new ProcedureManagementWindow();
			procMng.show();
		} else {
			procMng.show();
			procMng.requestFocus();
		}
		
	}
	
	private void loadPatientWindow() {
		if (patMng == null) {
			patMng = new PatientManagementWindow();
			patMng.show();
		} else {
			patMng.show();
			patMng.requestFocus();
		}
	}

	private void logoutUser() {
		try {
			AppData.INSTANCE.setSavedUser(null);
			AppNavigation.saveConfig();
			new InitialLoadWindow();
			parent.close();
		} catch (IOException e) {
			ExceptionDialog exwin = new ExceptionDialog("I/O Error", "There was a problem saving application config file.", e );
			exwin.show();
		} catch (Exception e) {
			ExceptionDialog exwin = new ExceptionDialog("Critical error", "There was an unexpected error handling application window", e);
			exwin.show();
		}

	}

	private void changePassword() {
		ChangePasswordWindow window = new ChangePasswordWindow();
		window.show();
	}
}

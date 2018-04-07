package ui;

import controller.AppData;
import controller.AppNavigation;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class AppMenu extends MenuBar {
	HomeWindow parent;
	ProcedureManagementWindow procMng;
	UserManagementWindow userMng;

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
		self.getItems().addAll(findPatientMenu);
		return self;
	}

	private Menu reportsMenu() {
		Menu self = new Menu("Reports");
		MenuItem patientsReportMenu = new MenuItem("Application Report");
		patientsReportMenu.setOnAction(event -> {
			ReportAppWindow win = new ReportAppWindow();
			win.show();
		});
		MenuItem debtReportMenu = new MenuItem("Unpaid Invoices Report");
		debtReportMenu.setOnAction(event -> {
			ReportDebtWindow win = new ReportDebtWindow();
			win.show();
		});
		self.getItems().addAll(patientsReportMenu, debtReportMenu);
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

	private void logoutUser() {
		try {
			AppData.INSTANCE.setSavedUser(null);
			AppNavigation.saveConfig();
			InitialLoadWindow loader = new InitialLoadWindow();
			parent.close();
		} catch (Exception e) {
			// TODO EXCEPTION
			e.printStackTrace();
		}

	}

	private void changePassword() {
		ChangePasswordWindow window = new ChangePasswordWindow();
		window.show();
	}
}

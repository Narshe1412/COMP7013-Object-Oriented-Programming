package ui;

import controller.AppNavigation;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class AppMenu extends MenuBar{
	HomeWindow parent;
	
	public AppMenu(HomeWindow homeWindow) {
		this.parent = homeWindow;
		//prefWidthProperty().bind(Controller.getInstance().getStage().widthProperty());
	    //root.setTop(menuBar)
		getMenus().addAll(
				fileMenu(),
				patientMenu(),
				reportsMenu(),
				adminMenu());
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
		saveMenu.setDisable(true);
		
		MenuItem quitMenu = new MenuItem("Exit");
		quitMenu.setOnAction(actionEvent -> Platform.exit());
/*
 	    tabMenuItem.setOnAction(actionEvent -> new MyTabPane()); //or get a singleton instance of my tab pane?
	    borderMenuItem.setOnAction(actionEvent -> new MySplitPane()); //or get a singleton instance of my tab pane?
	    exitMenuItem.setOnAction(actionEvent -> Platform.exit());
*/
		self.getItems().addAll(
				loadMenu,
				saveMenu,
				new SeparatorMenuItem(),
				quitMenu);
		return self;
	}
	
	private Menu patientMenu() {
		Menu self = new Menu("Patient");
		MenuItem findPatientMenu = new MenuItem("Find Patient...");
/*
 	    tabMenuItem.setOnAction(actionEvent -> new MyTabPane()); //or get a singleton instance of my tab pane?
	    borderMenuItem.setOnAction(actionEvent -> new MySplitPane()); //or get a singleton instance of my tab pane?
	    exitMenuItem.setOnAction(actionEvent -> Platform.exit());
*/
		self.getItems().addAll(
				findPatientMenu);
		return self;
	}
	
	private Menu reportsMenu() {
		Menu self = new Menu("Reports");
		MenuItem patientsReportMenu = new MenuItem("Application Report");
		patientsReportMenu.setOnAction(event -> {
			ReportPatientsWindow win = new ReportPatientsWindow();
			win.show();
		});
		MenuItem debtReportMenu = new MenuItem("Unpaid Invoices Report");
		debtReportMenu.setOnAction(event -> {
			ReportDebtWindow win = new ReportDebtWindow();
			win.show();
		});
/*
 	    tabMenuItem.setOnAction(actionEvent -> new MyTabPane()); //or get a singleton instance of my tab pane?
	    borderMenuItem.setOnAction(actionEvent -> new MySplitPane()); //or get a singleton instance of my tab pane?
	    exitMenuItem.setOnAction(actionEvent -> Platform.exit());
*/
		self.getItems().addAll(
				patientsReportMenu,
				debtReportMenu);
		return self;
	}
	
	private Menu adminMenu() {
		Menu self = new Menu("Admin");
		MenuItem editProcedureMenu = new MenuItem("Manage Procedures...");
		MenuItem editStaffMenu = new MenuItem("Manage Staff...");
		MenuItem changePasswordMenu = new MenuItem("Change Password...");
/*
 	    tabMenuItem.setOnAction(actionEvent -> new MyTabPane()); //or get a singleton instance of my tab pane?
	    borderMenuItem.setOnAction(actionEvent -> new MySplitPane()); //or get a singleton instance of my tab pane?
	    exitMenuItem.setOnAction(actionEvent -> Platform.exit());
*/
		self.getItems().addAll(
				changePasswordMenu,
				new SeparatorMenuItem(),
				editProcedureMenu,
				editStaffMenu);
		return self;
	}
}

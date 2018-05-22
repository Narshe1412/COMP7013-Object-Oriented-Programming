package ui;

import java.util.ArrayList;
import java.util.List;

import controller.AppController;
import controller.AppNavigation;
import controller.AppState;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Invoice;

/**
 * Main window for the application. Creates a BorderPane that includes: <br>
 * A menu at the top <br>
 * A tabbed window in the middle <br>
 * A status bar at the bottom
 * 
 * @author Manuel Colorado
 * @version 1.0
 */
public class HomeWindow extends Stage implements ReloadableNode {

	private TabPane tabs;
	private TabMain mainPatient;
	private AppNavigation navigator;

	public HomeWindow(AppController controller, AppNavigation navigator) {
		this.navigator = navigator;
		AppNavigation.setMainWindow(this);
		mainPatient = new TabMain(this, controller);

		BorderPane root = new BorderPane();

		tabs = new TabPane();
		addNewTab(mainPatient);
		// Refreshes the UI every time the user switches Tabs.
		tabs.getSelectionModel().selectedIndexProperty().addListener((ov, oldTab, newTab) -> {
			if (oldTab != null && newTab.intValue() == 0) {
				mainPatient.refreshUI();
			}
		});
		root.setCenter(tabs);

		AppMenu menuBar = new AppMenu(this, controller, navigator);
		menuBar.prefWidthProperty().bind(root.widthProperty());
		root.setTop(menuBar);

		// Set up a simple status bar with the name of the user
		root.setBottom(new Label("Logged as user: " + AppState.INSTANCE.getCurrentUser().getUsername()));

		Scene scene = new Scene(root, 640, 480);
		setScene(scene);
		setTitle("Boca bites");
		getIcons().add(new Image("/assets/smile.png"));
		setResizable(false);

		// Handles the close event when the window is being closed
		setOnCloseRequest(event -> onClose(event));

	};

	/**
	 * Catches the close event to provide custom behavior before closing the
	 * application
	 * 
	 * @param event
	 *            the setOnCloseRequest event passed by lambda
	 */
	private void onClose(WindowEvent event) {
		// Cancels the regular onCloseRequest event
		event.consume();
		// Uses custom close event
		navigator.exitApp();
	}

	/**
	 * Adds a new tab to the main window
	 * 
	 * @param t
	 *            A Tab node passed by parameter to be displayed in the main window
	 */
	public void addNewTab(Tab t) {
		tabs.getTabs().add(t);
		tabs.getSelectionModel().select(t);
	}

	/**
	 * Removes all the tabs from the view except the main tab (Patient)
	 */
	public void clearTabs() {
		while (tabs.getTabs().size() > 1) {
			tabs.getTabs().remove(1);
		}
	}

	/**
	 * Calls the refreshUI method for the children tabs and panes
	 */
	public void refreshUI() {
		mainPatient.refreshUI();
	}

	/**
	 * Gets a list of active invoices displayed as tabs in the main window
	 * 
	 * @return The list of active invoices
	 */
	public List<Invoice> getActiveInvoices() {
		ArrayList<Invoice> list = new ArrayList<>();
		for (Tab t : tabs.getTabs()) {
			if (t instanceof TabInvoice) {
				list.add(((TabInvoice) t).getActiveInvoice());
			}
		}
		return list;
	}

	/**
	 * Forces the focus on the Tab that contains an invoice passed by parameter
	 * 
	 * @param i
	 *            An Invoice object that needs to be displayed to the user
	 */
	public void setActiveInvoiceTab(Invoice i) {
		for (Tab t : tabs.getTabs()) {
			if (t instanceof TabInvoice) {
				if (((TabInvoice) t).getActiveInvoice() == i) {
					tabs.getSelectionModel().select(t);
				}
			}
		}
	}
}

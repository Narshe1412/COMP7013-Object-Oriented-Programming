package ui;

import java.util.ArrayList;
import java.util.List;

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

public class HomeWindow extends Stage implements ReloadableNode{
	
	private TabPane tabs;
	private TabMain mainPatient;
	
	public HomeWindow() {
		mainPatient = new TabMain(this);
		
		BorderPane root = new BorderPane();
		
		tabs = new TabPane();
		addNewTab(mainPatient);
		tabs.getSelectionModel().selectedIndexProperty().addListener((ov, oldTab, newTab) -> {
	        if (oldTab != null && newTab.intValue() == 0) {
	        	mainPatient.refreshUI();
	        }
		});
		root.setCenter(tabs);
		
		AppMenu menuBar = new AppMenu(this);
		menuBar.prefWidthProperty().bind(root.widthProperty());
	    root.setTop(menuBar);
		/*MenuBar menuBar = new MenuBar();
	    menuBar.prefWidthProperty().bind(Controller.getInstance().getStage().widthProperty());
	    root.setTop(menuBar);
*/

	    
	    
	    root.setBottom(new Label("Logged as user: " + AppState.INSTANCE.getCurrentUser().getUsername()));

		Scene scene = new Scene(root, 640, 480);
		setScene(scene);
		setTitle("Boca bites�");
		getIcons().add(new Image("/assets/smile.png"));
		setResizable(false);
		
		setOnHiding(event -> onClose(event));
		
	};
	
	private Object onClose(WindowEvent event) {
		AppNavigation.saveConfig();
		return null;
	}

	public void addNewTab(Tab t) {
		tabs.getTabs().add(t);
		tabs.getSelectionModel().select(t);
	}
	
	public void clearTabs() {
		while (tabs.getTabs().size() > 1) {
			tabs.getTabs().remove(1);
		}
	}
	
	public void refreshUI() {
		mainPatient.refreshUI();
	}
	
	public List<Invoice> getActiveInvoices() {
		ArrayList<Invoice> list = new ArrayList<>();
		for (Tab t: tabs.getTabs()) {
			if (t instanceof TabInvoice) {
				list.add(((TabInvoice) t).getActiveInvoice());
			}
		}
		return list;
	}

	public void setActiveInvoiceTab(Invoice i) {
		for (Tab t: tabs.getTabs()) {
			if (t instanceof TabInvoice) {
				if (((TabInvoice) t).getActiveInvoice() == i) {
					tabs.getSelectionModel().select(t);
				}
			}
		}	
	}
}

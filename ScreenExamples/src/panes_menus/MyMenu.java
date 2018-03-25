package panes_menus;

import Scenes.MyScene;
import controller.Controller;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class MyMenu {

	public MyMenu(){
		BorderPane root = new BorderPane();
	    MyScene scene = new MyScene(root, Color.WHITE);

	    MenuBar menuBar = new MenuBar();
	    menuBar.prefWidthProperty().bind(Controller.getInstance().getStage().widthProperty());
	    root.setTop(menuBar);

	    // File menu - new, save, exit
	    Menu windowMenu = new Menu("Windows");
	    MenuItem tabMenuItem = new MenuItem("Tab Window");
	    MenuItem borderMenuItem = new MenuItem("Multi Window");
	    MenuItem exitMenuItem = new MenuItem("Exit");
	    tabMenuItem.setOnAction(actionEvent -> new MyTabPane()); //or get a singleton instance of my tab pane?
	    borderMenuItem.setOnAction(actionEvent -> new MySplitPane()); //or get a singleton instance of my tab pane?
	    exitMenuItem.setOnAction(actionEvent -> Platform.exit());

	    windowMenu.getItems().addAll(tabMenuItem, borderMenuItem,
	        new SeparatorMenuItem(), exitMenuItem);
	    
	    menuBar.getMenus().addAll(windowMenu);
        Controller.getInstance().setScene(scene);
	}
}

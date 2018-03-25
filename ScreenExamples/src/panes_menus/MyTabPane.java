package panes_menus;

import Scenes.MyScene;
import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MyTabPane extends TabPane{
	
	public MyTabPane(){
		Group root = new Group();
        MyScene scene = new MyScene(root, Color.GREY);

        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();
         
        for (int i = 0; i < 5; i++) {
        	Button menuButton = new Button("Menu Window");
            menuButton.setOnAction(actionEvent -> new MyMenu()); //or get a singleton instance of my tab pane?
            Button multiButton = new Button("Multi Window");
            multiButton.setOnAction(actionEvent -> new MySplitPane()); //or get a singleton instance of my tab pane?
            Tab tab = new Tab();
            tab.setText("Tab" + i);
            HBox hbox = new HBox();
            hbox.getChildren().addAll(new Label("Tab" + i),menuButton,multiButton);
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        Controller.getInstance().setScene(scene);
	}
}

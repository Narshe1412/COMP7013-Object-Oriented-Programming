package menus;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SliderMenu extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menus");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250, Color.WHITE);
        
        MenuBar menuBar = new MenuBar();
        
        Menu menu = new Menu("File");
        menu.getItems().add(new MenuItem("New"));
        menu.getItems().add(new MenuItem("Save"));
        menu.getItems().add(new SeparatorMenuItem());
        menu.getItems().add(new MenuItem("Exit"));

        CustomMenuItem customMenuItem = new CustomMenuItem(new Slider());
        customMenuItem.setHideOnClick(false);
        menu.getItems().add(customMenuItem);
        
        menuBar.getMenus().add(menu);
        
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        
        root.getChildren().add(menuBar); 
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
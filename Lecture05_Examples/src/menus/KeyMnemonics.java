package menus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KeyMnemonics extends Application {

  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root, 300, 250, Color.WHITE);

    MenuBar menuBar = new MenuBar();
    menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
    root.setTop(menuBar);

    Menu fileMenu = new Menu("_File");
    fileMenu.setMnemonicParsing(true);
    MenuItem exitMenuItem = new MenuItem("Exit");
    fileMenu.getItems().add(exitMenuItem);
    exitMenuItem.setOnAction(actionEvent -> Platform.exit());

    menuBar.getMenus().addAll(fileMenu);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
  public static void main(String[] args) {
    launch(args);
  }
}
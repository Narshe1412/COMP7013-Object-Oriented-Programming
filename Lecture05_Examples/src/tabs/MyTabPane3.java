package tabs;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MyTabPane3 extends Application {

  @Override
  public void start(Stage stage) {
    final Group group = new Group();
    Scene scene = new Scene(group, 300, 150);
    stage.setScene(scene);
    stage.setTitle("Sample");

    TabPane tabPane = new TabPane();
    Tab tab = new Tab();
    tab.setText("new tab");
    tab.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
    tabPane.getTabs().add(tab);
    tabPane.getTabs().add(new Tab());
    
    group.getChildren().add(tabPane);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

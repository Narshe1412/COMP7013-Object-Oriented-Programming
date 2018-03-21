package TextAreaDemo;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public class TextAreaDemo extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    // Declare and create a description pane
    DescriptionPane descriptionPane = new DescriptionPane();
    String path = getClass().getResource("/").toExternalForm();
	path = path.substring(0, path.length() - 4);
	System.out.println(path);
    // Set title, text and image in the description pane
    descriptionPane.setTitle("Canada");
    String description = "The Canadian national flag ...";
    descriptionPane.setImageView(new ImageView(path+"image/ca.gif"));
    descriptionPane.setDescription(description);

    // Create a scene and place it in the stage
    Scene scene = new Scene(descriptionPane, 450, 200);
    primaryStage.setTitle("TextAreaDemo"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}

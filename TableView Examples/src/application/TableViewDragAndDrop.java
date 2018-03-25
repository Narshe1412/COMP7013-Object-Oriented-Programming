package application;

import java.util.function.Function;

import classes.DragAndDropPerson;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableViewDragAndDrop extends Application {

    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

    @Override
    public void start(Stage primaryStage) {
        TableView<DragAndDropPerson> tableView = new TableView<>();
        tableView.getColumns().add(createCol("First Name", DragAndDropPerson::firstNameProperty, 150));
        tableView.getColumns().add(createCol("Last Name", DragAndDropPerson::lastNameProperty, 150));
        tableView.getColumns().add(createCol("Email", DragAndDropPerson::emailProperty, 200));

        tableView.getItems().addAll(
            new DragAndDropPerson("Jacob", "Smith", "jacob.smith@example.com"),
            new DragAndDropPerson("Isabella", "Johnson", "isabella.johnson@example.com"),
            new DragAndDropPerson("Ethan", "Williams", "ethan.williams@example.com"),
            new DragAndDropPerson("Emma", "Jones", "emma.jones@example.com"),
            new DragAndDropPerson("Michael", "Brown", "michael.brown@example.com")
        );

        tableView.setRowFactory(tv -> {
            TableRow<DragAndDropPerson> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    DragAndDropPerson draggedPerson = tableView.getItems().remove(draggedIndex);

                    int dropIndex ; 

                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tableView.getItems().add(dropIndex, draggedPerson);

                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });

            return row ;
        });


        Scene scene = new Scene(new BorderPane(tableView), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableColumn<DragAndDropPerson, String> createCol(String title, 
            Function<DragAndDropPerson, ObservableValue<String>> mapper, double size) {

        TableColumn<DragAndDropPerson, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> mapper.apply(cellData.getValue()));
        col.setPrefWidth(size);

        return col ;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

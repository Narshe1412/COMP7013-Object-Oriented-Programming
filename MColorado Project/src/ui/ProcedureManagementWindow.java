package ui;

import controller.AppData;
import controller.AppState;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Procedure;

public class ProcedureManagementWindow extends Stage{
	
	private ObservableList<Procedure> procList;
	private TableView<Procedure> table;

	public ProcedureManagementWindow() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		
		table = new TableView<Procedure>();
		
		procList = FXCollections.observableArrayList(AppData.INSTANCE.getProcedureList());
		for (Procedure proc : procList) {
			if (proc.isDisabled()) {
				procList.remove(proc);
			}
		}
		table.setItems(procList);
		
		TableColumn<Procedure, Integer> colID = new TableColumn<Procedure, Integer>("Id");
		colID.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProcID()).asObject());

		TableColumn<Procedure, Double> colAmount = new TableColumn<Procedure, Double>("Amount (€)");
		colAmount.setCellValueFactory(data -> data.getValue().getProcCost().asObject());
		colAmount.setMinWidth(120);
		TableColumn<Procedure, String> colName = new TableColumn<Procedure, String>("Type of Procedure");
		colName.setCellValueFactory(data -> data.getValue().getProcName());
		colName.setMinWidth(200);
		
		table.getColumns().add(colID);
		table.getColumns().add(colName);
		table.getColumns().add(colAmount);
		
		root.setCenter(table);
		
		VBox buttons = new VBox(10);
		buttons.setPadding(new Insets(10));
		Button btnAdd = new Button("Add");
		btnAdd.setOnAction(event -> addProcedure());
		Button btnEdit = new Button("Edit");
		btnEdit.setOnAction(event -> editProcedure());
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(event -> deleteProcedure());
		buttons.getChildren().addAll(btnAdd, btnEdit, btnDelete);
		
		root.setRight(buttons);
		
		Scene scene = new Scene(root, 480, 600);
		setScene(scene);
		setTitle("Procedure Management");
		getIcons().add(new Image("/assets/smile.png"));
	}

	private void deleteProcedure() {
		Procedure p = table.getSelectionModel().getSelectedItem();
		if (p != null) {
			AppData.INSTANCE.getProcedureList().get(p.getProcID()).setDisabled(true);
			procList.remove(p);
			AppState.INSTANCE.setModified(true);   
		}
		
	}

	private void editProcedure() {
		Procedure p = table.getSelectionModel().getSelectedItem();
		if ( p != null) {
			int row = procList.indexOf(p);
			int id = p.getProcID();
			Procedure toEdit = AppData.INSTANCE.getProcedureList().get(id);
			ProcedureDialog dialog = new ProcedureDialog(toEdit);
			procList.set(row, dialog.getEdit());
		}
		AppState.INSTANCE.setModified(true);
	}

	private void addProcedure() {
		ProcedureDialog dialog = new ProcedureDialog(null);
		Procedure newProc = dialog.getEdit();
		procList.add(newProc);
		AppState.INSTANCE.setModified(true);
	}
}

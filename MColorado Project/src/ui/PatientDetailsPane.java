package ui;

import controller.AppData;
import controller.AppState;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Patient;

public class PatientDetailsPane extends Pane {
	
	private Label lblName;
	private TextField txtName;
	private Label lblAddress;
	private TextField txtAddress;
	private Label lblPhone;
	private TextField txtPhone;
	private Label lblId;
	private TextField txtId;
	private Button btnSave;
	private Button btnNew;
	private Button btnEdit;
	private Button btnCancel;
	
	public PatientDetailsPane() {
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(10);
		root.setVgap(10);
		root.setPadding(new Insets(25, 25, 25, 25));
		
		/**
		 * Title
		 */
		Text scenetitle = new Text("Patient Details");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		
		lblName = new Label("Name:");
		txtName = new TextField();
				
		lblAddress = new Label("Address:");
		txtAddress = new TextField();
		
		lblPhone = new Label("Telephone:");
		txtPhone = new TextField();
		
		lblId = new Label("Client id:");
		txtId = new TextField();
		txtId.setPrefWidth(50);
		txtId.setDisable(true);
		
		btnEdit = new Button("Edit");
		btnEdit.setOnAction(e -> enableAll());
		btnSave = new Button("Save");
		btnSave.setOnAction(event -> {
			if (AppState.INSTANCE.getCurrentPatient() == null) {
				Patient p = AppData.INSTANCE.getPatientList().add(getTxtName(), getTxtAddress(), getTxtPhone());
				AppState.INSTANCE.setCurrentPatient(p);
			} else {
				AppData.INSTANCE.getPatientList().updateById(getTxtId(), getTxtName(), getTxtAddress(), getTxtPhone());
			}
			refresh();
		});
		btnNew = new Button("New");
		btnNew.setOnAction(e -> {
			enableAll();
			clearPatientDetails();
			AppState.INSTANCE.setPreviousPatient(AppState.INSTANCE.getCurrentPatient());
			AppState.INSTANCE.setCurrentPatient(null); 
			//TODO show btnCancel instead of btnNew
			txtName.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!oldValue.equals(newValue) && !getTxtName().equalsIgnoreCase(""))
			    btnSave.setDisable(false);
			}); 

		});
		btnCancel = new Button("Cancel"); // Hidden by default
		
		root.add(scenetitle, 0, 0, 2, 1);
		root.add(lblId, 2, 0);
		root.add(txtId, 3, 0);
		root.add(new Label(), 0 , 1, 4, 1);
		root.add(lblName, 0, 2);
		root.add(txtName, 1, 2, 3, 1);
		root.add(lblAddress, 0, 3);
		root.add(txtAddress, 1, 3, 3, 1);
		root.add(lblPhone, 0, 4);
		root.add(txtPhone, 1, 4);
		//root.add(new Label(), 0, 5, 4, 1);
		root.add(btnEdit, 1, 6);
		GridPane.setHalignment(btnEdit, HPos.RIGHT);
		root.add(btnSave, 2, 6);
		GridPane.setHalignment(btnSave, HPos.RIGHT);
		root.add(btnNew, 3, 6);		
		GridPane.setHalignment(btnNew, HPos.RIGHT);
		
		refresh();
		this.getChildren().add(root);
	}
	
	private void refresh() {
		disableAll();		
		loadPatientDetails(AppState.INSTANCE.getCurrentPatient());
	}

	private void enableAll() {
		txtName.setDisable(false);
		txtAddress.setDisable(false);
		txtPhone.setDisable(false);
		btnEdit.setDisable(true);
	}
	
	private void disableAll() {
		txtName.setDisable(true);
		txtAddress.setDisable(true);
		txtPhone.setDisable(true);
		btnSave.setDisable(true);
		btnEdit.setDisable(false);
	}
	
	public void loadPatientDetails(Patient p) {
		setTxtId(p.getPatientNo());
		setTxtName(p.getName());
		setTxtAddress(p.getAddress());
		setTxtPhone(p.getPhone());
	}
	
	public void clearPatientDetails() {
		setTxtId(-1);
		setTxtName("");
		setTxtAddress("");
		setTxtPhone("");
	}
	
	public void setTxtName(String pName) {
		txtName.setText(pName);
	}
	
	public String getTxtName() {
		return txtName.getText();
	}
	
	public void setTxtAddress(String pAddress) {
		txtAddress.setText(pAddress);
	}
	
	public String getTxtAddress() {
		return txtAddress.getText();
	}
	
	public void setTxtPhone(String pPhone) {
		txtPhone.setText(pPhone);
	}
	
	public String getTxtPhone() {
		return txtPhone.getText();
	}
	
	public void setTxtId(int id) {
		if(id < 0) {
			txtId.setText("");
		} else {
			txtId.setText(id + "");
		}
	}
	
	public int getTxtId() {
		return Integer.parseInt(txtId.getText());
	}
}

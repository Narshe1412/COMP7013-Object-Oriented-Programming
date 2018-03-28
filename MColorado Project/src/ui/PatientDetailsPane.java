package ui;

import controller.AppData;
import controller.AppNavigation;
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
		setMinWidth(385);
		setMaxWidth(385);

		/**
		 * Title
		 */
		Text scenetitle = new Text("Patient Details");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		/**
		 * Client ID field - Read only
		 */
		lblId = new Label("Client id:");
		txtId = new TextField();
		txtId.setPrefWidth(50);
		txtId.setDisable(true);

		/**
		 * Patient details
		 */
		lblName = new Label("Name:");
		txtName = new TextField();
		lblAddress = new Label("Address:");
		txtAddress = new TextField();
		lblPhone = new Label("Telephone:");
		txtPhone = new TextField();

		/**
		 * Edit button Workflow: Allows edit all the current patient details 1. When
		 * edit is pressed all patient details fields are enabled 2. New button is
		 * disabled in edit mode 3. Cancel button is enabled in edit mode
		 */
		btnEdit = new Button("Edit");
		btnEdit.setOnAction(e -> {
			enableAll();
			btnNew.setVisible(false);
			btnCancel.setVisible(true);
		});
		/**
		 * Save button Workflow: Pressing save checks if you are in Edit mode or New
		 * mode 1. If new mode, it adds current details to the patient list, and sets
		 * that as current patient If edit mode, it edits current patient from the list,
		 * does not modify state 2. Cancel button is no longer active 3. New Button is
		 * back to active 4. Refreshes the patient details
		 */
		btnSave = new Button("Save");
		btnSave.setOnAction(event -> {
			if (AppState.INSTANCE.getCurrentPatient() == null) {
				Patient p = AppData.INSTANCE.getPatientList().add(getTxtName(), getTxtAddress(), getTxtPhone());
				AppNavigation.updatePatient(p);
			} else {
				AppData.INSTANCE.getPatientList().updateById(getTxtId(), getTxtName(), getTxtAddress(), getTxtPhone());
			}
			btnCancel.setVisible(false);
			btnNew.setVisible(true);
			refresh();
		});
		/**
		 * New button Workflow: Clicking the new button puts the UI in New mode 1.
		 * Enables all the fields and clears the text from them 2. Saves the pointer of
		 * the current patient to the State 3. Empties the current patient from the
		 * state 4. New button is disabled from New Mode 5. Cancel button is enabled in
		 * New Mode
		 */
		btnNew = new Button("New");
		btnNew.setOnAction(e -> {
			enableAll();
			clearPatientDetails();
			AppNavigation.updatePatient(null);
			btnNew.setVisible(false);
			btnCancel.setVisible(true);

		});
		/**
		 * Cancel button Workflow: Cancel button is hidden unless in New or Edit mode 1.
		 * Checks if we are in New mode by checking the current patient from the state
		 * If empty, we are in new mode. By cancelling we get the backup from the state
		 * and empty the backup 2. In Edit mode, we don't have to manage state.
		 * Refreshing the UI will cancel all edits 3. Cancel button is disabled after
		 * the button is pressed 4. New and Edit button are back enabled 5. We refresh
		 * the UI
		 */
		btnCancel = new Button("Cancel");
		btnCancel.setVisible(false);
		btnCancel.setOnAction(e -> {
			if (AppState.INSTANCE.getCurrentPatient() == null) {
				AppNavigation.updatePatient(AppState.INSTANCE.getPreviousPatient());
				AppState.INSTANCE.setPreviousPatient(null);
			}
			btnCancel.setVisible(false);
			btnNew.setVisible(true);
			btnSave.setDisable(true);
			refresh();
		});

		/**
		 * UI Initial Setup
		 */
		root.add(scenetitle, 0, 0, 2, 1);
		root.add(lblId, 2, 0);
		root.add(txtId, 3, 0);
		root.add(new Label(), 0, 1, 4, 1);
		root.add(lblName, 0, 2);
		root.add(txtName, 1, 2, 3, 1);
		root.add(lblAddress, 0, 3);
		root.add(txtAddress, 1, 3, 3, 1);
		root.add(lblPhone, 0, 4);
		root.add(txtPhone, 1, 4);
		// root.add(new Label(), 0, 5, 4, 1);
		root.add(btnEdit, 1, 6);
		GridPane.setHalignment(btnEdit, HPos.RIGHT);
		root.add(btnSave, 2, 6);
		GridPane.setHalignment(btnSave, HPos.RIGHT);
		root.add(btnNew, 3, 6);
		root.add(btnCancel, 3, 6);
		GridPane.setHalignment(btnNew, HPos.RIGHT);
		GridPane.setHalignment(btnCancel, HPos.RIGHT);

		refresh();
		this.getChildren().add(root);
	}

	/**
	 * Refreshes the UI with the details from the app state
	 */
	public void refresh() {
		disableAll();
		loadPatientDetails(AppState.INSTANCE.getCurrentPatient());
	}

	/**
	 * Enables all the patient details to be able to be edited
	 */
	private void enableAll() {
		txtName.setDisable(false);
		txtAddress.setDisable(false);
		txtPhone.setDisable(false);
		btnEdit.setDisable(true);
		btnSave.setDisable(false);
	}

	/**
	 * Disables all the patient fields so they can no longer be edited
	 */
	private void disableAll() {
		txtName.setDisable(true);
		txtAddress.setDisable(true);
		txtPhone.setDisable(true);
		btnSave.setDisable(true);
		btnEdit.setDisable(false);
	}

	/**
	 * Loads Patient details passed by parameter into the UI
	 * 
	 * @param p
	 *            A Patient object
	 */
	public void loadPatientDetails(Patient p) {
		setTxtId(p.getPatientNo());
		setTxtName(p.getName());
		setTxtAddress(p.getAddress());
		setTxtPhone(p.getPhone());
	}

	/**
	 * Empties the UI from all patient details
	 */
	public void clearPatientDetails() {
		setTxtId(-1);
		setTxtName("");
		setTxtAddress("");
		setTxtPhone("");
	}

	/**
	 * Sets up the text field for the name
	 * 
	 * @param pName
	 *            A string with a patient name
	 */
	private void setTxtName(String pName) {
		txtName.setText(pName);
	}

	/**
	 * Gets the actual value of the Name text box
	 * 
	 * @return a String with the name of the patient as typed in the text box
	 */
	public String getTxtName() {
		return txtName.getText();
	}

	/**
	 * Sets up the text field designated for the address of the patient
	 * 
	 * @param pAddress
	 *            A String with the address of the patient
	 */
	private void setTxtAddress(String pAddress) {
		txtAddress.setText(pAddress);
	}

	/**
	 * Gets the actual value from the Address text box
	 * 
	 * @return a String with the address of the patient as typed in the text box
	 */
	public String getTxtAddress() {
		return txtAddress.getText();
	}

	/**
	 * Sets up the text field designated for the phone of the patient
	 * 
	 * @param pPhone
	 *            a String containing the phone number of the patient
	 */
	private void setTxtPhone(String pPhone) {
		txtPhone.setText(pPhone);
	}

	/**
	 * Gets the actual value from the Phone text box
	 * 
	 * @return a string value containing the phone of the patient as typed in the
	 *         text box
	 */
	public String getTxtPhone() {
		return txtPhone.getText();
	}

	/**
	 * Sets up the ID text field from the state. If the ID is not a valid value, it
	 * empties the box
	 * 
	 * @param id
	 *            an integer that designates the ID from the customer
	 */
	private void setTxtId(int id) {
		if (id < 0) {
			txtId.setText("");
		} else {
			txtId.setText(id + "");
		}
	}

	/**
	 * Obtains the ID value as it appears in the ID text box
	 * 
	 * @return an integer value that is contained in the ID text box
	 */
	public int getTxtId() {
		return Integer.parseInt(txtId.getText());
	}
}

package ui;

import java.util.Optional;

import controller.AppController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Procedure;

/**
 * Creates a Dialog JavaFX window to get the information to create and edit
 * Procedures in the system
 * 
 * @author Manuel Colorado
 *
 */
public class ProcedureDialog extends Dialog<Procedure> {

	TextField procName;
	TextField procCost;
	Procedure toEdit;
	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param toEdit
	 *            a Procedure object passed by parameter that will be edited. If
	 *            null, creates a new Procedure object
	 */
	public ProcedureDialog(Procedure toEdit, AppController controller) {
		super();
		this.toEdit = toEdit;
		this.controller = controller;

		setTitle("Procedure Details");
		setHeaderText("Please enter the procedure details:");
		DialogPane dialogPane = getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		procName = new TextField();
		procCost = new TextField();

		// Add values from the Procedure that will be edited and display them on screen
		if (toEdit != null) {
			procName.setText(toEdit.getProcName().get());
			procCost.setText(toEdit.getProcCost().get() + "");
		}

		dialogPane.setContent(new VBox(10, new Label("Name of the Procedure:"), procName, new Label("Cost of the Procedure:"), procCost));
		Platform.runLater(procName::requestFocus);

		final Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
		// Event that handles how the OK button behaves
		okButton.addEventFilter(ActionEvent.ACTION, ae -> {
			String txtName = procName.getText().trim();
			String txtCost = procCost.getText().trim();
			if (!Validator.stringValidator(txtName, 2, 250) || !Validator.doubleValidator(txtCost)) {
				Validator.setStringValidation(txtName, 2, 250, procName, "Procedure name cannot be blank.");
				Validator.setDoubleValidation(txtCost, procCost, "Cost needs to be a positive number");
				ae.consume(); // Consume OK button event due to failed validation
			}
		});
		// Add dialog icon
		Stage icon = (Stage) this.getDialogPane().getScene().getWindow();
		icon.getIcons().add(new Image(this.getClass().getResource("/assets/smile.png").toString()));
	}

	/**
	 * Get the results typed on the window and returns them as a Procedure object
	 * 
	 * @return a Procedure object with the new details as typed on screen
	 */
	public Procedure getEdit() {

		setResultConverter((ButtonType button) -> {
			if (button == ButtonType.OK) {
				String txtName = procName.getText().trim();
				String txtCost = procCost.getText().trim();
				if (Validator.stringValidator(txtName, 2, 250) && Validator.doubleValidator(txtCost)) {
					return new Procedure(txtName, Double.parseDouble(txtCost));
				}
			}
			return null;
		});
		Optional<Procedure> result = showAndWait();
		result.ifPresent((Procedure results) -> {
			double amount = results.getProcCost().get();
			String desc = results.getProcName().get();
			if (toEdit == null) {
				// If we're creating a new procedure, call add
				if (controller.addProcedure(results) > 0) {
					toEdit = results;	
				}
				 
			} else {
				// If we're editing a procedure, change its details
				toEdit.setProcCost(amount);
				toEdit.setProcName(desc);
			}
		});
		return toEdit;
	}
}
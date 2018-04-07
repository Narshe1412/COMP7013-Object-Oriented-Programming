package ui;

import java.util.Optional;

import controller.AppData;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Procedure;

public class ProcedureDialog extends Dialog<Procedure> {

	TextField procName;
	TextField procCost;
	Procedure toEdit;

	public ProcedureDialog(Procedure toEdit) {
		super();
		this.toEdit = toEdit;

		setTitle("Procedure Details");
		setHeaderText("Please enter the procedure details:");
		DialogPane dialogPane = getDialogPane();
		dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		procName = new TextField();
		procCost = new TextField();

		if (toEdit != null) {
			procName.setText(toEdit.getProcName().get());
			procCost.setText(toEdit.getProcCost().get() + "");
		}

		dialogPane.setContent(new VBox(10, procName, procCost));
		Platform.runLater(procName::requestFocus);
	}
	
	public Procedure getEdit() {
		setResultConverter((ButtonType button) -> {
			if (button == ButtonType.OK) {
				return new Procedure(procName.getText(), Double.parseDouble(procCost.getText().trim()));
			}
			return null;
		});
		Optional<Procedure> result = showAndWait();
		result.ifPresent((Procedure results) -> {
			double amount = results.getProcCost().get();
			String desc = results.getProcName().get();
			if (toEdit == null) {
				toEdit = AppData.INSTANCE.getProcedureList().addNew(desc, amount);
			} else {
				toEdit.setProcCost(amount);
				toEdit.setProcName(desc);
			}
		});
		return toEdit;
	}
}

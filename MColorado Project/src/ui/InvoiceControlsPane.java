package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.AppData;
import controller.AppNavigation;
import controller.AppState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Invoice;
import model.Procedure;

public class InvoiceControlsPane extends Pane {
	private Stage parent;

	private Button btnNew;
	private Button btnOpen;
	private Label lblAmountDue;
	private Text txtAmountDue;
	private Label lblAmountTotal;
	private Text txtAmountTotal;
	private Button btnPay;

	public InvoiceControlsPane(HomeWindow parent) {
		this.parent = parent;

		BorderPane root = new BorderPane();

		GridPane expenses = new GridPane();
		expenses.setPadding(new Insets(25, 25, 25, 0));
		expenses.setAlignment(Pos.CENTER);
		expenses.setHgap(10);
		expenses.setVgap(10);

		/**
		 * Title
		 */
		Text scenetitle = new Text("Expenses Detail");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		expenses.add(scenetitle, 0, 0, 3, 1);
		expenses.add(new Label(), 0, 1, 4, 1);
		lblAmountTotal = new Label("Total amount:");
		expenses.add(lblAmountTotal, 0, 3, 2, 1);
		txtAmountTotal = new Text();
		expenses.add(txtAmountTotal, 2, 3);
		lblAmountDue = new Label("Amount due:");
		expenses.add(lblAmountDue, 0, 4, 2, 1);
		txtAmountDue = new Text();
		expenses.add(txtAmountDue, 2, 4);
		btnPay = new Button("\nPAY\n ");
		GridPane.setValignment(btnPay, VPos.TOP);
		expenses.add(btnPay, 3, 3, 1, 2);

		root.setCenter(expenses);

		HBox btnGroup = new HBox(20);
		btnGroup.setPadding(new Insets(25, 25, 25, 0));
		btnGroup.setAlignment(Pos.CENTER);

		btnNew = new Button("New Invoice");
		btnNew.setOnAction(event -> {
			Invoice i = AppData.INSTANCE.getInvoiceList().addNew();
			AppState.INSTANCE.getCurrentPatient().addInvoice(i);
			parent.addNewTab(new TabInvoice(i));
		});
		btnOpen = new Button("Open Invoice");
		btnOpen.setOnAction(event -> {
			List<String> choices = new ArrayList<>();
			for (Invoice inv : AppState.INSTANCE.getCurrentPatient().getP_invoiceList()) {
				String invoiceDetails = inv.getInvoiceID() + "#   " + inv.getStringDate()
						+ (inv.isPaid() ? "   --PAID--" : "") 
						+ "   Total: " + inv.getInvoiceAmt() + " $";
				choices.add(invoiceDetails);
			}

			ChoiceDialog<String> dialog = new ChoiceDialog<String>(choices.get(0), choices);

			dialog.setTitle("Open");
			dialog.setHeaderText("List of Invoices");
			dialog.setContentText("Select the invoice you want to inspect: ");

			// Traditional way to get the response value.
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(invoiceSelected -> {
				int id = Integer.parseInt(invoiceSelected.substring(0, invoiceSelected.indexOf("#")));
				Invoice i = AppData.INSTANCE.getInvoiceList().get(id);
				if (!parent.getActiveInvoices().contains(i)) {
					parent.addNewTab(new TabInvoice(i));
				} else {
					parent.setActiveInvoiceTab(i);
				}
			});
		});
		btnGroup.getChildren().add(btnNew);
		btnGroup.getChildren().add(btnOpen);

		GroupBox invoiceButtons = new GroupBox();
		invoiceButtons.setText("Invoices");
		invoiceButtons.setContent(btnGroup);
		root.setBottom(btnGroup);

		getChildren().add(root);

		refresh();
	}

	public void refresh() {
		if (AppState.INSTANCE.getCurrentPatient() == null) {
			txtAmountTotal.setText("0.0 $");
			txtAmountDue.setText("0.0 $");
		} else {
			txtAmountTotal.setText(AppState.INSTANCE.getCurrentPatient().getTotalInvoiceValue() + " $");
			txtAmountDue.setText(AppState.INSTANCE.getCurrentPatient().getRemainingInvoiceValue() + " $");

		}
	}

}

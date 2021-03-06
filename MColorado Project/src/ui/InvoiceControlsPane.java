package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import controller.AppController;
import controller.AppState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
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
import model.Payment;

/**
 * Creates a pane to control the invoice details for the patient
 * 
 * @author Manuel Colorado
 *
 */
public class InvoiceControlsPane extends Pane {
	private HomeWindow parent;

	private Button btnNew;
	private Button btnOpen;
	private Label lblAmountDue;
	private Text txtAmountDue;
	private Label lblAmountTotal;
	private Text txtAmountTotal;
	private Button btnPay;

	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the reference to the parent object so this pane can call parent
	 *            methods
	 */
	public InvoiceControlsPane(HomeWindow parent, AppController controller) {
		this.controller = controller;
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

		/** Gets the total amount that remains to be paid */
		btnPay.setOnAction(event -> {
			if (AppState.INSTANCE.getCurrentPatient() != null) {
				btnPayOnClick();
			}
		});

		HBox btnGroup = new HBox(20);
		btnGroup.setPadding(new Insets(25, 25, 25, 0));
		btnGroup.setAlignment(Pos.CENTER);

		/** Creates a new invoice and adds a tab corresponding to the new invoice */
		btnNew = new Button("New Invoice");
		btnNew.setOnAction(event -> {
			if (AppState.INSTANCE.getCurrentPatient() != null) {
				btnNewOnClick();
			}
		});
		/** Opens the list of invoices and creates a new tab for the selected invoice */
		btnOpen = new Button("Open Invoice");
		btnOpen.setOnAction(event -> {
			if (AppState.INSTANCE.getCurrentPatient() != null) {
				btnOpenOnClick();
			}
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

	/**
	 * @param controller
	 */
	private void btnOpenOnClick() {
		List<String> choices = new ArrayList<>();
		for (Invoice inv : controller.getInvoicesFromPatient(AppState.INSTANCE.getCurrentPatient())) {
			String invoiceDetails = inv.getInvoiceID() + "#   " + inv.getStringDate()
					+ (inv.isPaid() ? "   --PAID--" : "") + "   Total: " + inv.getInvoiceAmt() + " $";
			choices.add(invoiceDetails);
		}
		if (choices.isEmpty()) {
			AlertDialog alert = new AlertDialog(AlertType.INFORMATION, "Information", null,
					"This patient does not have any invoices");
			alert.showAndWait();

		} else {
			loadInvoiceDialog(choices);
		}

	}

	/**
	 * @param parent
	 * @param controller
	 */
	private void btnNewOnClick() {
		Invoice invoice = new Invoice();
		controller.addInvoiceToPatient(invoice, AppState.INSTANCE.getCurrentPatient());
		parent.addNewTab(new TabInvoice(invoice, controller));
	}

	/**
	 * @param controller
	 */
	private void btnPayOnClick() {
		List<String> choices = new ArrayList<>();
		for (Invoice inv : controller.getInvoicesFromPatient(AppState.INSTANCE.getCurrentPatient())) {
			if (!inv.isPaid()) {
				String invoiceDetails = inv.getInvoiceID() + "#   " + inv.getStringDate() + "   Total pending: "
						+ inv.getInvoiceAmt() + "";
				choices.add(invoiceDetails);
			}
		}
		if (choices.isEmpty()) {
			AlertDialog alert = new AlertDialog(AlertType.INFORMATION, "Information", null,
					"This patient does not owe us any money");
			alert.showAndWait();
		} else {
			loadInvoiceDialog(choices);
		}
	}

	/**
	 * Refresh the window with the current invoice values
	 */
	public void refresh() {
		if (AppState.INSTANCE.getCurrentPatient() == null) {
			txtAmountTotal.setText("0.0");
			txtAmountDue.setText("0.0");
		} else {
			double total = 0;
			double paid = 0;
			for (Invoice i : controller.getInvoicesFromPatient(AppState.INSTANCE.getCurrentPatient())) {
				total += i.getInvoiceAmt();
				for (Payment p : controller.getPaymentsFromInvoice(i)) {
					paid += p.getPaymentAmt().get();
				}
			}
			txtAmountTotal.setText(total + "");
			txtAmountDue.setText((total - paid) + "");
		}
	}

	/**
	 * Load the list of invoices to allow the user to select the one to open
	 * 
	 * @param choices
	 *            the list of available invoices to open
	 */
	private void loadInvoiceDialog(List<String> choices) {
		ChoiceDialog<String> dialog = new ChoiceDialog<String>(choices.get(0), choices);

		dialog.setTitle("Open");
		dialog.setHeaderText("List of Invoices");
		dialog.setContentText("Select the invoice you want to inspect: ");
		// Add a custom icon.
		Stage icon = (Stage) dialog.getDialogPane().getScene().getWindow();
		icon.getIcons().add(new Image(this.getClass().getResource("/assets/smile.png").toString()));

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(invoiceSelected -> {
			int id = Integer.parseInt(invoiceSelected.substring(0, invoiceSelected.indexOf("#")));
			Invoice i = controller.getInvoiceById(id);
			if (!parent.getActiveInvoices().contains(i)) {
				parent.addNewTab(new TabInvoice(i, controller));
			} else {
				parent.setActiveInvoiceTab(i);
			}
		});
	}
}

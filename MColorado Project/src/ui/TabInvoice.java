package ui;

import controller.AppController;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import model.Invoice;
import model.Payment;
import model.Procedure;

/**
 * Creates a tab with the contents of the invoice passed by parameter
 * 
 * @author Manuel Colorado
 * @version 1.0
 */
public class TabInvoice extends Tab implements ReloadableNode {

	private InvoiceTitlePane titlePane;
	private Invoice activeInvoice;
	private AppController controller;

	/**
	 * Constructor
	 * 
	 * @param invoiceNo
	 *            gets the invoiceID from the Data Model and display the details on
	 *            the tab
	 */
	public TabInvoice(int invoiceNo, AppController controller) {
		this.controller = controller;
		createContent(controller.getInvoiceById(invoiceNo));
	}

	/**
	 * Constructor
	 * 
	 * @param i
	 *            gets an invoice passed by parameter and display the details on the
	 *            tab
	 */
	public TabInvoice(Invoice i, AppController controller) {
		this.controller = controller;
		createContent(i);
	}

	/**
	 * Create the content for the tab based on the invoice passed by parameter
	 * 
	 * @param i
	 *            An Invoice object that contains the details that will be displayed
	 *            in the window
	 */
	private void createContent(Invoice i) {
		this.activeInvoice = i;
		for (Procedure p : controller.getProceduresByInvoice(i)) {
			i.addProcedure(p);
		}
		for (Payment p : controller.getPaymentsFromInvoice(i)) {
			i.addPayment(p);
		}

		// Tab title
		setText("Invoice #" + i.getInvoiceID() + " - " + i.getStringDate());

		// Tab contents
		VBox root = new VBox();
		titlePane = new InvoiceTitlePane(i, controller);
		root.getChildren().addAll(titlePane, new InvoiceProceduresPane(i, titlePane, controller),
				new InvoicePaymentsPane(i, titlePane, controller));
		setContent(root);
	}

	/**
	 * Refreshes the content on the tab
	 */
	public void refreshUI() {
		titlePane.refresh();
	}

	/**
	 * Gets the details of the selected invoice
	 * 
	 * @return the Invoice object that is being displayed in this tab
	 */
	public Invoice getActiveInvoice() {
		return activeInvoice;
	}

}

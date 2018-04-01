package ui;

import controller.AppData;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import model.Invoice;

public class TabInvoice extends Tab{
	
	private InvoiceTitlePane titlePane;
	
	public TabInvoice(int invoiceNo) {

		createContent(AppData.INSTANCE.getInvoiceList().get(invoiceNo));
	}
	
	public TabInvoice(Invoice i) {
		createContent(i);
	}

	private void createContent(Invoice i) {
		

		setText("Invoice #" + i.getInvoiceID() + " - " + i.getStringDate());
		VBox root = new VBox();
		titlePane = new InvoiceTitlePane(i);
		root.getChildren().addAll(titlePane, new InvoiceProceduresPane(i, titlePane), new InvoicePaymentsPane(i, titlePane));
		setContent(root);
	}

}

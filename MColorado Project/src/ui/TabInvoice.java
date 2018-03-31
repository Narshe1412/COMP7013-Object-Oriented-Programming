package ui;

import controller.AppData;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import model.Invoice;

public class TabInvoice extends Tab{
	
	private InvoiceTitlePane titlePane;
	
	public TabInvoice(int invoiceNo) {
		createContent(invoiceNo);
	}
	
	private void createContent(int invoiceNo) {
		
		Invoice inv = AppData.INSTANCE.getInvoiceList().get(invoiceNo);
		setText("Invoice #" + invoiceNo + " - " + inv.getStringDate());
		VBox root = new VBox();
		titlePane = new InvoiceTitlePane(inv);
		root.getChildren().addAll(titlePane, new InvoiceProceduresPane(inv, titlePane), new InvoicePaymentsPane(inv, titlePane));
		setContent(root);
	}

}

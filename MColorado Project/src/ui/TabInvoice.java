package ui;

import controller.AppData;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Invoice;

public class TabInvoice extends Tab{
	
	public TabInvoice(int invoiceNo) {
		createContent(invoiceNo);
	}
	
	private void createContent(int invoiceNo) {
		Invoice inv = AppData.INSTANCE.getInvoiceList().get(invoiceNo);
		setText("Invoice #" + invoiceNo + " - " + inv.getStringDate());
		VBox root = new VBox();
		root.getChildren().addAll(new InvoiceTitlePane(inv), new InvoiceProceduresPane(inv), new InvoicePaymentsPane(inv));
		setContent(root);
	}

}

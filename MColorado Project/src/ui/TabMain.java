package ui;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;

/**
 * Creates a main tab window where the current selected patient details will be
 * displayed and a summary of the invoices
 * 
 * @author Manuel Colorado
 * @version 1.0
 */
class TabMain extends Tab implements ReloadableNode {

	@SuppressWarnings("unused")
	private HomeWindow parent;
	private PatientDetailsPane details;
	private InvoiceControlsPane invoices;

	/**
	 * Creates the main tab for the system
	 * 
	 * @param parent
	 *            The caller parent, so the children can use its methods
	 */
	public TabMain(HomeWindow parent) {
		this.parent = parent;
		setText("Patient Details");
		SplitPane root = new SplitPane();

		details = new PatientDetailsPane(parent);
		invoices = new InvoiceControlsPane(parent);

		root.getItems().addAll(details, invoices);
		setContent(root);
		setClosable(false);
	}

	/**
	 * Refreshes the content in this tab
	 */
	public void refreshUI() {
		details.refreshUI();
		invoices.refresh();
	}

}

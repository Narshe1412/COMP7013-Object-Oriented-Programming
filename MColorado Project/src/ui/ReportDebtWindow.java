package ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import controller.AppController;
import controller.AppData;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Invoice;
import model.Patient;
import model.Payment;

/**
 * Creates a report in the system with the list of patients that have not paid
 * any pending invoices in the past 6 months
 * 
 * @author Manuel Colorado
 *
 */
public class ReportDebtWindow extends Stage {

	private AppController controller;

	/**
	 * Constructor
	 * @param controller 
	 */
	public ReportDebtWindow(AppController controller) {
		this.controller = controller;
		BorderPane root = new BorderPane();

		TextFlow patientReport = getInvoicesReport();
		ScrollPane scrollableReport = new ScrollPane();
		scrollableReport.setPadding(new Insets(10, 10, 10, 10));
		scrollableReport.setContent(patientReport);
		root.setCenter(scrollableReport);

		Scene scene = new Scene(root, 480, 600);
		setScene(scene);
		setTitle("Unpaid Invoices Report");
		getIcons().add(new Image("/assets/smile.png"));
	}

	/**
	 * Creates a list of patients that have not made any payments in the last 6
	 * months
	 * 
	 * @return a TextFlow object with the report already constructed
	 */
	private TextFlow getInvoicesReport() {
		TextFlow result = new TextFlow();

		Text patientTitle = new Text("Patients that haven't made a payment in the last 6 months: \n");
		patientTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 18));

		String patientData = "";
		// Creates a copy of the patients to be reordered by amount pending
		List<Patient> patientList = controller.getAllPatients();
		patientList.sort((Patient a, Patient b) -> (int) (b.getRemainingInvoiceValue() - a.getRemainingInvoiceValue()));

		if (patientList.isEmpty()) {
			// Fallback if there are no patients with out of date payments in the system
			patientData += "There are no patients with pending payments on the system.\n";
		} else {
			for (Patient p : patientList) {
				// Checks if the remaining payments are greater than 0
				if (p.getRemainingInvoiceValue() > 0) {
					// Get the list of invoices
					for (Invoice i : p.getP_invoiceList()) {
						// Discard those which are paid
						if (!i.isPaid()) {
							boolean recentPayment = false;
							// From the unpaid invoices, check those who have a recent payment, and discard
							// those
							for (Payment pay : i.getIn_paymentList()) {
								Calendar cal = Calendar.getInstance(); // Get current date/month i.e 27 Feb, 2012
								cal.add(Calendar.MONTH, -6); // Set date to 6 months ago
								if (pay.getPaymentDate().after(cal.getTime())) {
									recentPayment = true;
								}
							}
							if (!recentPayment) {
								patientData += p.getName() + " \tRemaining to pay:   " + p.getRemainingInvoiceValue()
										+ " $\n";
							}
						}
					}
				}
			}
		}
		if (patientData.trim().equalsIgnoreCase("")) {
			// Fallback if there are no patients with pending payments longer than the 6
			// month
			patientData += "There are no patients with out of date pending payments on the system.\n";
		}
		Text patientDataTxt = new Text();
		patientDataTxt.setText(patientData);

		/** Report date section */
		Text txtDate = new Text("\n\nReport generated on: " + new Date() + "\n");
		txtDate.setFont(Font.font("Consolas", FontWeight.THIN, FontPosture.ITALIC, 10));
		txtDate.setTextAlignment(TextAlignment.RIGHT);

		result.getChildren().addAll(patientTitle, patientDataTxt, txtDate);
		return result;
	}
}

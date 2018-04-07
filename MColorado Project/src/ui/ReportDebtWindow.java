package ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

public class ReportDebtWindow extends Stage {

	public ReportDebtWindow() {
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

	private TextFlow getInvoicesReport() {

		TextFlow result = new TextFlow();

		Text patientTitle = new Text("Patients that haven't made a payment in the last 6 months: \n");
		patientTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 18));

		String patientData = "";

		List<Patient> patientList = AppData.INSTANCE.getPatientList().stream().collect(Collectors.toList());
		patientList.sort((Patient a, Patient b) -> (int) (b.getRemainingInvoiceValue() - a.getRemainingInvoiceValue()));

		for (Patient p : patientList) {
			if (p.getRemainingInvoiceValue() > 0) {
				for (Invoice i : p.getP_invoiceList()) {
					if (!i.isPaid()) {
						boolean recentPayment = false;
						for (Payment pay : i.getIn_paymentList()) {
							Calendar cal = Calendar.getInstance(); // Get current date/month i.e 27 Feb, 2012
							cal.add(Calendar.MONTH, -6); // Set date to 6 months ago
							if (pay.getPaymentDate().after(cal.getTime())) {
								recentPayment = true;
							}
							;
						}
						if (!recentPayment) {
							patientData += p.getName() + " \tRemaining to pay:   " + p.getRemainingInvoiceValue() + " $\n";
						}
					}
				}

			}
		}
		if (patientData.trim().equalsIgnoreCase("")) {
			patientData += "There are no patients with outdated payments on the system.\n";
		}
		Text patientDataTxt = new Text();
		patientDataTxt.setText(patientData);

		Text txtDate = new Text("\n\nReport generated on: " + new Date() + "\n");
		txtDate.setFont(Font.font("Consolas", FontWeight.THIN, FontPosture.ITALIC, 10));
		txtDate.setTextAlignment(TextAlignment.RIGHT);

		result.getChildren().addAll(patientTitle, patientDataTxt, txtDate);
		return result;
	}
}

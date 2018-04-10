//TODO Refactoring
package ui;

import java.util.Comparator;
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
import model.Procedure;

public class ReportPatientWindow extends Stage {

	public ReportPatientWindow() {
		BorderPane root = new BorderPane();

		TextFlow patientReport = getPatientReport();
		ScrollPane scrollableReport = new ScrollPane();
		scrollableReport.setPadding(new Insets(10, 10, 10, 10));
		scrollableReport.setContent(patientReport);
		root.setCenter(scrollableReport);

		Scene scene = new Scene(root, 480, 600);
		setScene(scene);
		setTitle("Application Report");
		getIcons().add(new Image("/assets/smile.png"));
	}

	private TextFlow getPatientReport() {

		TextFlow result = new TextFlow();

		Text patientTitle = new Text("List of Patients: \n");
		patientTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 20));
		patientTitle.setUnderline(true);
		result.getChildren().add(patientTitle);

		if (AppData.INSTANCE.getPatientList().isEmpty()) {
			Text empty = new Text("No patients have been registered in the system.\n");
			result.getChildren().add(empty);
		} else {
			List<Patient> patientList = AppData.INSTANCE.getPatientList().stream().collect(Collectors.toList());
			patientList.sort((Patient a, Patient b) -> a.getName().compareToIgnoreCase(b.getName()));

			Text patientDataTxt;
			Text procedureTitle;
			Text procDataTxt;
			Text paymentTitle;
			Text paymentDataTxt;
			Text invoiceTitle = new Text();

			for (Patient p : patientList) {
				String patientData = "";
				patientData += p.toString() + "\n";
				patientDataTxt = new Text(patientData);
				patientDataTxt.setFont(Font.font("Calibri", FontWeight.BLACK, 16));
				result.getChildren().addAll(new Text("\n"),patientDataTxt);

				procedureTitle = new Text("Procedures: \n");
				procedureTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 13));
				paymentTitle = new Text("List of Payments: \n");
				paymentTitle.setFont(Font.font("Calibri", FontWeight.BOLD, 13));

				String procData = "";
				String paymentData = "";

				if (p.getP_invoiceList().isEmpty()) {
					result.getChildren().add(new Text("This patient doesn't have any invoices yet.\n"));
				} else {
					for (Invoice i : p.getP_invoiceList()) {
						invoiceTitle = new Text("Invoice no. " + i.getInvoiceID() + "\n");
						invoiceTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 14));
						if (i.getIn_paymentList().isEmpty()) {
							procData += "No procedures added to this bill. \n";
						} else {
							List<Procedure> procedureList = i.getIn_procList().stream().collect(Collectors.toList());
							procedureList.sort((Procedure a, Procedure b) -> a.getProcName().get()
									.compareToIgnoreCase(b.getProcName().get()));
							for (Procedure proc : procedureList) {
								procData += proc.toString() + "\n";
							}
						}
						if (i.getIn_paymentList().isEmpty()) {
							paymentData += "There are no payments against this invoice.\n";
						} else {
							List<Payment> paymentList = i.getIn_paymentList().stream().collect(Collectors.toList());

							for (Payment payment : paymentList) {
								paymentData += payment.toString() + "\n";
							}
						}
					}
					procDataTxt = new Text(procData);
					paymentDataTxt = new Text(paymentData);
					result.getChildren().addAll(invoiceTitle, procedureTitle, procDataTxt, paymentTitle,
							paymentDataTxt);
				}
			}

		}

		Text txtDate = new Text("\n\nReport generated on: " + new Date() + "\n");
		txtDate.setFont(Font.font("Consolas", FontWeight.THIN, FontPosture.ITALIC, 10));
		txtDate.setTextAlignment(TextAlignment.RIGHT);
		result.getChildren().add(txtDate);
		return result;
	}

	class ByName implements Comparator<Patient> {
		@Override
		public int compare(Patient a, Patient b) {
			return a.getName().compareToIgnoreCase(b.getName());
		}
	}
}

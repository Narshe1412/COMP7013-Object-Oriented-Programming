package ui;

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
import model.Patient;
import model.Payment;
import model.Procedure;

/**
 * Creates a new window with a report with the summary of the system
 * 
 * @author Manuel Colorado
 *
 */
public class ReportAppWindow extends Stage {

	private AppController controller;

	/**
	 * Constructor
	 */
	public ReportAppWindow(AppController controller) {
		this.controller= controller;
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

	/**
	 * Creates the text with the report
	 * @return a TextFlow object that will be included in the window
	 */
	private TextFlow getPatientReport() {

		TextFlow result = new TextFlow();

		/** Patient Section */
		Text patientTitle = new Text("List of Patients: \n");
		patientTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 20));

		String patientData = "";

		// Creates a copy of the patients that will be reordered by name
		List<Patient> patientList = controller.getAllPatients();
		patientList.sort((Patient a, Patient b) -> a.getName().compareToIgnoreCase(b.getName()));
		
		if (patientList.isEmpty()) {
			// Fallback if no Patients are registered in the system
			patientData += "There are no patients registered on the system.\n";
		} else {
			for (Patient p : patientList) {
				patientData += p.toString() + "\n";
			}
		}
		
		Text patientDataTxt = new Text();
		patientDataTxt.setText(patientData);

		/** Procedure Section */
		Text procedureTitle = new Text("\nList of Procedures: \n");
		procedureTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 20));
		String procData = "";
		
		// Creates a copy of the list of procedures to be sorted by name
		List<Procedure> procedureList = AppData.INSTANCE.getProcedureList().stream().collect(Collectors.toList());
		procedureList
				.sort((Procedure a, Procedure b) -> a.getProcName().get().compareToIgnoreCase(b.getProcName().get()));

		if (procedureList.isEmpty()) {
			// Fallback if there are no procedures recorded in the system
			procData += "There are no procedures registered on the system. \n";	
		} else {
			for (Procedure proc : procedureList) {
				if (!proc.isDisabled()) {
					procData += proc.toString() + "\n";
				}
			}
		}	
		Text procDataTxt = new Text();
		procDataTxt.setText(procData);

		/** Payment Section */
		Text paymentTitle = new Text("\nList of Payments: \n");
		paymentTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 20));

		String paymentData = "";
		List<Payment> paymentList = AppData.INSTANCE.getPaymentList().stream().collect(Collectors.toList());

		if (paymentList.isEmpty()) {
			// Fallback if no payments have been added on the system
			paymentData += "There are no payments registered on the system. \n";
		} else {
			for (Payment payment : paymentList) {
				paymentData += payment.toString() + "\n";
			}
		}
		Text paymentDataTxt = new Text();
		paymentDataTxt.setText(paymentData);

		/** Report date footnote */
		Text txtDate = new Text("\n\nReport generated on: " + new Date() + "\n");
		txtDate.setFont(Font.font("Consolas", FontWeight.THIN, FontPosture.ITALIC, 10));
		txtDate.setTextAlignment(TextAlignment.RIGHT);

		result.getChildren().addAll(patientTitle, patientDataTxt, procedureTitle, procDataTxt, paymentTitle,
				paymentDataTxt, txtDate);
		return result;
	}
}

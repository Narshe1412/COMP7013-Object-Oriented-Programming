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
import model.Patient;
import model.Payment;
import model.Procedure;

public class ReportAppWindow extends Stage {
	

	public ReportAppWindow() {
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

		String patientData = "";

		List<Patient> patientList = AppData.INSTANCE.getPatientList().stream().collect(Collectors.toList());
		patientList.sort((Patient a, Patient b) -> a.getName().compareToIgnoreCase(b.getName()));

		for (Patient p : patientList) {
			patientData += p.toString() + "\n";
			
		}
		if (patientData.trim().equalsIgnoreCase("")) {
			patientData += "There are no patients registered on the system.\n";
		}
		Text patientDataTxt = new Text();
		patientDataTxt.setText(patientData);

		Text procedureTitle = new Text("\nList of Procedures: \n");
		procedureTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 20));

		String procData = "";
		List<Procedure> procedureList = AppData.INSTANCE.getProcedureList().stream().collect(Collectors.toList());
		procedureList
				.sort((Procedure a, Procedure b) -> a.getProcName().get().compareToIgnoreCase(b.getProcName().get()));

		for (Procedure proc : procedureList) {
			if(!proc.isDisabled()) {
				procData += proc.toString() + "\n";
			}
		}
		if (procData.trim().equalsIgnoreCase("")) {
			procData += "There are no procedures registered on the system. \n";
		}
		Text procDataTxt = new Text();
		procDataTxt.setText(procData);

		Text paymentTitle = new Text("\nList of Payments: \n");
		paymentTitle.setFont(Font.font("Calibri", FontWeight.BLACK, 20));

		String paymentData = "";
		List<Payment> paymentList = AppData.INSTANCE.getPaymentList().stream().collect(Collectors.toList());
		
		for (Payment payment : paymentList) {
			paymentData += payment.toString() + "\n";
		}
		if (paymentData.trim().equalsIgnoreCase("")) {
			paymentData += "There are no payments registered onn the system. \n";
		}
		Text paymentDataTxt = new Text();
		paymentDataTxt.setText(paymentData);
		
		Text txtDate = new Text("\n\nReport generated on: " + new Date() + "\n");
		txtDate.setFont(Font.font("Consolas", FontWeight.THIN, FontPosture.ITALIC, 10));
		txtDate.setTextAlignment(TextAlignment.RIGHT);

		result.getChildren().addAll(patientTitle, patientDataTxt, procedureTitle, procDataTxt, paymentTitle,
				paymentDataTxt, txtDate);
		return result;
	}

	class ByName implements Comparator<Patient> {
		@Override
		public int compare(Patient a, Patient b) {
			return a.getName().compareToIgnoreCase(b.getName());
		}
	}
}

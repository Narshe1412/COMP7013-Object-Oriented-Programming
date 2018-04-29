/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.util.UUID;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Surgery extends JFrame implements ActionListener {

    private JTabbedPane tabbedPane;
    //panels for the tabs
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    //buttons in the system
    private JButton show;
    private JButton add;
    private JButton edit;
    private JButton delete;
    private JButton saveAndQuit;
    private JButton quitNoSave;
    private JButton addProcedure;
    private JButton editProcedure;
    private JButton deleteProcedure;
    private JButton addPatientProcedure;
    private JButton generateReport;    
    private JButton deletePatientProcedure;
    private JButton payPaymentProcedure;
    //textfields in the system
    private JTextField patientName;
    private JTextField patientAddress;
    private JTextField patientContact;
    private JTextField procedureName;
    private JTextField procedurePrice;
    //arraylists of the objects
    //ArrayList<Patient> patients;
    //ArrayList<Procedure> procedures;
    //lists and models for the scrollpanes
    JList listPatients;
    DefaultListModel modelPatients;
    JList listProcedures;
    DefaultListModel modelProcedures;
    JList listPayments;
    DefaultListModel modelPayments;
    //add patient procedure page labels
    JLabel idLabel, 
           patientNameLabel, 
           patientAddressLabel, 
           patientContactLabel, 
           procedureIDLabel, 
           procedureNameLabel, 
           procedurePriceLabel,
           paymentProcedureIDLabel, 
           paymentProcedureNameLabel, 
           paymentProcedurePriceLabel;
    //add patient procedure comboboxes
    JComboBox<String> selectedPatient = new JComboBox<String>();
    JComboBox<String> patientReport = new JComboBox<String>();
    JComboBox<String> patientPayment = new JComboBox<String>();
    JComboBox<String> selectedProcedure = new JComboBox<String>();
    //scrollpanes in the system
    JScrollPane patientPane;
    JScrollPane procedurePane;
    JScrollPane reportsPane;
    JScrollPane paymentsPane;
    //currentpatient/procedure being edited
    int currentPatient;
    int currentProcedure;
    //title borders used in the patient procedure page
    TitledBorder patientSection = BorderFactory.createTitledBorder("Patient");
    TitledBorder procedureSection = BorderFactory.createTitledBorder("Procedure");
    //text feild that is a date field. Uses the DateTextField class
    //This is a plugin and was coded by another user.
    DateTextField procedureDateTextField,
                  paymentReceivedDateTextField;

    //JTextArea on reports page
    JTextArea reportsText;
    //JCheckbox to show only unpaid procedures
    JCheckBox showUnPaid;   
    
    Database db;
    public Surgery() {
    	
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       
        setTitle("Dental Surgery");
        setSize(650, 600);
        setBackground(Color.gray);
        
        //read from database    
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create the tab pages
        patientPage();
        procedurePage();
        patientProcedurePage();
        paymentPage();
        reportsPage();
        setupDropDowns();
        readPatients();
        readProcedures();
        // Create a tabbed patientPane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Patient", panel1);
        tabbedPane.addTab("Add Procedure", panel2);
        tabbedPane.addTab("Add Patient Procedure", panel4);
        tabbedPane.addTab("Process Payment", panel3);
        tabbedPane.addTab("Reports", panel5);
        topPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    public void reportsPage() {
        panel5 = new JPanel();
        panel5.setLayout(new GridLayout(3, 1));

        JPanel reportsPanel = new JPanel();
        reportsPanel.add(patientReport);

        panel5.add(reportsPanel);

        JPanel reportButtonPanel = new JPanel();
        generateReport = new JButton("Generate Report");
        generateReport.addActionListener(this);
        reportButtonPanel.add(generateReport);
        panel5.add(reportButtonPanel);
        reportsText = new JTextArea();
        reportsText.setText("Generate a report");
        reportsPane = new JScrollPane(reportsText);
        panel5.add(reportsPane);

    }

    public void patientPage() {
        panel1 = new JPanel();
        panel1.setLayout(null);

        JLabel label1 = new JLabel("Patient Name:");
        label1.setBounds(10, 15, 150, 20);
        panel1.add(label1);

        patientName = new JTextField();
        patientName.setBounds(10, 35, 150, 20);
        panel1.add(patientName);

        JLabel label2 = new JLabel("Address:");
        label2.setBounds(175, 15, 150, 20);
        panel1.add(label2);

        patientAddress = new JTextField();
        patientAddress.setBounds(175, 35, 150, 20);
        panel1.add(patientAddress);

        JLabel label3 = new JLabel("Contact Number:");
        label3.setBounds(340, 15, 150, 20);
        panel1.add(label3);

        patientContact = new JTextField();
        patientContact.setBounds(340, 35, 150, 20);
        panel1.add(patientContact);

        add = new JButton("Add Patient");
        add.addActionListener(this);
        add.setBounds(10, 65, 120, 20);
        panel1.add(add);

        show = new JButton("Display Patients");
        show.addActionListener(this);
        show.setBounds(135, 65, 130, 20);
        panel1.add(show);

        edit = new JButton("Edit Patient");
        edit.addActionListener(this);
        edit.setBounds(270, 65, 100, 20);
        panel1.add(edit);

        delete = new JButton("Delete Patient");
        delete.addActionListener(this);
        delete.setBounds(375, 65, 115, 20);
        panel1.add(delete);

        saveAndQuit = new JButton("Save and Exit");
        saveAndQuit.addActionListener(this);
        saveAndQuit.setBounds(100, 95, 140, 20);
        panel1.add(saveAndQuit);

        quitNoSave = new JButton("Exit without Saving");
        quitNoSave.addActionListener(this);
        quitNoSave.setBounds(260, 95, 160, 20);
        panel1.add(quitNoSave);

        modelPatients = new DefaultListModel();
        listPatients = new JList(modelPatients);
        patientPane = new JScrollPane(listPatients);
        patientPane.setBounds(100, 130, 320, 350);
        panel1.add(patientPane);
        listPatients();
    }

    public void listPatients() {
        modelPatients.clear();
        getPatients();
    }

    public void listProcedures() {
        modelProcedures.clear();
        getProcedures();
    }
    
    public void getPatients(){
    	db = new Database();
    	db.getDBConnection();
    	String query = "select * from patient";
    	System.out.println(query);
    	db.QueryDB(query);
    	try {
			while(db.rs.next()){
			    modelPatients.addElement(db.rs.getInt("patientID") + ") - " + db.rs.getString("patientName")+ " - " + db.rs.getString("patientAddress"));	
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		}
    	db.CloseDB();
    }
    
    public void getProcedures(){
    	db = new Database();
    	db.getDBConnection();
    	String query = "select * from procedures";
    	System.out.println(query);
    	db.QueryDB(query);
    	try {
			while(db.rs.next()){
			    modelProcedures.addElement(db.rs.getInt("procedureID") + ") - " + db.rs.getString("procedureName")+ " - €" + db.rs.getFloat("procedureCost"));	
			}
		} catch (SQLException e) {
			System.out.println("Error reading patients");
		}
    	db.CloseDB();
    }
    
    public void getPatientProcedures(int i, boolean showUnPaid) throws SQLException{
    	db = new Database();
    	db.getDBConnection();
    	String sql = "";
    	if(!showUnPaid){
    		sql ="select pp.date, pp.procedureID, p.procedureName, p.procedureCost, pp.paymentID, py.isPaid, py.paymentDate"
    				+ " from patientprocedure pp "
    				+ " inner join procedures p"
    				+ " on p.procedureID = pp.procedureID"
    				+ " inner join payment py"
    				+ " on py.paymentID = pp.paymentID"
    				+ " where pp.patientID = "+i;
    		System.out.println(sql);
    		db.QueryDB(sql);
    	}
    	else{
    		sql = "select pp.date, pp.procedureID, p.procedureName, p.procedureCost, pp.paymentID, py.isPaid, py.paymentDate "
    				+ " from patientprocedure pp "
    				+ " inner join procedures p"
    				+ " on p.procedureID = pp.procedureID"
    				+ " inner join payment py"
    				+ " on py.paymentID = pp.paymentID"
    				+ " where patientID = "+i+" and isPaid=1";
    		System.out.println(sql);
    		db.QueryDB(sql);
    	}
    	int count = db.rowCount();
    	if(count>0){
    		while(db.rs.next()){
    			if(db.rs.getString("paymentDate")==null){
    				modelPayments.addElement(db.rs.getString("procedureName")+
                            " -- " +db.rs.getString("paymentID")+
                            " -- Not Paid" +
                            " -- Paid: " +db.rs.getBoolean("isPaid"));
    			}
    			else{
    				modelPayments.addElement(db.rs.getString("procedureName")+
    						" -- " +db.rs.getString("paymentID")+
                            " -- " +db.rs.getString("paymentDate")+
                            " -- Paid: " +db.rs.getBoolean("isPaid"));
    			}
                        
    		}
    	}
    	else{
    		if(!showUnPaid){
    			modelPayments.addElement("No Procedures for this patient");
    		}else{
    			modelPayments.addElement("No Paid Procedures for this patient");
    		}
    	}
    	db.CloseDB();
    }
    
    public void listPatientProcedures(int i) {
        modelPayments.clear();
        boolean showOnlyUnPaid = showUnPaid.isSelected();
        try{
        	getPatientProcedures(i,showOnlyUnPaid);
        }
        catch(Exception e){
        	System.out.println("Error getting patient procedures for patientID "+i);
        	e.printStackTrace();
        }
    }

    public void procedurePage() {
        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        JPanel procedureText = new JPanel();
        procedureText.setLayout(new GridLayout(1, 4));

        procedureText.add(new JLabel("Procedure Name:"));
        procedureName = new JTextField();
        procedureText.add(procedureName);
        procedureText.add(new JLabel("Procedure Price:"));
        procedurePrice = new JTextField();
        procedureText.add(procedurePrice);

        addProcedure = new JButton("Add Procedure");
        addProcedure.addActionListener(this);
        editProcedure = new JButton("Edit Procedure");
        editProcedure.addActionListener(this);
        deleteProcedure = new JButton("Delete Procedure");
        deleteProcedure.addActionListener(this);
        JPanel editPanel = new JPanel();
        editPanel.add(editProcedure);
        JPanel addPanel = new JPanel();
        addPanel.add(addProcedure);
        JPanel deletePanel = new JPanel();
        deletePanel.add(deleteProcedure);
        panel2.add(procedureText, BorderLayout.NORTH);
        panel2.add(addPanel, BorderLayout.SOUTH);
        panel2.add(editPanel, BorderLayout.EAST);
        panel2.add(deletePanel, BorderLayout.WEST);
        modelProcedures = new DefaultListModel();
        listProcedures = new JList(modelProcedures);
        procedurePane = new JScrollPane(listProcedures);

        panel2.add(procedurePane, BorderLayout.CENTER);
        listProcedures();
    }

    public void paymentPage() {
        panel3 = new JPanel();
        panel3.setLayout(null);
        
        JLabel label1 = new JLabel("Select Patient:");
        label1.setBounds(10, 15, 150, 20);
        panel3.add(label1);
        patientPayment.setBounds(10, 35, 150, 20);
        patientPayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePatientsPaymentPage();
            }
        });        
        panel3.add(patientPayment);
        
        showUnPaid = new JCheckBox("Show Unpaid Only");
        showUnPaid.setBounds(10,75,150,20);
        showUnPaid.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePatientsPaymentPage();
            }
        });
        panel3.add(showUnPaid);
        
        JLabel label2 = new JLabel("Procedure -- PaymentID -- Date -- Payment Received");
        label2.setBounds(10, 110, 400, 20);
        panel3.add(label2);
        
        modelPayments = new DefaultListModel();
        listPayments = new JList(modelPayments);
        paymentsPane = new JScrollPane(listPayments);
        paymentsPane.setBounds(10, 130, 590, 250);
        panel3.add(paymentsPane);        
        
        deletePatientProcedure= new JButton("Remove Procedure");
        deletePatientProcedure.addActionListener(this);
        deletePatientProcedure.setBounds(50, 420, 150, 20);
        panel3.add(deletePatientProcedure);
        payPaymentProcedure = new JButton("Process Payment");
        payPaymentProcedure.addActionListener(this);
        payPaymentProcedure.setBounds(250, 420, 150, 20);
        panel3.add(payPaymentProcedure);
    }

    public void setupDropDowns() {
        listPatients();
        listProcedures();
        selectedPatient.removeAllItems();
        selectedProcedure.removeAllItems();
        patientPayment.removeAllItems();
        patientReport.removeAllItems();
        selectedPatient.addItem("");
        selectedProcedure.addItem("");
        patientPayment.addItem("");
        for (int i = 0; i < modelPatients.size(); i++) {
            selectedPatient.addItem(modelPatients.getElementAt(i).toString());
            patientReport.addItem(modelPatients.getElementAt(i).toString());
            patientPayment.addItem(modelPatients.getElementAt(i).toString());
        }

        for (int i = 0; i < modelProcedures.size(); i++) {
            selectedProcedure.addItem(modelProcedures.getElementAt(i).toString());
        }
    }

    public void patientProcedurePage() {
        panel4 = new JPanel();
        panel4.setLayout(null);
        selectedPatient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try{
                	updatePatientProcedurePage(1);
                }
                catch(Exception ex){
                	
                }
            }
        });

        selectedProcedure.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                	updatePatientProcedurePage(2);
                }
                catch(Exception ex){
                	
                }
            }
        });

        JLabel label1 = new JLabel("Patient:");
        label1.setBounds(10, 15, 150, 20);
        panel4.add(label1);

        selectedPatient.setBounds(10, 35, 250, 20);
        panel4.add(selectedPatient);

        JLabel label2 = new JLabel("Procedure:");
        label2.setBounds(265, 15, 150, 20);
        panel4.add(label2);

        selectedProcedure.setBounds(265, 35, 250, 20);
        panel4.add(selectedProcedure);

        patientSection.setTitleJustification(TitledBorder.LEFT);
        JPanel patientPanel = new JPanel();
        patientPanel.setLayout(new GridLayout(4, 1));
        patientPanel.setBorder(patientSection);
        patientPanel.setBounds(10, 60, 500, 200);
        idLabel = new JLabel("");
        patientNameLabel = new JLabel("");
        patientAddressLabel = new JLabel("");
        patientContactLabel = new JLabel("");
        patientPanel.add(idLabel);
        patientPanel.add(patientNameLabel);
        patientPanel.add(patientAddressLabel);
        patientPanel.add(patientContactLabel);

        panel4.add(patientPanel);
        procedureSection.setTitleJustification(TitledBorder.LEFT);
        JPanel procedurePanel = new JPanel();
        procedurePanel.setLayout(new GridLayout(3, 1));
        procedurePanel.setBorder(procedureSection);
        procedurePanel.setBounds(10, 265, 500, 200);
        procedureIDLabel = new JLabel("");
        procedureNameLabel = new JLabel("");
        procedurePriceLabel = new JLabel("");
        procedurePanel.add(procedureIDLabel);
        procedurePanel.add(procedureNameLabel);
        procedurePanel.add(procedurePriceLabel);
        panel4.add(procedurePanel);

        JLabel label4 = new JLabel("Procedure Date");
        label4.setBounds(10, 465, 150, 20);
        panel4.add(label4);

        DateFormat format = new SimpleDateFormat("yyyy--MMMM--dd");
        procedureDateTextField = new DateTextField();
        procedureDateTextField.setBounds(10, 485, 200, 20);
        panel4.add(procedureDateTextField);

        addPatientProcedure = new JButton("Add Patient Procedure");
        addPatientProcedure.addActionListener(this);
        addPatientProcedure.setBounds(250, 485, 180, 20);
        panel4.add(addPatientProcedure);
    }

    public void updatePatientsPaymentPage(){
        modelPayments.clear();
        if(patientPayment.getItemCount()>0){
            String s = patientPayment.getSelectedItem().toString();
            if(!s.equals("")){
                int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
                listPatientProcedures(i);
            }
        }
        
    }
    
    public void updatePatientProcedurePage(int check) throws SQLException {
        if (check == 1) {
            if(selectedPatient.getItemCount()>0){
                String s = selectedPatient.getSelectedItem().toString();
                if(!s.equals("")){
                    int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
                    System.out.println(i);
                    db = new Database();
                	db.getDBConnection();
                	db.QueryDB("select * from patient where patientID ="+i);
                	while(db.rs.next()){
                		idLabel.setText("ID: " + db.rs.getInt("patientID"));
                        patientNameLabel.setText("Name: " + db.rs.getString("patientName"));
                        patientAddressLabel.setText("Address: " + db.rs.getString("patientAddress"));
                        patientContactLabel.setText("Phone: " + db.rs.getString("patientPhone"));
                	} 
                	db.CloseDB();
                }
            }                        
        } else if (check == 2) {
            if(selectedProcedure.getItemCount()>0){
                String s = selectedProcedure.getSelectedItem().toString();
                    if(!s.equals("")){
                    int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
                    System.out.println(i);
                    //idLabel, patientNameLabel, patientAddressLabel, patientContactLabel, procedureIDLabel, procedureNameLabel, procedurePriceLabel;
                    db = new Database();
                	db.getDBConnection();
                	db.QueryDB("select * from procedures where procedureID ="+i);
                	while(db.rs.next()){
                		procedureIDLabel.setText("ID: " + db.rs.getInt("procedureID"));
                        procedureNameLabel.setText("Procedure: " + db.rs.getString("procedureName"));
                        procedurePriceLabel.setText("Price: " + db.rs.getFloat("procedureCost"));
                	}
                }
            }            
        } else {
            idLabel.setText("");
            patientNameLabel.setText("");
            patientAddressLabel.setText("");
            patientContactLabel.setText("");
            procedureIDLabel.setText("");
            procedureNameLabel.setText("");
            procedurePriceLabel.setText("");
        }
    }
    
    // Main method to get things started
    public static void main(String args[]) {
        // Create an instance of the test application
        Surgery mainFrame = new Surgery();
        mainFrame.setVisible(true);
    }

    public void readPatients() {
        listPatients();
    }

    public void readProcedures() {
        listProcedures();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Patient")) {
            if (validatePatient()) {
                String name = patientName.getText();
                String address = patientAddress.getText();
                String contact = patientContact.getText();
                Patient p = new Patient(name, address, contact, 0);
                p.save();                  
                setupDropDowns();
                showMessage("Patient Added");
                resetPatientFields();
            }
        } else if (e.getActionCommand().equals("Display Patients")) {
            listPatients();
        } else if (e.getActionCommand().equals("Edit Patient")) {
            editPatient();
        } else if (e.getActionCommand().equals("Delete Patient")) {
            deletePatient();
        } else if (e.getActionCommand().equals("Update Patient")) {
            update();
        } else if (e.getActionCommand().equals("Save and Exit")) {
            save();
        } else if (e.getActionCommand().equals("Edit Procedure")) {
            editProcedure();
        } else if (e.getActionCommand().equals("Add Procedure")) {
            addProcedure();
        } else if (e.getActionCommand().equals("Delete Procedure")) {
            deleteProcedure();
        } else if (e.getActionCommand().equals("Update Procedure")) {
            updateProcedure();
        } else if (e.getActionCommand().equals("Add Patient Procedure")) {
            addPatientProcedure();
        } else if (e.getActionCommand().equals("Exit without Saving")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("Generate Report")) {
            generateReport();
        }else if (e.getActionCommand().equals("Remove Procedure")) {
            removePatientProcedure();
        }else if (e.getActionCommand().equals("Process Payment")) {
            processPayment();
        }

    }

    public static int nthOccurrence(String str, String c, int n) {
        int pos = str.indexOf(c, 0);
        while (n-- > 0 && pos != -1)
            pos = str.indexOf(c, pos+1);
        return pos;
    }
    
    public void processPayment(){
        try {
            String s = patientPayment.getSelectedItem().toString();
            int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
            System.out.println(i);
            s = listPayments.getSelectedValue().toString();
            int index = nthOccurrence(s, "--", 0);
            int index2 = nthOccurrence(s, "--", 1);            
            String paymentID = s.substring(index+3,index2-1);
            System.out.println("-"+paymentID+"-");
            Database db = new Database();
            db.getDBConnection();
            db.QueryDB("select * from payment where paymentID = '"+paymentID+"'");
            Payment pc = new Payment();
            pc.setPaymentId(paymentID);
            while(db.rs.next()){
            	pc.setPaid(db.rs.getBoolean("isPaid"));
            	pc.setPaymentAmount(db.rs.getFloat("paymentAmount"));
            	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            	Date date = new Date();
            	System.out.println(dateFormat.format(date));
            	pc.setPaymentDate(dateFormat.format(date));
            }
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Has full payment of €"+pc.getPaymentAmount()+" been received?", "Process Payment", dialogButton);
            if (dialogResult == 0) {
                pc.setPaid(true);                                
                pc.update();
                listPatientProcedures(i);
            }                      
        } catch (Exception e) {
            //showMessage("Please select a procedure or add a procedure if none exist");
            e.printStackTrace();
        }
    }
    
    public void removePatientProcedure(){
        try {
            String s = patientPayment.getSelectedItem().toString();
            int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
            System.out.println(i);
            s = listPayments.getSelectedValue().toString();
            int index = nthOccurrence(s, "--", 0);
            int index2 = nthOccurrence(s, "--", 1);            
            String paymentID = s.substring(index+3,index2-1);
            System.out.println("-"+paymentID+"-");
            int ppID=0;
            String procedure="";
        	Database db =new Database();
        	db.getDBConnection();
        	String sql ="select * from patientProcedure where patientID="+i+" and paymentID='"+paymentID+"'";
        	System.out.println(sql);
        	db.QueryDB(sql);
        	PatientProcedure pp = new PatientProcedure();
        	while(db.rs.next()){
        		 pp.setPPID(db.rs.getInt("ID"));
        	}
        	db.CloseDB();
        	
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this procedure from this paitent?", "Remove Procedure from Patient", dialogButton);
            if (dialogResult == 0) {
            	
            	Payment p=new Payment();
                p.setPaymentId(paymentID);
                p.delete();
            	pp.setPayment(p);
            	pp.delete();
                listPatientProcedures(i);
            }
                      
        } catch (Exception e) {
            //showMessage("Please select a procedure or add a procedure if none exist");
            e.printStackTrace();
        }
    }
    
    public void generateReport() {
        String s = patientReport.getSelectedItem().toString();
        int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
        Database db = new Database();
        db.getDBConnection();
    	String query = "select * from patient where patientID="+i;
    	db.QueryDB(query);
    	Patient p = new Patient();
    	try {
			while(db.rs.next()){
			    p.setPatientId(db.rs.getInt("patientID"));
			    p.setPatientName(db.rs.getString("patientName"));
			    p.setpatientAddress(db.rs.getString("patientAddress"));
			    p.setPatientPhone(db.rs.getString("patientPhone"));		
			}
		} catch (SQLException e) {
			System.out.println("Error reading patient");
			e.printStackTrace();
		}
    	db.CloseDB();
    	try {
			writeReport(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void writeReport(Patient p) throws BadLocationException, SQLException {
        int count = 0;
        reportsText.setText("");
        double totalOwed=0;
        double totalPaid=0;
        String s="";
        s+="Patient Name: "+ p.getPatientName()+"\n";
        s+="Patient Address: " + p.getpatientAddress()+"\n";
        s+="Patient Phone: " + p.getPatientPhone()+"\n";

        s+="==============================================\n";
        s+="Patient Procedures\n";
        s+="==============================================\n";        
        
        Database db = new Database();
        db.getDBConnection();
        String sql ="select pp.date, pp.procedureID, p.procedureName, p.procedureCost, pp.paymentID, py.isPaid, py.paymentDate, py.paymentAmount"
				+ " from patientprocedure pp "
				+ " inner join procedures p"
				+ " on p.procedureID = pp.procedureID"
				+ " inner join payment py"
				+ " on py.paymentID = pp.paymentID"
				+ " where pp.patientID = "+p.getPatientId();
        db.QueryDB(sql);
        while(db.rs.next()){
        	 s+="_____________________________________________\n";
             s+="\tProcedure Name: " + db.rs.getString("procedureName")+"\n";
             s+="\tProcedure Price: " + db.rs.getFloat("procedureCost")+"\n";
             s+="\tProcedure Date: " + db.rs.getString("date")+"\n";
             s+="\t\tPayment ID: " + db.rs.getString("paymentID")+"\n";
             s+="\t\tPayment Received: " + db.rs.getBoolean("isPaid")+"\n";
             if(db.rs.getBoolean("isPaid")){
            	 totalPaid+=db.rs.getFloat("paymentAmount");
            	 s+="\t\tPayment Date: " + db.rs.getString("paymentDate")+"\n";                 
             }
             else{
            	 totalOwed+=db.rs.getFloat("paymentAmount");
             }
             s+="\t\tPayment Amount: " + db.rs.getFloat("paymentAmount")+"\n";
             s+="_____________________________________________\n";
             
             count++;
        }
        
        if(count<1){
            s+="\tNo Procedures yet!!!\n";            
        }
        else{            
            s+="----------------------------------------------------------------------------------------\n";
            s+="Total Paid: "+totalPaid+"\n";
            s+="Total Owed: " +totalOwed+"\n";
        }
        System.out.println(s);
        reportsText.append(s);
    }

    public void addPatientProcedure() {
        if (validatePatientProcedure()) {
            String date = procedureDateTextField.getText();

            Procedure proc = new Procedure();
            String s = selectedProcedure.getSelectedItem().toString();
            int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
            System.out.println(i);
            
            Database db = new Database();
            db.getDBConnection();
            String query = "select * from procedures where procedureID="+i;
        	System.out.println(query);
            db.QueryDB(query);
        	try {
    			while(db.rs.next()){
    			    proc.setProcedureId(db.rs.getInt("procedureID"));
    			    proc.setProcedureName(db.rs.getString("procedureName"));
    			    proc.setProcedureCost(db.rs.getFloat("procedureCost"));	
    			}
    		} catch (SQLException e) {
    			System.out.println("Error reading procedure");
    			e.printStackTrace();
    		}
            s = selectedPatient.getSelectedItem().toString();
            i = Integer.parseInt(s.substring(0, s.indexOf(")")));
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            Payment pay = new Payment("", proc.getProcedureCost(), randomUUIDString, false);
            pay.save();
            PatientProcedure pp = new PatientProcedure(0,i,proc, date, pay);
            pp.save();
                
            showMessage("Procedure added to patient");
            resetPatientProcedurePage();
        }
    }

    public void resetPatientProcedurePage() {
    	try{
        	updatePatientProcedurePage(0);
        }
        catch(Exception ex){
        	
        }
    }

    public boolean validatePatientProcedure() {
        if (selectedPatient.getSelectedItem().equals("")) {
            showMessage("Select a Patient");
            return false;
        }

        if (selectedProcedure.getSelectedItem().equals("")) {
            showMessage("Select a Procedure");
            return false;
        }

        if (idLabel.getText().equals("")) {
            showMessage("Select a Patient");
            return false;
        }

        if (procedureIDLabel.getText().equals("")) {
            showMessage("Select a Procedure");
            return false;
        }

        return true;
    }

    public void addProcedure() {
        if (validateProcedure()) {
            String name = procedureName.getText();
            String price = procedurePrice.getText();

            double b = Double.parseDouble(price);
            Procedure p = new Procedure(name, b, 0);
            p.save();
            showMessage("Procedure Added");
            resetProcedureFields();
            listProcedures();
            setupDropDowns();
        }
    }

    public void updateProcedure() {
        if (validateProcedure()) {
            String name = procedureName.getText();
            String price = procedurePrice.getText();

            double b = Double.parseDouble(price);
            Procedure p = new Procedure();
            p.setProcedureId(currentProcedure);
            p.setProcedureName(name);
            p.setProcedureCost(b);
            p.update(); //save to database
            showMessage("Procedure Updated");
            resetProcedureFields();
            add.setText("Add Procedure");
            listProcedures();
            setupDropDowns();
            
        }
    }

    public void update() {
        if (validatePatient()) {
            String name = patientName.getText();
            String address = patientAddress.getText();
            String contact = patientContact.getText();
            Patient p = new Patient();
            p.setPatientId(currentPatient);
            p.setPatientName(name);
            p.setpatientAddress(address);
            p.setPatientPhone(contact);
            p.update();
            showMessage("Patient Updated");
            resetPatientFields();
            add.setText("Add Patient");
            listPatients();
        }
    }

    public void editPatient() {
        try {
            String s = listPatients.getSelectedValue().toString();
            add.setText("Update Patient");
            int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
            System.out.println(i);
            Database db= new Database();
            db.getDBConnection();
            db.QueryDB("Select * from patient where patientID = "+i);
            while(db.rs.next()){
            	patientName.setText(db.rs.getString("patientName"));
            	patientAddress.setText(db.rs.getString("patientAddress"));
            	patientContact.setText(db.rs.getString("patientPhone"));
                currentPatient = db.rs.getInt("patientID");
            }
            listPatients();
        } catch (Exception e) {
            showMessage("Please select a patient or add a patient if none exist");
            //e.printStackTrace();
        }
    }

    public void deletePatient() {
        try {
            String s = listPatients.getSelectedValue().toString();
            int i = Integer.parseInt(s.substring(0, 1));
            System.out.println(i);
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you wish to delete this patient?", "Delete Patient", dialogButton);
            if (dialogResult == 0) {
            	 Patient p = new Patient();
            	 p.setPatientId(i);
            	 p.delete();
                 listPatients();
            }
        } catch (Exception e) {
            showMessage("Please select a patient or add a patient if none exist");
            //e.printStackTrace();
        }
    }

    public void save() {
        /*if (savePatients()) {
            saveProcedures();
            showMessage("Save Successful");
            System.exit(0);
        } else {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Do you still wish to exit?", "Error Saving Patients File", dialogButton);
            if (dialogResult == 0) {
                System.exit(0);
            }
        }*/
    	System.exit(0);
    }

    public void resetPatientFields() {
        patientName.setText("");
        patientAddress.setText("");
        patientContact.setText("");
    }

    public void resetProcedureFields() {
        procedureName.setText("");
        procedurePrice.setText("");
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public boolean validatePatient() {
        String name = patientName.getText();
        String address = patientAddress.getText();
        String contact = patientContact.getText();
        if (name.equals("")) {
            showMessage("Please enter the patient name");
            return false;
        }

        if (address.equals("")) {
            showMessage("Please enter the patient address");
            return false;
        }

        if (contact.equals("")) {
            showMessage("Please enter the patient contact number");
            return false;
        }

        try {
            Integer.parseInt(contact);
        } catch (Exception e) {
            showMessage("Please enter a valid patient number(Numbers only!!)");
            return false;
        }
        if (contact.length() < 7 || contact.length() > 10) {
            showMessage("Please enter a valid number(7-10 digits");
            return false;
        }
        return true;
    }

    public boolean validateProcedure() {
        String name = procedureName.getText();
        String price = procedurePrice.getText();
        if (name.equals("")) {
            showMessage("Please enter the procedure name");
            return false;
        }

        try {
            double b = Double.parseDouble(price);
        } catch (Exception e) {
            showMessage("Please enter a price in the following format 00.00");
            return false;
        }
        return true;
    }

    public void editProcedure() {
        try {
            String s = listProcedures.getSelectedValue().toString();
            addProcedure.setText("Update Procedure");
            int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
            Database db= new Database();
            db.getDBConnection();
            db.QueryDB("Select * from procedures where procedureID = "+i);
            while(db.rs.next()){
            	procedureName.setText(db.rs.getString("procedureName"));
                procedurePrice.setText("" + db.rs.getFloat("procedureCost"));
                currentProcedure = db.rs.getInt("procedureID");
            }
            
            listProcedures();
        } catch (Exception e) {
            showMessage("Please select a procedure or add a procedure if none exist");
            //e.printStackTrace();
        }
    }

    public void deleteProcedure() {
        try {
            String s = listProcedures.getSelectedValue().toString();
            int i = Integer.parseInt(s.substring(0, s.indexOf(")")));
            System.out.println(i);
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you wish to delete this procedure?", "Delete Procedure", dialogButton);
            if (dialogResult == 0) {
               Procedure p = new Procedure();
               p.setProcedureId(i);
               p.delete();
            }
           
            listProcedures();
        } catch (Exception e) {
            showMessage("Please select a procedure or add a procedure if none exist");
            //e.printStackTrace();
        }
    }

}

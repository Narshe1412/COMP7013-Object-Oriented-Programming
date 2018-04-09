package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import org.junit.jupiter.api.Test;

import model.Invoice;
import model.Patient;

class TestPatient {
	
	@Test
	void testPatientCreate() {
		//Arrange
		Patient sut, sut2;
		String name = "name";
		String address = "addr";
		String phone = "12233";
		
		//Act
		sut = new Patient(name, address);
		sut2 = new Patient(name, address, phone);
		
		//Assert
		assertAll("Patient",
				()->assertEquals(name, sut.getName()),
				()->assertEquals(address, sut.getAddress()),
				()->assertEquals("", sut.getPhone()));
		
		assertAll("Patient2",
				()->assertEquals(name, sut2.getName()),
				()->assertEquals(address, sut2.getAddress()),
				()->assertEquals(phone, sut2.getPhone()));
	}
	
	@Test
	void testPatientAddInvoice() {
		//Arrange
		Patient sut3;
		String name = "name";
		String address = "addr";
		String phone = "12233";
		sut3 = new Patient(name, address, phone);
		
		Invoice invOne = new Invoice(new Date());
		Invoice invTwo = new Invoice(new Date());
		
		//Act
		sut3.addInvoice(invOne);
		
		//Assert
		assertAll("",
				()->assertEquals(1, sut3.getP_invoiceList().size()),
				()->assertEquals(true, sut3.getP_invoiceList().contains(invOne)));
		//Act
		sut3.addInvoice(invTwo);
		
		//Assert
		assertAll("",
				()->assertEquals(2, sut3.getP_invoiceList().size()),
				()->assertEquals(true, sut3.getP_invoiceList().contains(invOne)),
				()->assertEquals(true, sut3.getP_invoiceList().contains(invTwo))
				);		
	}
	
	@Test
	void testPatientRemoveInvoice() {
		//Arrange
		Patient sut4;
		String name = "name";
		String address = "addr";
		String phone = "12233";
		sut4 = new Patient(name, address, phone);
		
		Invoice invOne = new Invoice(new Date());
		Invoice invTwo = new Invoice(new Date());
		sut4.addInvoice(invOne);
		sut4.addInvoice(invTwo);
		
		//Act
		sut4.removeInvoice(invOne);
		
		//Assert
		assertAll("",
				()->assertEquals(1, sut4.getP_invoiceList().size()),
				()->assertEquals(false, sut4.getP_invoiceList().contains(invOne)),
				()->assertEquals(true, sut4.getP_invoiceList().contains(invTwo))
				);
	}
	
	@Test
	void testPatientNonExistantRemoveInvoice() {
		//Arrange
		Patient sut5;
		String name = "name";
		String address = "addr";
		String phone = "12233";
		sut5 = new Patient(name, address, phone);
		
		Invoice invOne = new Invoice(new Date());
		Invoice invTwo = new Invoice(new Date());
		sut5.addInvoice(invOne);
		
		//Assert
		assertAll("",
				()->assertEquals(false, sut5.removeInvoice(invTwo)),
				()->assertEquals(1, sut5.getP_invoiceList().size()),
				()->assertEquals(true, sut5.getP_invoiceList().contains(invOne)),
				()->assertEquals(false, sut5.getP_invoiceList().contains(invTwo))
				);
	}
	
	@Test
	void testPatientID() {
		//Arrange
		Patient sut6;
		String name = "name";
		String address = "addr";
		String phone = "12233";
		
		//Arrange
		sut6 = new Patient(name, address, phone);
		int expectedIDsut6 = 6 - 1; // counter starts at 0
		
		//Assert
		assertAll("",
				()->assertEquals(expectedIDsut6, sut6.getPatientNo()));
	}
}
/*
 * 	private static int id = 0;
	private int patientNo;

	public int getPatientNo() {
	}
	
	public void setPatientNo() {
	}
	
 * */

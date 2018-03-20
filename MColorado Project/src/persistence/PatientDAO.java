package persistence;

import model.Patient;

public class PatientDAO implements IDBOperationRepository<Patient>{

	@Override
	public boolean add(Patient contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Patient> getByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Patient> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Patient contents) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Patient contents) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}

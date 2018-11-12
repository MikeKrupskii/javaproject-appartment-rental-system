package flexiRentSystem.model;

import exceptions.MaintenanceException;
import exceptions.RentException;
import exceptions.ReturnException;

public class ApplicationStartUp {

	public static void main(String[] args) throws RentException, ReturnException, MaintenanceException {
		FlexiRentSystem system = new FlexiRentSystem();
		system.readInputAndPerformActions();
	}

}

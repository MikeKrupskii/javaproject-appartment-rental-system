package flexiRentSystem.model;

import java.util.Calendar;

import exceptions.MaintenanceException;

public class Appartment extends RentalProperty {

	public Appartment() {
	}

	public Appartment(int streetNumber, String streetName, String suburb, int numOfBedrooms, PropertyType type,
			PropertyStatus status) {
		super(streetNumber, streetName, suburb, numOfBedrooms, type, status);
		setRentalRate();
	}

	@Override
	public void setRentalRate() {
		if (this.getNumberOfBedroom() == 1) {
			this.rentalRate = 143;
		} else if (this.getNumberOfBedroom() == 2) {
			this.rentalRate = 210;
		} else if (this.getNumberOfBedroom() == 3) {
			this.rentalRate = 319;
		}
	}

	private int getDayOfWeek(DateTime date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek;
	}

	@Override
	public void setMinRentalPeriod(DateTime startDate, DateTime endDate) {
		if (getDayOfWeek(startDate) == Calendar.FRIDAY || getDayOfWeek(startDate) == Calendar.SATURDAY) {
			this.minRentalPeriod = 3;
		} else if (getDayOfWeek(startDate) >= Calendar.SUNDAY && getDayOfWeek(endDate) <= Calendar.THURSDAY) {
			this.minRentalPeriod = 2;
		}
	}

	@Override
	public void performMaintenance(DateTime startDate) throws MaintenanceException {

		if (this.getStatus() == PropertyStatus.UnderMaintenance) {
			throw new MaintenanceException("Property is already under maintenance!");
		}
		if (this.getStatus() == PropertyStatus.Rented) {
			throw new MaintenanceException("Mainteanance couldn't be performed, the proeprty is being rented!");
		} else {
			this.setStatus(PropertyStatus.UnderMaintenance);
		}
	}

	@Override
	public void completeMaintenance(DateTime completionDate) throws MaintenanceException {
		if (this.getStatus() == PropertyStatus.Rented) {
			throw new MaintenanceException("Maintenance couldn't be completed, since property is rented!");
		} else {
			if (this.getStatus() == PropertyStatus.UnderMaintenance) {
				this.setStatus(PropertyStatus.Available);
			} else {
				throw new MaintenanceException(
						"Can't complete the action, since maintenance has never been initiated!");
			}

		}
	}

	@Override
	public String getLastMaintenance() {
		return "Not applicable";
	}

}

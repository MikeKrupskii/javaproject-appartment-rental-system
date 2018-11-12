package flexiRentSystem.model;

import exceptions.MaintenanceException;

public class PremiumSuit extends RentalProperty {

	private static final int numberOfBedrooms = 3;

	private DateTime lastMaintenanceDate;

	public PremiumSuit(int streetNumber, String streetName, String suburb, PropertyType type, PropertyStatus status,
			DateTime lastMaintenance) {
		super(streetNumber, streetName, suburb, numberOfBedrooms, type, status);
		setRentalRate();
		this.lastMaintenanceDate = lastMaintenance;
	}

	public void setLastMaintenanceDate(DateTime lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}

	public DateTime getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}

	@Override
	public void setRentalRate() {
		this.rentalRate = 554;
	}

	@Override
	public void setMinRentalPeriod(DateTime startDate, DateTime endDate) {
		this.minRentalPeriod = 1;
	}

	@Override
	public void performMaintenance(DateTime startDate) throws MaintenanceException {
		if (DateTime.diffDays(startDate, this.getLastMaintenanceDate()) > 10)
			throw new MaintenanceException("Maintenance is due earlier!");
		if (DateTime.diffDays(startDate, this.getLastMaintenanceDate()) < 10)
			throw new MaintenanceException("Maintenance is due later!");
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
		if (this.getStatus() != PropertyStatus.UnderMaintenance) {
			throw new MaintenanceException("Maintenance has never been started!");
		}
		if (this.getStatus() == PropertyStatus.Rented) {
			throw new MaintenanceException("Mainteanance couldn't be performed, the proeprty is being rented!");
		} else {
			this.setLastMaintenanceDate(completionDate);
			this.setStatus(PropertyStatus.Available);
		}
	}

	public String getFormattedMaintenance() {
		String maintenanceDateString = "";
		if (lastMaintenanceDate == null) {
			maintenanceDateString = "Maintenance not yet performed!";
		} else {
			maintenanceDateString = this.lastMaintenanceDate.getFormattedDate();
		}
		return maintenanceDateString;
	}

	@Override
	public String toString() {

		return this.uniqueID + ":" + this.streetNumber + ":" + this.streetName + ":" + suburb + ":"
				+ this.getTypeStringFormat() + ":" + numberOfBedroom + ":" + this.getStatusStringFormat() + ":"
				+ this.lastMaintenanceDate + ":" + this.getPathToImage() + ":" + this.getDescription();
	}

	@Override
	public String printDetails() {

		return "Property ID: " + this.uniqueID + "\nAddress: " + this.streetNumber + " " + this.streetName + "\nType: "
				+ this.getTypeStringFormat() + "\nBedroom: " + this.getNumberOfBedroom() + "\nStatus: "
				+ this.getStatusStringFormat() + "\nLast maintenance: " + getFormattedMaintenance()
				+ "\nRENTAL RECORD: " + this.getAllRentalRecordInfo();
	}

	@Override
	public String getLastMaintenance() {
		return lastMaintenanceDate.getFormattedDate();
	}
}

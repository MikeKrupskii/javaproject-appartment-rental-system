package flexiRentSystem.model;

import java.util.ArrayList;

import exceptions.MaintenanceException;
import exceptions.RentException;
import exceptions.ReturnException;

public abstract class RentalProperty {

	protected String uniqueID;
	protected int streetNumber;
	protected String streetName;
	protected String suburb;
	protected int numberOfBedroom;
	protected PropertyType flexiRentType;
	protected PropertyStatus status;
	protected String pathToImage;
	protected String description;

	protected int rentalRate;
	protected int minRentalPeriod;
	private static final int maxRentalPeriod = 28;

	private ArrayList<RentalRecord> records = new ArrayList<>();

	public ArrayList<RentalRecord> getRecords() {
		return records;
	}

	public RentalProperty() {
	}

	public RentalProperty(int streetNumber, String streetName, String suburb, int numOfBedrooms, PropertyType type,
			PropertyStatus status) {
		this.streetNumber = streetNumber;
		this.streetName = streetName;
		this.suburb = suburb;
		this.numberOfBedroom = numOfBedrooms;
		this.flexiRentType = type;
		this.status = status;
		this.pathToImage = "/flexiRentSystem/img/no_image.jpg";
		this.description = "No description available";
		setUniqueID();
	}

	public void setUniqueID() {
		String firstLetter;
		if (flexiRentType == PropertyType.Appartment) {
			firstLetter = "A_";
		} else {
			firstLetter = "S_";
		}
		uniqueID = firstLetter + streetNumber + streetName.substring(0, 1).toUpperCase() + "S"
				+ suburb.substring(0, 3).toUpperCase();
	}

	/** GETTERS AND SETTERS: **/

	public void setPathToImage(String path) {
		this.pathToImage = path;
	}

	public String getPathToImage() {
		return pathToImage;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public int getNumberOfBedroom() {
		return numberOfBedroom;
	}

	public void setNumberOfBedroom(int numberOfBedroom) {
		this.numberOfBedroom = numberOfBedroom;
	}

	public PropertyType getFlexiRentType() {
		return flexiRentType;
	}

	public void setFlexiRentType(PropertyType flexiRentType) {
		this.flexiRentType = flexiRentType;
	}

	public PropertyStatus getStatus() {
		return status;
	}

	public String getTypeStringFormat() {
		if (this.getFlexiRentType() == PropertyType.Appartment) {
			return "Appartment";
		} else if (this.getFlexiRentType() == PropertyType.PremiumSuit) {
			return "Premium Suit";
		} else {
			return "Invalid Property Type";
		}
	}

	public void setStatus(PropertyStatus status) {
		this.status = status;
	}

	public String getStatusStringFormat() {
		if (this.getStatus() == PropertyStatus.Rented) {
			return "Rented";
		} else if (this.getStatus() == PropertyStatus.Available) {
			return "Available";
		} else if (this.getStatus() == PropertyStatus.UnderMaintenance) {
			return "Under Maintenance";
		} else {
			return "Invalid status";
		}
	}

	public int getMinRentalPeriod() {
		return minRentalPeriod;
	}

	public static int getMaxrentalperiod() {
		return maxRentalPeriod;
	}

	/******************************/

	/** ABSTRACT METHODS: **/
	public abstract void setRentalRate();

	public abstract void setMinRentalPeriod(DateTime startDate, DateTime endDate);

	public abstract void completeMaintenance(DateTime completionDate) throws MaintenanceException;

	public abstract String getLastMaintenance();

	public abstract void performMaintenance(DateTime date) throws MaintenanceException;

	/******************************/

	public int getRentalRate() {
		return rentalRate;
	}

	public void addRecord(RentalRecord someRecord) {
		records.add(0, someRecord);
	}

	public boolean isUnderMainenance() {
		if (this.getStatus() == PropertyStatus.UnderMaintenance)
			return true;
		else
			return false;
	}

	public boolean rentedAlready(DateTime rentDate, int days) {
		DateTime estimated = new DateTime(rentDate, days);
		for (RentalRecord record : records) {
			if (record != null) {
				if (record.getRentDate().toString().equals(rentDate.toString())) {
					return true;
				} else if (DateTime.diffDays(estimated, record.getRentDate()) >= 0
						&& DateTime.diffDays(estimated, record.getActualReturn()) <= 0) {
					return true;
				} else if (DateTime.diffDays(rentDate, record.getActualReturn()) <= 0
						&& DateTime.diffDays(rentDate, record.getRentDate()) >= 0) {
					return true;
				}

			}
		}
		return false;
	}

	public void rent(String customerID, DateTime rentDate, int numOfRentDays) throws RentException {
		DateTime estimatedReturn = new DateTime(rentDate, numOfRentDays);
		if (this.getStatus() == PropertyStatus.Rented)
			throw new RentException("The property is unavailable for this date!");
		else if (rentedAlready(rentDate, numOfRentDays))
			throw new RentException("Sorry, but the property has already been rented for this date!");
		else if (isUnderMainenance())
			throw new RentException("Property is under maintenace!");
		else if (numOfRentDays < this.minRentalPeriod || numOfRentDays > maxRentalPeriod)
			throw new RentException("Invalid rental period!");
		else if (this.getFlexiRentType() == PropertyType.PremiumSuit) {
			PremiumSuit suit = (PremiumSuit) this;
			DateTime lastMaintenance = suit.getLastMaintenanceDate();
			DateTime followingMaintenance = new DateTime(lastMaintenance, 10);

			if (numOfRentDays > 10) {
				throw new RentException("Unfortunately, the maintenance has to be performed first!");
			} else if (DateTime.diffDays(rentDate, lastMaintenance) >= 0
					&& DateTime.diffDays(estimatedReturn, followingMaintenance) >= 0) {
				throw new RentException("Unfortunately, the maintenance has to be performed first!");
			} else if (DateTime.diffDays(rentDate, followingMaintenance) >= 0
					&& DateTime.diffDays(estimatedReturn, new DateTime(followingMaintenance, 10)) >= 0) {
				throw new RentException("Unfortunately, the maintenance has to be performed first!");
			} else {
				this.setStatus(PropertyStatus.Rented);
				RentalRecord newRecord = new RentalRecord(customerID, rentDate, estimatedReturn, this);
				this.addRecord(newRecord);
			}
		}
	}

	public String getAllRentalRecordInfo() {
		String resultingInfo = "";
		if (records.isEmpty()) {
			return "empty";
		} else {
			for (RentalRecord rentalRecord : records) {
				if (rentalRecord != null) {
					resultingInfo += "\n------------------------------\n" + rentalRecord.printDetails()
							+ "\n----------------------------------\n";
				}
			}
		}
		return resultingInfo;
	}

	@Override
	public String toString() {
		return this.uniqueID + ":" + this.streetNumber + ":" + this.streetName + ":" + suburb + ":"
				+ this.getTypeStringFormat() + ":" + numberOfBedroom + ":" + this.getStatusStringFormat() + ":"
				+ this.getPathToImage() + ":" + this.getDescription();
	}

	public String printDetails() {
		return "Property ID: " + this.uniqueID + "\nAddress: " + this.streetNumber + " " + this.streetName + "\nType: "
				+ this.getTypeStringFormat() + "\nBedroom: " + this.getNumberOfBedroom() + "\nStatus: "
				+ this.getStatusStringFormat() + "\nRENTAL RECORD: " + this.getAllRentalRecordInfo();
	}

	public void returnProperty(DateTime returnDate) throws ReturnException {
		int difference = DateTime.diffDays(returnDate, this.getRecords().get(0).getRentDate());
		if (difference < 0) {
			throw new ReturnException("Property can't be returned!");
		} else {
			this.getRecords().get(0).setActualReturn(returnDate);
			this.getRecords().get(0).setRentalFee();
			this.getRecords().get(0).setLateFee();
			this.setStatus(PropertyStatus.Available);
		}
	}

}

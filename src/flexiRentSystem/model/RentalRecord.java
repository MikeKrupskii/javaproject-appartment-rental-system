package flexiRentSystem.model;

public class RentalRecord {

	private String recordID;
	private DateTime rentDate;
	private DateTime estimatedReturn;
	private DateTime actualReturn;
	private String customerID;
	private RentalProperty property;
	private double lateFee;
	private double rentalFee;

	public RentalRecord() {
	}

	public RentalRecord(String customer_ID, DateTime rentDate, DateTime estimatedReturn, RentalProperty property) {
		this.rentDate = rentDate;
		this.estimatedReturn = estimatedReturn;
		this.property = property;
		this.customerID = customer_ID;
		setRecordID();
		setLateFee();
		setRentalFee();
	}

	public RentalRecord(String customer_ID, DateTime rentDate, DateTime estimatedReturn, DateTime actualReturn,
			RentalProperty property) {
		this.rentDate = rentDate;
		this.estimatedReturn = estimatedReturn;
		this.property = property;
		this.customerID = customer_ID;
		this.actualReturn = actualReturn;
		setRecordID();
		setLateFee();
		setRentalFee();
	}

	// public RentalRecord(String customer_ID, DateTime rentDate, DateTime
	// estimatedReturn, DateTime actualReturn,
	// String propertyId) {
	// this.rentDate = rentDate;
	// this.estimatedReturn = estimatedReturn;
	// this.actualReturn = actualReturn;
	// this.customerID = customer_ID;
	// setRecordID(propertyId);
	// setLateFee();
	// setRentalFee();
	// }

	public void setProperty(RentalProperty property) {
		this.property = property;
	}

	public RentalProperty getProperty() {
		return property;
	}

	public void setActualReturn(DateTime actualReturn) {
		this.actualReturn = actualReturn;
	}

	public DateTime getActualReturn() {
		return actualReturn;
	}

	public void setEstimatedReturnDate(int days) {
		this.estimatedReturn = new DateTime(this.rentDate, days);
	}

	public DateTime getEstimatedReturn() {
		return estimatedReturn;
	}

	public void setCustomerID(String ID) {
		this.customerID = ID;
	}

	public DateTime getRentDate() {
		return rentDate;
	}

	public void setRentDate(DateTime date) {
		this.rentDate = date;
	}

	public void setRecordID(String propertyID) {
		recordID = propertyID + "_" + getCustomerID() + "_" + this.getRentDate().getEightDigitDate();
	}

	public void setRecordID() {
		recordID = property.getUniqueID() + "_" + getCustomerID() + "_" + this.getRentDate().getEightDigitDate();
	}

	public String getCustomerID() {
		return customerID;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setLateFee(double lateFee) {
		this.lateFee = lateFee;
	}

	public void setRentalRate(double rate) {
		this.rentalFee = rate;
	}

	public void setLateFee() {
		if (actualReturn == null) {
			lateFee = 0;
		} else {
			int difference = DateTime.diffDays(this.estimatedReturn, this.actualReturn);
			if (difference >= 0) {
				this.lateFee = 0;
			} else {
				if (this.getProperty().getFlexiRentType() == PropertyType.Appartment) {
					this.lateFee = 1.15 * this.getProperty().getRentalRate();
				} else {
					this.lateFee = Math.abs(difference) * 662;
				}
			}
		}

	}

	public double getLateFee() {
		return lateFee;
	}

	public void setRentalFee() {
		if (actualReturn == null) {
			rentalFee = 0;
		} else {
			int difference = DateTime.diffDays(this.actualReturn, this.rentDate);
			this.rentalFee = this.getProperty().getRentalRate() * difference + this.lateFee;
		}
	}

	public double getRentalFee() {
		return rentalFee;
	}

	@Override
	public String toString() {
		String stringActualReturn, stringLateFee, stringRentalFee = "";
		if (this.actualReturn == null) {
			stringActualReturn = "none";
			stringLateFee = "none";
			stringRentalFee = "none";
		} else {
			stringActualReturn = getActualReturn().toString();
			stringLateFee = String.valueOf(lateFee);
			stringRentalFee = String.valueOf(rentalFee);
		}
		return recordID + ":" + rentDate + ":" + estimatedReturn + ":" + stringActualReturn + ":" + stringRentalFee
				+ ":" + stringLateFee;
	}

	public String printDetails() {
		if (this.actualReturn == null) {
			return "Record ID: " + recordID + "\n" + "Rent date: " + rentDate + "\nEstimated return date: "
					+ estimatedReturn;
		} else {
			return "Record ID: " + recordID + "\n" + "Rent date: " + rentDate + "\nEstimated return date: "
					+ estimatedReturn + "\nActual return date: " + actualReturn + "\nRental Fee: " + rentalFee
					+ "\nLate fee: " + lateFee;
		}
	}

}

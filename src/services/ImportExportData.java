package services;

import java.io.IOException;

import flexiRentSystem.model.Appartment;
import flexiRentSystem.model.DateTime;
import flexiRentSystem.model.PremiumSuit;
import flexiRentSystem.model.PropertyStatus;
import flexiRentSystem.model.PropertyType;
import flexiRentSystem.model.RentalProperty;
import flexiRentSystem.model.RentalRecord;

public class ImportExportData {

	static DisplayErrorMessage errorController = new DisplayErrorMessage();

	public static RentalProperty importProperty(String uniqueID, int streetNumber, String streetName, String suburb,
			String type, int numberOfBedrooms, String status, String pathToImage, String description)
			throws IOException {
		RentalProperty property = null;
		try {

			PropertyStatus propertyStatus;
			if (status.equalsIgnoreCase("available")) {
				propertyStatus = PropertyStatus.Available;
			} else if (status.equalsIgnoreCase("rented")) {
				propertyStatus = PropertyStatus.Rented;
			} else {
				propertyStatus = PropertyStatus.UnderMaintenance;
			}
			property = new Appartment(streetNumber, streetName, suburb, numberOfBedrooms, PropertyType.Appartment,
					propertyStatus);
			if (pathToImage.equalsIgnoreCase("No Image Available")) {
				property.setPathToImage("/flexiRentSystem/img/no_image.jpg");
			} else {
				property.setPathToImage(pathToImage);
			}
			property.setDescription(description);

		} catch (Exception ex) {
			errorController.displayError("Invalid entry format");
		}
		return property;
	}

	public static RentalProperty importProperty(String uniqueID, int streetNumber, String streetName, String suburb,
			String type, int numberOfBedrooms, String status, String lastMaintenance, String pathToImage,
			String description) throws IOException {
		RentalProperty property = null;
		try {

			PropertyStatus propertyStatus;
			if (status.equalsIgnoreCase("available")) {
				propertyStatus = PropertyStatus.Available;
			} else if (status.equalsIgnoreCase("rented")) {
				propertyStatus = PropertyStatus.Rented;
			} else {
				propertyStatus = PropertyStatus.UnderMaintenance;
			}
			DateTime lastMaintenaceDate = DateFormatConverter.convertStringToDate(lastMaintenance);
			property = new PremiumSuit(streetNumber, streetName, suburb, PropertyType.PremiumSuit, propertyStatus,
					lastMaintenaceDate);
			if (pathToImage.equalsIgnoreCase("No Image Available")) {
				property.setPathToImage("/flexiRentSystem/img/no_image.jpg");
			} else {
				property.setPathToImage(pathToImage);
			}
			property.setDescription(description);
		} catch (Exception ex) {
			errorController.displayError("Invalid entry format!");
		}
		return property;
	}

	public static RentalRecord importRecord(String recordID, String rentDate, String estimatedReturn,
			String actualReturn, double rentalfee, double latefee, RentalProperty property) throws IOException {
		RentalRecord record = null;
		try {
			String[] recordInfo = recordID.split("_");
			String propertyID = recordInfo[0] + recordInfo[1];
			String customerID = recordInfo[2];
			DateTime rentalDate = DateFormatConverter.convertStringToDate(rentDate);
			DateTime estimatedReturnDate = DateFormatConverter.convertStringToDate(estimatedReturn);
			DateTime actualReturnDate = DateFormatConverter.convertStringToDate(actualReturn);
			record = new RentalRecord(customerID, rentalDate, estimatedReturnDate, actualReturnDate, property);
			System.out.println(record.toString());
		} catch (Exception e) {
			errorController.displayError("Invalid entry format");
		}
		return record;
	}

}

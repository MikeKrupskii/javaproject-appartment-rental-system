package services;

import java.sql.ResultSet;
import java.sql.SQLException;

import databaseRelated.SelectQueries;
import flexiRentSystem.model.Appartment;
import flexiRentSystem.model.DateTime;
import flexiRentSystem.model.PremiumSuit;
import flexiRentSystem.model.PropertyStatus;
import flexiRentSystem.model.PropertyType;
import flexiRentSystem.model.RentalProperty;
import flexiRentSystem.model.RentalRecord;

public class ConvertResultSetToObject {

	public static RentalProperty convertPropertyToObject(ResultSet resultSet) throws SQLException {
		RentalProperty property = null;
		if (resultSet != null) {

			int streetNumber = resultSet.getInt("street_number");
			String streetName = resultSet.getString("street_name");
			String suburb = resultSet.getString("suburb");
			int numOfBedrooms = resultSet.getInt("bedroom_number");
			String type = resultSet.getString("type");
			String stringStatus = resultSet.getString("status");
			DateTime lastMaintenance = DateFormatConverter.convertStringToDate(resultSet.getString("last_maintenance"));
			String imagePath = resultSet.getString("img_path");
			String info = resultSet.getString("description");

			PropertyStatus status = null;
			if (stringStatus.equalsIgnoreCase("Available")) {
				status = PropertyStatus.Available;
			} else if (stringStatus.equalsIgnoreCase("Rented")) {
				status = PropertyStatus.Rented;
			} else {
				status = PropertyStatus.UnderMaintenance;
			}

			if (type.equalsIgnoreCase(PropertyType.Appartment.toString())) {

				property = new Appartment(streetNumber, streetName, suburb, numOfBedrooms, PropertyType.Appartment,
						status);
				property.setPathToImage(imagePath);
				property.setDescription(info);
			} else {
				property = new PremiumSuit(streetNumber, streetName, suburb, PropertyType.PremiumSuit, status,
						lastMaintenance);
				property.setPathToImage(imagePath);
				property.setDescription(info);
			}
		}
		return property;
	}

	public static RentalRecord convertRecordResultSetToObject(ResultSet resultSet) throws SQLException {
		RentalRecord record = null;
		if (resultSet != null) {
			String recordID = resultSet.getString("record_id");
			DateTime rentDate = DateFormatConverter.convertStringToDate(resultSet.getString("rent_date"));
			DateTime estimated = DateFormatConverter.convertStringToDate(resultSet.getString("estimated_return"));
			DateTime actual = DateFormatConverter.convertStringToDate(resultSet.getString("actual_return"));
			String customerID = resultSet.getString("customer_id");
			double rentalFee = resultSet.getDouble("rental_fee");
			double lateFee = resultSet.getDouble("late_fee");
			String propertyID = resultSet.getString("property_id");

			RentalProperty property = null;
			ResultSet propResultSet = SelectQueries.selectPropertyById(propertyID);
			while (propResultSet.next()) {
				property = convertPropertyToObject(propResultSet);
			}

			record = new RentalRecord(customerID, rentDate, estimated, actual, property);
		}
		return record;

	}
}

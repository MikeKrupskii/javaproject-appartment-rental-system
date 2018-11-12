package databaseRelated;

import java.sql.Connection;
import java.sql.Statement;

import exceptions.ReturnException;
import flexiRentSystem.model.RentalProperty;
import flexiRentSystem.model.RentalRecord;

public class InsertRows {

	static final String DB_NAME = "testDB";
	static final String PROPERTIES_TABLE_NAME = "RENTAL_PROPERTY";
	static final String RECORDS_TABLE_NAME = "RENTAL_RECORD";

	public static void insertPropertyRows() throws ReturnException {
		int result = 0;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "";
			for (RentalProperty property : DataGenerator.getAllProperties()) {

				query = "INSERT INTO " + PROPERTIES_TABLE_NAME + " VALUES('" + property.getUniqueID() + "',"
						+ property.getStreetNumber() + "," + "'" + property.getStreetName() + "','"
						+ property.getSuburb() + "'," + property.getNumberOfBedroom() + ",'"
						+ property.getTypeStringFormat() + "','" + property.getStatusStringFormat() + "','"
						+ property.getLastMaintenance() + "','" + property.getPathToImage() + "','"
						+ property.getDescription() + "')";

				result = stmt.executeUpdate(query);
			}
			con.commit();
			System.out.println("Insert into table " + PROPERTIES_TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertRecordsRows() throws ReturnException {

		int result = 0;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "";
			for (RentalProperty property : DataGenerator.getAllProperties()) {

				if (property.getRecords().size() != 0) {
					for (RentalRecord record : property.getRecords()) {
						query = "INSERT INTO " + RECORDS_TABLE_NAME + " VALUES('" + record.getRecordID() + "','"
								+ record.getRentDate() + "','" + record.getEstimatedReturn() + "','"
								+ record.getActualReturn() + "','" + record.getCustomerID() + "','"
								+ record.getProperty().getUniqueID() + "'," + record.getLateFee() + ","
								+ record.getRentalFee() + ")";

						result = stmt.executeUpdate(query);
					}
				}

			}
			con.commit();
			System.out.println("Insert into table " + RECORDS_TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertRecord(RentalRecord record, String propertyID) throws ReturnException {
		int result = 0;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + RECORDS_TABLE_NAME + " VALUES('" + record.getRecordID() + "','"
					+ record.getRentDate() + "','" + record.getEstimatedReturn() + "','" + record.getActualReturn()
					+ "','" + record.getCustomerID() + "','" + propertyID + "'," + record.getLateFee() + ","
					+ record.getRentalFee() + ")";

			result = stmt.executeUpdate(query);

			con.commit();
			System.out.println("Insert into table " + RECORDS_TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void insertProperty(RentalProperty property) throws ReturnException {
		int result = 0;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "INSERT INTO " + PROPERTIES_TABLE_NAME + " VALUES('" + property.getUniqueID() + "',"
					+ property.getStreetNumber() + ",'" + property.getStreetName() + "','" + property.getSuburb() + "',"
					+ property.getNumberOfBedroom() + ",'" + property.getTypeStringFormat() + "','"
					+ property.getStatusStringFormat() + "','" + property.getLastMaintenance() + "','"
					+ property.getPathToImage() + "','" + property.getDescription() + "')";

			result = stmt.executeUpdate(query);

			con.commit();
			System.out.println("Insert into table " + PROPERTIES_TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

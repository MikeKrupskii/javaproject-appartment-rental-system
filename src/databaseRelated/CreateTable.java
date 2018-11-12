package databaseRelated;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

	final static String DB_NAME = "testDB";
	final static String PROPERTIES_TABLE_NAME = "RENTAL_PROPERTY";
	final static String RECORDS_TABLE_NAME = "RENTAL_RECORDS";

	public static void createRentalPropertyTable() {
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("CREATE TABLE RENTAL_PROPERTY (" + "id VARCHAR(20) NOT NULL,"
					+ "street_number INT," + "street_name VARCHAR(50) NOT NULL," + "suburb VARCHAR(20) NOT NULL,"
					+ "bedroom_number INT NOT NULL," + "type VARCHAR(20) NOT NULL," + "status VARCHAR(20) NOT NULL,"
					+ "last_maintenance VARCHAR(20) NOT NULL," + "img_path VARCHAR(50) NOT NULL,"
					+ "description VARCHAR(80) NOT NULL," + "PRIMARY KEY (id))");
			if (result == 0) {
				System.out.println("Table " + PROPERTIES_TABLE_NAME + " has been created successfully");
			} else {
				System.out.println("Table " + PROPERTIES_TABLE_NAME + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createRentalRecordTable() {
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("CREATE TABLE RENTAL_RECORD (" + "record_id VARCHAR(50) NOT NULL,"
					+ "rent_date VARCHAR(20)," + "estimated_return VARCHAR(20)," + "actual_return VARCHAR(20),"
					+ "customer_id VARCHAR(20) NOT NULL," + "property_id VARCHAR(20) NOT NULL," + "late_fee DOUBLE,"
					+ "rental_fee DOUBLE," + "PRIMARY KEY (record_id))");
			if (result == 0) {
				System.out.println("Table " + RECORDS_TABLE_NAME + " has been created successfully");
			} else {
				System.out.println("Table " + RECORDS_TABLE_NAME + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws SQLException {
		createRentalPropertyTable();

		createRentalRecordTable();
	}
}

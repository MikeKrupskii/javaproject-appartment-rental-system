package databaseRelated;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectQueries {

	final static String DB_NAME = "testDB";
	final static String PROPERTY_TABLE_NAME = "RENTAL_PROPERTY";
	final static String RECORDS_TABLE_NAME = "RENTAL_RECORD";

	public static ResultSet selectByType(String propertyType) {

		ResultSet resultSet = null;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + PROPERTY_TABLE_NAME + " WHERE type ='" + propertyType + "'";
			try {
				resultSet = stmt.executeQuery(query);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultSet;
	}

	public static ResultSet selectByStatus(String status) {

		ResultSet resultSet = null;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + PROPERTY_TABLE_NAME + " WHERE status ='" + status + "'";
			try {
				resultSet = stmt.executeQuery(query);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultSet;
	}

	public static ResultSet selectByBedNumber(int number) {

		ResultSet resultSet = null;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + PROPERTY_TABLE_NAME + " WHERE bedroom_number ='" + number + "'";
			try {
				resultSet = stmt.executeQuery(query);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultSet;
	}

	public static ResultSet selectAll() {
		ResultSet resultSet = null;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + PROPERTY_TABLE_NAME;

			resultSet = stmt.executeQuery(query);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultSet;
	}

	public static ResultSet selectPropertyById(String uniqueID) {
		ResultSet resultSet = null;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + PROPERTY_TABLE_NAME + " WHERE id = " + "'" + uniqueID + "'";

			resultSet = stmt.executeQuery(query);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultSet;
	}

	public static ResultSet selectRecordsById(String uniqueID) {
		ResultSet resultSet = null;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + RECORDS_TABLE_NAME + " WHERE property_id = " + "'" + uniqueID + "'";

			resultSet = stmt.executeQuery(query);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultSet;
	}

	public static void printAll() {
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + PROPERTY_TABLE_NAME;

			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while (resultSet.next()) {
					System.out.println(resultSet.getString("id") + " " + resultSet.getString("status"));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void printAllRecords() {
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + RECORDS_TABLE_NAME;

			try (ResultSet resultSet = stmt.executeQuery(query)) {
				while (resultSet.next()) {
					System.out.println(resultSet.getString("property_id") + " " + resultSet.getString("rent_date"));
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

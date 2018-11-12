package databaseRelated;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateQueries {

	static final String DB_NAME = "testDB";
	static final String PROPERTIES_TABLE_NAME = "RENTAL_PROPERTY";
	static final String RECORDS_TABLE_NAME = "RENTAL_RECORD";

	public static void updateRentalStatus(String uniqueID, String status) throws SQLException, ClassNotFoundException {
		int result = 0;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "UPDATE " + PROPERTIES_TABLE_NAME + " SET status = '" + status + "' WHERE id = '" + uniqueID
					+ "';";

			result = stmt.executeUpdate(query);

			con.commit();
			System.out.println("UPDATE of table " + PROPERTIES_TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");
		}
	}

	public static void updateLastMaintenance(String uniqueID, String lastMaintenance)
			throws SQLException, ClassNotFoundException {
		int result = 0;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "UPDATE " + PROPERTIES_TABLE_NAME + " SET last_maintenance = '" + lastMaintenance
					+ "' WHERE id = '" + uniqueID + "';";

			result = stmt.executeUpdate(query);

			con.commit();
			System.out.println("UPDATE of table " + PROPERTIES_TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");
		}
	}

	public static void updateRentalRecord(String uniqueID, String dateStringFormat)
			throws SQLException, ClassNotFoundException {
		int result = 0;
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			String query = "UPDATE " + RECORDS_TABLE_NAME + " SET actual_return = '" + dateStringFormat
					+ "' WHERE record_id = '" + uniqueID + "';";

			result = stmt.executeUpdate(query);

			con.commit();
			System.out.println("UPDATE of table " + PROPERTIES_TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");
		}
	}
}

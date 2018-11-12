package databaseRelated;

import java.sql.Connection;
import java.sql.Statement;

public class DeleteTableProperties {
	public static void deleteEverythingFromProperties() {

		final String DB_NAME = "testDB";
		final String PROPERTIES_TABLE_NAME = "RENTAL_PROPERTY";

		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("DROP TABLE RENTAL_PROPERTY");

			if (result == 0) {
				System.out.println("Table " + PROPERTIES_TABLE_NAME + " has been deleted successfully");
			} else {
				System.out.println("Table " + PROPERTIES_TABLE_NAME + " was not deleted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

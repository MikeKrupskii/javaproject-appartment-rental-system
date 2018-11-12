package databaseRelated;

import java.sql.Connection;
import java.sql.Statement;

public class DeleteTableRecords {

	final static String DB_NAME = "testDB";
	final static String RECORDS_TABLE_NAME = "RENTAL_RECORD";

	public static void dropRecordsTable() {

		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("DROP TABLE RENTAL_RECORD");

			if (result == 0) {
				System.out.println("Table " + RECORDS_TABLE_NAME + " has been deleted successfully");
			} else {
				System.out.println("Table " + RECORDS_TABLE_NAME + " was not deleted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteEverythingFromRecords() {
		try (Connection con = ConnectionTest.getConnection(DB_NAME); Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate("DELETE FROM " + RECORDS_TABLE_NAME);

			if (result == 0) {
				System.out.println("Table " + RECORDS_TABLE_NAME + " HAS NOT BEEN DELETED");
			} else {
				System.out.println("Table " + RECORDS_TABLE_NAME + "  deleted");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

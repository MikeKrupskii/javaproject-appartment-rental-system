package services;

import flexiRentSystem.model.DateTime;

public class DateFormatConverter {

	public static DateTime convertStringToDate(String date) {

		DateTime dateTimeConverted = null;
		if (date.contains("/")) {
			String[] array = date.split("/");
			int day = Integer.parseInt(array[0]);
			int month = Integer.parseInt(array[1]);
			int year = Integer.parseInt(array[2]);
			dateTimeConverted = new DateTime(day, month, year);
		}
		return dateTimeConverted;

	}
}

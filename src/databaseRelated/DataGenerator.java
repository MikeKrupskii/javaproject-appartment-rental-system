package databaseRelated;

import java.util.ArrayList;
import java.util.Random;

import exceptions.ReturnException;
import flexiRentSystem.model.Appartment;
import flexiRentSystem.model.DateTime;
import flexiRentSystem.model.PremiumSuit;
import flexiRentSystem.model.PropertyStatus;
import flexiRentSystem.model.PropertyType;
import flexiRentSystem.model.RentalProperty;
import flexiRentSystem.model.RentalRecord;

public class DataGenerator {

	private static ArrayList<RentalProperty> allProperties = new ArrayList<>();

	public static ArrayList<RentalProperty> getAllProperties() {
		return allProperties;
	}

	public static void generateData() throws ReturnException {
		Appartment appartment1 = new Appartment(618, "Londale Street", "Melbourne", 1, PropertyType.Appartment,
				PropertyStatus.Available);
		appartment1.setPathToImage("/flexiRentSystem/img/1.jpg");
		appartment1.setDescription("Newest appartment with a greatest view ever");
		DateTime estimatedReturn1 = new DateTime(3);
		RentalRecord record1 = new RentalRecord(createRandomCustomerID(), new DateTime(-4), estimatedReturn1,
				estimatedReturn1, appartment1);
		appartment1.addRecord(record1);

		DateTime estimatedReturn2 = new DateTime(3);
		RentalRecord record2 = new RentalRecord(createRandomCustomerID(), new DateTime(), estimatedReturn2,
				new DateTime(5), appartment1);
		appartment1.addRecord(record2);

		Appartment appartment2 = new Appartment(241, "City Road", "Southbank", 1, PropertyType.Appartment,
				PropertyStatus.Available);
		appartment2.setPathToImage("/flexiRentSystem/img/2.jpg");
		Appartment appartment3 = new Appartment(63, "WhiteMan Street", "Southbank", 1, PropertyType.Appartment,
				PropertyStatus.Available);
		appartment3.setPathToImage("/flexiRentSystem/img/3.jpg");
		Appartment appartment4 = new Appartment(127, "City Road Street", "Melbourne", 1, PropertyType.Appartment,
				PropertyStatus.Available);
		appartment4.setPathToImage("/flexiRentSystem/img/4.jpg");
		PremiumSuit suit1 = new PremiumSuit(75, "Power Street & City Road", "Southbank", PropertyType.PremiumSuit,
				PropertyStatus.Available, new DateTime());
		Appartment apartment6 = new Appartment(1, "Mcgoun Street", "Richmond", 3, PropertyType.Appartment,
				PropertyStatus.Available);
		apartment6.setPathToImage("/flexiRentSystem/img/6.jpg");
		Appartment apartment7 = new Appartment(105, "Clarendon Street", "Southbank", 3, PropertyType.Appartment,
				PropertyStatus.Available);
		apartment7.setPathToImage("/flexiRentSystem/img/7.jpg");
		Appartment apartment8 = new Appartment(7, "Riverside Quay", "Southbank", 2, PropertyType.Appartment,
				PropertyStatus.Available);
		apartment8.setPathToImage("/flexiRentSystem/img/8.jpg");
		Appartment apartment9 = new Appartment(160, "Victoria Street", "Carlton", 2, PropertyType.Appartment,
				PropertyStatus.Available);
		apartment9.setPathToImage("/flexiRentSystem/img/9.jpg");
		Appartment apartment10 = new Appartment(13, "Percy Street", "Prahran", 1, PropertyType.Appartment,
				PropertyStatus.Available);
		apartment10.setPathToImage("/flexiRentSystem/img/10.jpg");
		PremiumSuit suite1 = new PremiumSuit(75, "Power Street", "Southbank", PropertyType.PremiumSuit,
				PropertyStatus.Available, new DateTime());
		suite1.setPathToImage("/flexiRentSystem/img/11.jpg");
		PremiumSuit suite2 = new PremiumSuit(53, "Havelock Street", "StKilda", PropertyType.PremiumSuit,
				PropertyStatus.Available, new DateTime());
		suite2.setPathToImage("/flexiRentSystem/img/12.jpg");
		PremiumSuit suite3 = new PremiumSuit(16, "Woorigoleen Road", "Toorak", PropertyType.PremiumSuit,
				PropertyStatus.Available, new DateTime());
		suite3.setPathToImage("/flexiRentSystem/img/13.jpg");
		PremiumSuit suite4 = new PremiumSuit(1, "Marne Street", "SouthYarra", PropertyType.PremiumSuit,
				PropertyStatus.Available, new DateTime());
		suite4.setPathToImage("/flexiRentSystem/img/14.jpg");
		PremiumSuit suite5 = new PremiumSuit(8, "Kavanagh Street", "Southbank", PropertyType.PremiumSuit,
				PropertyStatus.Available, new DateTime());
		suite5.setPathToImage("/flexiRentSystem/img/15.jpg");
		Appartment apartment5 = new Appartment(5, "Northampton Place", "SouthYarra", 2, PropertyType.Appartment,
				PropertyStatus.Available);
		apartment5.setPathToImage("/flexiRentSystem/img/5.jpg");

		allProperties.add(appartment1);
		allProperties.add(appartment2);
		allProperties.add(appartment3);
		allProperties.add(appartment4);
		allProperties.add(apartment5);
		allProperties.add(apartment6);
		allProperties.add(apartment7);
		allProperties.add(apartment8);
		allProperties.add(apartment9);
		allProperties.add(apartment10);
		allProperties.add(suite1);
		allProperties.add(suite2);
		allProperties.add(suite3);
		allProperties.add(suite4);
		allProperties.add(suite5);

	}

	public static String createRandomCustomerID() {
		String customerID = "CUS";
		Random randomGenerator = new Random();
		int value = randomGenerator.nextInt(9999);
		return customerID.concat(String.valueOf(value));
	}

}

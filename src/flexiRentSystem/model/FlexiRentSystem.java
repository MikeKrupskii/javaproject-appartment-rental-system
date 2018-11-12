package flexiRentSystem.model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.MaintenanceException;
import exceptions.RentException;
import exceptions.ReturnException;

public class FlexiRentSystem {

	private RentalProperty[] properties = new RentalProperty[50];
	int lastIndex = 0;
	Scanner input = new Scanner(System.in);

	public void displayMenu() {
		System.out.println("**** FLEXIRENT SYSTEM MENU ****");
		System.out.println("Add property:              1");
		System.out.println("Rent property:             2");
		System.out.println("Return property:           3");
		System.out.println("Property maintenance:      4");
		System.out.println("Complete maintenance:      5");
		System.out.println("Display all properties:    6");
		System.out.println("Exit program:   	   7");
	}

	public void readInputAndPerformActions() throws RentException, ReturnException, MaintenanceException {
		displayMenu();
		int userAnswer = 0;
		while (userAnswer != 7) {
			try {
				System.out.println("Enter your choice: 			");
				userAnswer = input.nextInt();
				if (userAnswer == 1) {
					addProperty();
				}
				if (userAnswer == 2) {
					try {
						rentProperty();
					} catch (RentException ex) {
						System.out.println(ex.getMessage());
						readInputAndPerformActions();
					}
				} else if (userAnswer == 3) {
					try {
						returnProperty();
					} catch (ReturnException ex) {
						System.out.println(ex.getMessage());
						readInputAndPerformActions();
					}
				}
				if (userAnswer == 4) {
					try {
						performMaintenance();
					} catch (MaintenanceException ex) {
						System.out.println(ex.getMessage());
						readInputAndPerformActions();
					}
				}
				if (userAnswer == 5) {
					try {
						completeMaintenance();
					} catch (MaintenanceException ex) {
						System.out.println(ex.getMessage());
						readInputAndPerformActions();
					}
				}
				if (userAnswer == 6) {
					printAllProperties();
				}
				displayMenu();
			} catch (InputMismatchException ex) {
				System.out.println("INVALID INPUT!");
				input.nextLine();
				readInputAndPerformActions();
			}
		}
	}

	public boolean checkIfExists(String ID) {
		for (RentalProperty rentalProperty : properties) {
			if (rentalProperty != null && rentalProperty.getUniqueID().equals(ID)) {
				return true;
			}
		}
		return false;
	}

	public Appartment getDetailsAndCreateAppartment() {
		System.out.println("Enter street number: ");
		int number = input.nextInt();
		System.out.println("Enter street name: ");
		String strName = input.next();
		System.out.println("Enter suburb: ");
		String suburb = "";
		while (suburb.length() < 3) {
			System.out.println("Suburb format must be at least 3 letters long:");
			suburb = input.next();
		}
		System.out.println("Enter number of bedrooms: 1, 2 or 3 ");
		int numOfBedrooms = input.nextInt();
		while (numOfBedrooms <= 0 || numOfBedrooms > 3) {
			System.out.println("You can only choose between 1, 2 or 3 bedrooms!");
			System.out.println("Enter number of bedrooms: 1, 2 or 3 ");
			numOfBedrooms = input.nextInt();
		}
		Appartment appartment = new Appartment(number, strName, suburb, numOfBedrooms, PropertyType.Appartment,
				PropertyStatus.Available);
		return appartment;
	}

	public DateTime convertInputToDate(String date) {
		String[] array = date.split("/");
		int day = Integer.parseInt(array[0]);
		int month = Integer.parseInt(array[1]);
		int year = Integer.parseInt(array[2]);
		DateTime dateTimeConverted = new DateTime(day, month, year);
		return dateTimeConverted;
	}

	public PremiumSuit getDetailsAndCreatePremiumSuit() {
		System.out.println("Enter street number: ");
		int number = input.nextInt();
		System.out.println("Enter street name: ");
		String strName = input.next();
		System.out.println("Enter suburb (must be at least 3 letters long): ");
		String suburb = input.next();
		System.out.println("Enter last maintenace date (dd/mm/year): ");
		String date = input.next();
		DateTime maintenance = convertInputToDate(date);
		PremiumSuit suit = new PremiumSuit(number, strName, suburb, PropertyType.PremiumSuit, PropertyStatus.Available,
				maintenance);
		return suit;
	}

	public void addToArray(RentalProperty property) {
		if (!checkIfExists(property.getUniqueID())) {
			properties[lastIndex] = property;
			lastIndex++;
			System.out.println("You've just created an appartment with the following details: \n-----------------\n"
					+ property.printDetails());
			System.out.println("--------------------");
		} else {
			System.out.println("ERROR: An appartment with following details already exits in the system!!!");
			return;
		}
	}

	public void printAllProperties() {
		if (properties[0] == null) {
			System.out.println("No properties have been created!");
		} else {
			System.out.println("/*****************LIST OF ALL PROPERTIES*****************");
			for (RentalProperty rentalProperty : properties) {
				if (rentalProperty != null) {
					System.out.println("-----------------");
					System.out.println(rentalProperty.printDetails());
					System.out.println("-----------------");
				}
			}
		}
	}

	public void addProperty() {
		System.out.println(
				"Specify the property type that you wish to create? \nEnter 'a' for Appartment and 'p' for Premium Suit");
		char propertyType = input.next().charAt(0);
		if (propertyType == 'a') {
			Appartment newAppartment = getDetailsAndCreateAppartment();
			addToArray(newAppartment);
		} else if (propertyType == 'p') {
			PremiumSuit newSuit = getDetailsAndCreatePremiumSuit();
			addToArray(newSuit);
		} else {
			System.out.println("Unknown character!");
			return;
		}
	}

	public RentalProperty searchAndReturn(String ID) {
		for (RentalProperty rentalProperty : properties) {
			if (rentalProperty != null && rentalProperty.getUniqueID().equals(ID)) {
				return rentalProperty;
			}
		}
		System.out.println("No such property exists in the system!");
		return null;
	}

	public void rentProperty() throws RentException {
		System.out.println("Enter property id: ");
		String propertyID = input.next();
		System.out.println("Enter cstomer id (CUSXXYY): ");
		String customerID = input.next();
		System.out.println("Enter the rent date (dd/mm/year): ");
		String rentString = input.next();
		DateTime rentDate = convertInputToDate(rentString);
		System.out.println("How many days? ");
		int days = input.nextInt();
		RentalProperty propertyToRent = searchAndReturn(propertyID);
		if (propertyToRent != null) {
			try {
				propertyToRent.rent(customerID, rentDate, days);
				if (propertyToRent.getFlexiRentType() == PropertyType.Appartment) {
					System.out.println(
							"Appartment " + propertyToRent.getUniqueID() + " is now rented by customer " + customerID);
				} else {
					System.out.println("Premium Suit " + propertyToRent.getUniqueID() + " is now rented by customer "
							+ customerID);
				}
			}

			catch (RentException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	public void returnProperty() throws ReturnException {
		System.out.println("Enter property id: ");
		String propertyID = input.next();
		System.out.println("Enter the return date (dd/mm/year): ");
		String returnString = input.next();
		DateTime returnDate = convertInputToDate(returnString);
		RentalProperty propertyToReturn = searchAndReturn(propertyID);
		if (propertyToReturn != null) {
			ArrayList<RentalRecord> records = propertyToReturn.getRecords();

			RentalRecord latest = records.get(0);
			if (latest != null) {
				String customerID = latest.getCustomerID();
				try {
					propertyToReturn.returnProperty(returnDate);
					if (propertyToReturn.getFlexiRentType() == PropertyType.Appartment) {
						System.out.println("Appartment " + propertyToReturn.getUniqueID()
								+ " is now returned by customer " + customerID);
					} else {
						System.out.println("Premium Suit " + propertyToReturn.getUniqueID()
								+ " is now returned by customer " + customerID);
					}
					System.out.println("----------PROPERTY DETAILS---------------");
					System.out.println(propertyToReturn.printDetails());
				} catch (ReturnException ex) {
					System.err.println(ex.getMessage());
				}

			} else {
				throw new ReturnException("Property has never been rented!");
			}

		} else {
			throw new ReturnException("Property could not be returned!");
		}

	}

	public void performMaintenance() throws MaintenanceException {
		System.out.println("Enter property id: ");
		String propertyID = input.next();
		RentalProperty property = searchAndReturn(propertyID);
		// property.performMaintenance();
		if (property.getFlexiRentType() == PropertyType.Appartment
				&& property.getStatus() == PropertyStatus.UnderMaintenance) {
			System.out.println("Appartment " + property.getUniqueID() + " is now under maintenace!");
		} else {
			System.out.println("Premium Suit " + property.getUniqueID() + " is now under maintenace!");
		}

	}

	public void completeMaintenance() throws MaintenanceException {
		System.out.println("Enter property id: ");
		String propertyID = input.next();
		RentalProperty property = searchAndReturn(propertyID);
		System.out.println("Enter maintenace completion date: ");
		String date = input.next();
		DateTime completion = convertInputToDate(date);
		try {
			property.completeMaintenance(completion);
			if (property.getFlexiRentType() == PropertyType.Appartment) {
				System.out
						.println("Appartment " + propertyID + " has all maintenance completed and is ready for rent!");
			} else {
				System.out.println(
						"Premium Suit " + propertyID + " has all maintenance completed and is ready for rent!");
			}
		} catch (MaintenanceException ex) {
			System.err.println(ex.getMessage());
		}
	}

}

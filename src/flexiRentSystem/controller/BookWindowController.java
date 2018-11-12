package flexiRentSystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import databaseRelated.InsertRows;
import databaseRelated.SelectQueries;
import databaseRelated.UpdateQueries;
import exceptions.RentException;
import exceptions.ReturnException;
import flexiRentSystem.model.DateTime;
import flexiRentSystem.model.RentalProperty;
import flexiRentSystem.model.RentalRecord;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookWindowController implements Initializable {

	RentalProperty propertyToBook;

	public void setPropertyToBook(RentalProperty propertyToBook) {
		this.propertyToBook = propertyToBook;
	}

	public RentalProperty getPropertyToBook() {
		return propertyToBook;
	}

	@FXML
	private Label propertyID;

	@FXML
	private DatePicker rentDate;

	@FXML
	private TextField numOfDays;

	@FXML
	private TextField customerID;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setPropertyID(String uniqueID) throws ReturnException {
		propertyID.setText(uniqueID);
	}

	@FXML
	public void bookProperty() throws ReturnException, SQLException, NumberFormatException, RentException,
			ClassNotFoundException, IOException {
		//
		// ResultSet resultSet = SelectQueries.selectPropertyById(propertyID.getText());
		// RentalProperty property = null;
		// while (resultSet.next()) {
		// property = ConvertResultSetToObject.convertPropertyToObject(resultSet);
		// }

		int numberOFRentDays = 0;
		int day = 0;
		int month = 0;
		int year = 0;
		try {
			numberOFRentDays = Integer.parseInt(numOfDays.getText());
			day = rentDate.getValue().getDayOfMonth();
			month = rentDate.getValue().getMonthValue();
			year = rentDate.getValue().getYear();

			DateTime rentalDate = new DateTime(day, month, year);
			DateTime estimatedReturn = new DateTime(rentalDate, numberOFRentDays);
			try {
				if (rentDate.getValue() == null || customerID.getText().isEmpty() || numberOFRentDays == 0) {
					displayError("The fields can't stay empty!");
				} else {
					propertyToBook.rent(customerID.getText(), rentalDate, numberOFRentDays);
					RentalRecord record = new RentalRecord(customerID.getText(), rentalDate, estimatedReturn, null,
							propertyToBook);
					UpdateQueries.updateRentalStatus(propertyToBook.getUniqueID(), "Rented");
					InsertRows.insertRecord(record, propertyToBook.getUniqueID());
					SelectQueries.printAll();
				}
			} catch (RentException exception) {
				displayError(exception.getMessage());
			}

		} catch (NumberFormatException ex) {
			displayError("Numerical values allowed only!");
		} catch (NullPointerException ex) {
			displayError("Fields should not be empty!");
		}
	}

	public void displayError(String message) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/flexirentSystem/view/errorPopup.fxml"));
		Parent root = loader.load();
		ErrorPopupController popup = loader.getController();
		popup.setErrorMessage(message);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}

}

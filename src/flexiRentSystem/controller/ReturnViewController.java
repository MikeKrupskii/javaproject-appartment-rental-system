package flexiRentSystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import databaseRelated.SelectQueries;
import databaseRelated.UpdateQueries;
import exceptions.ReturnException;
import flexiRentSystem.model.DateTime;
import flexiRentSystem.model.RentalProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ReturnViewController {

	RentalProperty propertyToReturn;

	public void setPropertyToReturn(RentalProperty propertyToReturn) {
		this.propertyToReturn = propertyToReturn;
	}

	public RentalProperty getPropertyToReturn() {
		return propertyToReturn;
	}

	@FXML
	private Label propertyID;

	@FXML
	private DatePicker actualReturnDate;

	@FXML
	private TextField customerID;

	public void setPropertyID(String uniqueID) throws ReturnException {
		propertyID.setText(uniqueID);
	}

	@FXML
	public void performReturn(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {

		int day = actualReturnDate.getValue().getDayOfMonth();
		int month = actualReturnDate.getValue().getMonthValue();
		int year = actualReturnDate.getValue().getYear();
		DateTime actualReturnDate = new DateTime(day, month, year);

		try {
			propertyToReturn.returnProperty(actualReturnDate);
			UpdateQueries.updateRentalStatus(propertyToReturn.getUniqueID(), "Available");
			UpdateQueries.updateRentalRecord(propertyToReturn.getRecords().get(0).getRecordID(),
					actualReturnDate.getFormattedDate());
			SelectQueries.printAll();
			SelectQueries.printAllRecords();
		} catch (ReturnException exception) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/flexirentSystem/view/errorPopup.fxml"));
			Parent root = loader.load();
			ErrorPopupController popup = loader.getController();
			popup.setErrorMessage(exception.getMessage());
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		}
	}

}

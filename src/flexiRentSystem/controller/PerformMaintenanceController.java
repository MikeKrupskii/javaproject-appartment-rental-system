package flexiRentSystem.controller;

import java.io.IOException;
import java.sql.SQLException;

import databaseRelated.UpdateQueries;
import exceptions.MaintenanceException;
import flexiRentSystem.model.DateTime;
import flexiRentSystem.model.RentalProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PerformMaintenanceController {

	DetailedViewController detailedViewController = new DetailedViewController();

	RentalProperty propertyToMaintain;

	public RentalProperty getPropertyToMaintain() {
		return propertyToMaintain;
	}

	public void setPropertyToMaintain(RentalProperty propertyToMaintain) {
		this.propertyToMaintain = propertyToMaintain;
	}

	@FXML
	private Label propertyID;

	public void setPropertyID(String uniqueID) {
		propertyID.setText(uniqueID);
	}

	@FXML
	private DatePicker startDate;

	@FXML
	void perform(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		try {
			int day = startDate.getValue().getDayOfMonth();
			int month = startDate.getValue().getMonthValue();
			int year = startDate.getValue().getYear();
			DateTime startMaintenance = new DateTime(day, month, year);
			propertyToMaintain.performMaintenance(startMaintenance);
			detailedViewController.setDetailedProperty(propertyToMaintain);
			UpdateQueries.updateRentalStatus(propertyToMaintain.getUniqueID(),
					propertyToMaintain.getStatusStringFormat());
		} catch (MaintenanceException ex) {
			displayError(ex.getMessage());
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

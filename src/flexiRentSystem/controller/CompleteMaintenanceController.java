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

public class CompleteMaintenanceController {

	DetailedViewController detailedViewController = new DetailedViewController();

	RentalProperty propertyToMaintain;

	public RentalProperty getPropertyToMaintain() {
		return propertyToMaintain;
	}

	public void setPropertyToMaintain(RentalProperty propertyToMaintain) {
		this.propertyToMaintain = propertyToMaintain;
	}

	public void setPropertyID(String uniqueID) {
		propertyID.setText(uniqueID);
	}

	@FXML
	private Label propertyID;

	@FXML
	private DatePicker completionDate;

	@FXML
	void complete(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
		try {
			int day = completionDate.getValue().getDayOfMonth();
			int month = completionDate.getValue().getMonthValue();
			int year = completionDate.getValue().getYear();
			DateTime completionDate = new DateTime(day, month, year);
			propertyToMaintain.completeMaintenance(completionDate);
			detailedViewController.setDetailedProperty(propertyToMaintain);
			UpdateQueries.updateRentalStatus(propertyToMaintain.getUniqueID(),
					propertyToMaintain.getStatusStringFormat());
			UpdateQueries.updateLastMaintenance(propertyToMaintain.getUniqueID(), completionDate.getFormattedDate());
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

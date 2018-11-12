package flexiRentSystem.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorPopupController {

	@FXML
	private Label errorLabel;

	public void setErrorMessage(String errorMessage) {
		errorLabel.setText(errorMessage);
	}

}

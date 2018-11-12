package services;

import java.io.IOException;

import flexiRentSystem.controller.ErrorPopupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DisplayErrorMessage {

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

package flexiRentSystem.controller;

import java.io.IOException;

import flexiRentSystem.StartUpClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InstructionsController {
	@FXML
	private Button goBackBtn;

	@FXML
	void goBack(ActionEvent event) throws IOException {
		Stage mainStage = StartUpClass.parentStage;
		Parent root = FXMLLoader.load(getClass().getResource("/flexiRentSystem/view/mainView.fxml"));
		mainStage.getScene().setRoot(root);
	}

}

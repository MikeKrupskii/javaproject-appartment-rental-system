package flexiRentSystem.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import exceptions.ReturnException;
import flexiRentSystem.StartUpClass;
import flexiRentSystem.model.DateTime;
import flexiRentSystem.model.RentalProperty;
import flexiRentSystem.model.RentalRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.DisplayErrorMessage;

public class DetailedViewController implements Initializable {

	MainViewController controller = new MainViewController();
	DisplayErrorMessage message = new DisplayErrorMessage();

	RentalProperty detailedProperty;

	public RentalProperty getDetailedProperty() {
		return detailedProperty;
	}

	public void setDetailedProperty(RentalProperty detailedProperty) {
		this.detailedProperty = detailedProperty;
	}

	@FXML
	private AnchorPane detailedPane;

	@FXML
	private Label propertyID;

	@FXML
	private Label streetInfo;

	@FXML
	private Label suburb;

	@FXML
	private Label bedroomNumber;

	@FXML
	private Label type;

	@FXML
	private Label status;

	@FXML
	private Text description;

	@FXML
	private Label lastMaintenance;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private TableView<RentalRecord> recordsTableView;

	@FXML
	private TableColumn<RentalRecord, String> cusIDcol;

	@FXML
	private TableColumn<RentalRecord, DateTime> rentDateCol;

	@FXML
	private TableColumn<RentalRecord, DateTime> estiamtedReturnCol;

	@FXML
	private TableColumn<RentalRecord, DateTime> actualCol;

	@FXML
	private TableColumn<RentalRecord, Double> rentalFeeCol;

	@FXML
	private TableColumn<RentalRecord, Double> lateFeeCol;

	@FXML
	private Button backBtn;

	@FXML
	private Button bookBtn;

	@FXML
	private Button returnBtn;

	@FXML
	private ComboBox<String> maintenanceBtn;

	@FXML
	private ImageView largerImageView;

	public AnchorPane getDetailedPane() {
		return detailedPane;
	}

	@FXML
	void goBackToMainPage(ActionEvent event) throws IOException {
		Stage mainStage = StartUpClass.parentStage;
		Parent root = FXMLLoader.load(getClass().getResource("/flexiRentSystem/view/mainView.fxml"));
		mainStage.getScene().setRoot(root);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		maintenanceBtn.getItems().add("Perform Maintenance");
		maintenanceBtn.getItems().add("Complete Maintenance");

		maintenanceBtn.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue value, String old, String newS) {
				String maintenanceOption = maintenanceBtn.getSelectionModel().getSelectedItem();
				if (maintenanceOption.equalsIgnoreCase("Perform Maintenance")) {
					try {
						performMaintenance();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					try {
						completeMaintenance();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	public void completeMaintenance() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/flexiRentSystem/view/completeMaintenanceWindow.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		CompleteMaintenanceController controller = loader.getController();
		controller.setPropertyToMaintain(detailedProperty);
		controller.setPropertyID(propertyID.getText());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();

		stage.setOnCloseRequest((e) -> {
			try {
				refresh();
			} catch (IOException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public void performMaintenance() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/flexiRentSystem/view/performMaintenanceWindow.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		PerformMaintenanceController controller = loader.getController();
		controller.setPropertyToMaintain(detailedProperty);
		controller.setPropertyID(propertyID.getText());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();

		stage.setOnCloseRequest((e) -> {
			try {
				refresh();
			} catch (IOException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	public void setValues(RentalProperty property, String largeImage) throws IOException {
		try {
			setDetailedProperty(property);
			propertyID.setText(detailedProperty.getUniqueID());
			streetInfo.setText(detailedProperty.getStreetNumber() + " " + detailedProperty.getStreetName());
			suburb.setText(detailedProperty.getSuburb());
			bedroomNumber.setText(String.valueOf(detailedProperty.getNumberOfBedroom()));
			type.setText(detailedProperty.getTypeStringFormat());
			status.setText(detailedProperty.getStatusStringFormat());
			description.setText(detailedProperty.getDescription());
			lastMaintenance.setText(detailedProperty.getLastMaintenance());
			detailedProperty.setPathToImage(largeImage);
			largerImageView.setImage(new Image(largeImage));
		} catch (Exception ex) {
			message.displayError(ex.getMessage());
		}
	}

	@FXML
	void openBookWindow(ActionEvent event) throws IOException, ReturnException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/flexiRentSystem/view/bookWindow.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		BookWindowController controller = loader.getController();
		controller.setPropertyID(propertyID.getText());
		controller.setPropertyToBook(detailedProperty);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		stage.setOnCloseRequest((e) -> {
			try {
				refresh();
			} catch (IOException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	public void refresh() throws IOException, SQLException {
		controller.displayDetailedWindow(propertyID.getText());
		setValues(detailedProperty, detailedProperty.getPathToImage());
	}

	@FXML
	void returnProperty(ActionEvent event) throws IOException, ReturnException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/flexiRentSystem/view/returnWindow.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		ReturnViewController controller = loader.getController();
		controller.setPropertyToReturn(detailedProperty);
		controller.setPropertyID(propertyID.getText());
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		stage.setOnCloseRequest((e) -> {
			try {
				refresh();
			} catch (IOException | SQLException e1) {
				e1.printStackTrace();
			}
		});
	}

	public void setTableViews(ArrayList<RentalRecord> list) {
		recordsTableView.getItems().setAll(list);
		cusIDcol.setCellValueFactory(new PropertyValueFactory<RentalRecord, String>("customerID"));
		rentDateCol.setCellValueFactory(new PropertyValueFactory<RentalRecord, DateTime>("rentDate"));
		estiamtedReturnCol.setCellValueFactory(new PropertyValueFactory<RentalRecord, DateTime>("estimatedReturn"));
		actualCol.setCellValueFactory(new PropertyValueFactory<RentalRecord, DateTime>("actualReturn"));
		lateFeeCol.setCellValueFactory(new PropertyValueFactory<RentalRecord, Double>("lateFee"));
		rentalFeeCol.setCellValueFactory(new PropertyValueFactory<RentalRecord, Double>("rentalFee"));
	}
}

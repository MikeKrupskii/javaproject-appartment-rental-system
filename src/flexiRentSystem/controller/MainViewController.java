package flexiRentSystem.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import databaseRelated.InsertRows;
import databaseRelated.SelectQueries;
import exceptions.ReturnException;
import flexiRentSystem.StartUpClass;
import flexiRentSystem.model.RentalProperty;
import flexiRentSystem.model.RentalRecord;
import flexiRentSystem.view.SingleBoxView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ConvertResultSetToObject;
import services.DisplayErrorMessage;
import services.ImportExportData;

public class MainViewController implements Initializable {
	DisplayErrorMessage errorController = new DisplayErrorMessage();
	RentalProperty placeholder;

	public void setPlaceholder(RentalProperty placeholder) {
		this.placeholder = placeholder;
	}

	public RentalProperty getPlaceholder() {
		return placeholder;
	}

	@FXML
	private AnchorPane mainContent;

	@FXML
	private Label searchLabel;

	@FXML
	private ComboBox<String> choiceByStatus;

	@FXML
	private ComboBox<String> choiceByBedrooms;

	@FXML
	private ComboBox<String> choiceByType;

	@FXML
	AnchorPane wrapper;

	GridPane allEntries = new GridPane();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadChoiceBoxOptions();
		try {
			selectProperties(SelectQueries.selectAll());
		} catch (SQLException | IOException e) {
			try {
				errorController.displayError(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		manageFilterOptions();
	}

	@FXML
	void exportData(ActionEvent event) throws IOException {
		DirectoryChooser chooser = new DirectoryChooser();
		Stage stage = (Stage) mainContent.getScene().getWindow();
		File folder = chooser.showDialog(stage);
		BufferedWriter writer = null;
		try {
			if (folder != null) {
				File textExportedFile = new File(folder.getAbsolutePath() + "\\exportedData.txt\\");
				writer = new BufferedWriter(new FileWriter(textExportedFile));

				ResultSet allPropertiesSET = SelectQueries.selectAll();

				while (allPropertiesSET.next()) {
					RentalProperty property = ConvertResultSetToObject.convertPropertyToObject(allPropertiesSET);
					ResultSet rentalRecords = SelectQueries.selectRecordsById(property.getUniqueID());
					writer.write(property.toString());
					writer.newLine();
					while (rentalRecords.next()) {
						RentalRecord record = ConvertResultSetToObject.convertRecordResultSetToObject(rentalRecords);
						writer.write(record.toString());
						writer.newLine();
					}

				}

			}
		} catch (NullPointerException ex) {
			errorController.displayError(ex.getMessage());
		} catch (Exception ex) {
			errorController.displayError(ex.getMessage());
		} finally {
			writer.close();
		}
	}

	@FXML
	void importData(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		Stage stage = (Stage) mainContent.getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);

		try {
			if (file != null) {
				File importedFile = new File(file.getAbsolutePath());
				BufferedReader br = new BufferedReader(new FileReader(importedFile));
				String textEntries;
				RentalProperty property = null;
				while ((textEntries = br.readLine()) != null) {
					if (!textEntries.isEmpty()) {
						String[] arrayOfEntries = textEntries.split(":");

						if (!arrayOfEntries[0].contains("CUS")) {

							if (arrayOfEntries[0].startsWith("A")) {
								property = ImportExportData.importProperty(arrayOfEntries[0],
										Integer.parseInt(arrayOfEntries[1]), arrayOfEntries[2], arrayOfEntries[3],
										arrayOfEntries[4], Integer.parseInt(arrayOfEntries[5]), arrayOfEntries[6],
										arrayOfEntries[7], arrayOfEntries[8]);

							} else {
								property = ImportExportData.importProperty(arrayOfEntries[0],
										Integer.parseInt(arrayOfEntries[1]), arrayOfEntries[2], arrayOfEntries[3],
										arrayOfEntries[4], Integer.parseInt(arrayOfEntries[5]), arrayOfEntries[6],
										arrayOfEntries[7], arrayOfEntries[8], arrayOfEntries[9]);
							}
							try {
								InsertRows.insertProperty(property);
								refresh(SelectQueries.selectAll());
							} catch (Exception ex) {
								errorController.displayError(ex.getMessage());
								errorController.displayError("Operation couldn't be performed!");
							}

						} else {
							RentalRecord record = ImportExportData.importRecord(arrayOfEntries[0], arrayOfEntries[1],
									arrayOfEntries[2], arrayOfEntries[3], Double.valueOf(arrayOfEntries[4]),
									Double.valueOf(arrayOfEntries[5]), property);
							try {
								InsertRows.insertRecord(record, record.getProperty().getUniqueID());
							} catch (Exception ex) {
								errorController.displayError(ex.getMessage());
								// errorController.displayError("Operation couldn't be performed!");
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			errorController.displayError(ex.getMessage());
		}
	}

	@FXML
	void quitProgram(ActionEvent event) {
		Stage stage = (Stage) mainContent.getScene().getWindow();
		stage.close();
	}

	public void loadChoiceBoxOptions() {
		choiceByStatus.getItems().add("Available");
		choiceByStatus.getItems().add("Rented");
		choiceByStatus.getItems().add("Maintenance");
		choiceByBedrooms.getItems().add("1");
		choiceByBedrooms.getItems().add("2");
		choiceByBedrooms.getItems().add("3");
		choiceByType.getItems().add("Premium Suit");
		choiceByType.getItems().add("Appartment");
	}

	public void manageFilterOptions() {

		choiceByType.setOnAction(e -> {
			String selectedOption = choiceByType.getSelectionModel().getSelectedItem();
			ResultSet resultSet = SelectQueries.selectByType(selectedOption);
			try {
				selectProperties(resultSet);
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		});

		choiceByStatus.setOnAction(e -> {
			String selectedOption = choiceByStatus.getSelectionModel().getSelectedItem();
			ResultSet resultSet = SelectQueries.selectByStatus(selectedOption);
			try {
				selectProperties(resultSet);
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		});

		choiceByBedrooms.setOnAction(e -> {
			String selectedOption = choiceByBedrooms.getSelectionModel().getSelectedItem();
			int numberOfBedrooms = Integer.parseInt(selectedOption);
			ResultSet resultSet = SelectQueries.selectByBedNumber(numberOfBedrooms);
			try {
				selectProperties(resultSet);
			} catch (SQLException | IOException e1) {
				e1.printStackTrace();
			}
		});

	}

	public void selectProperties(ResultSet resultSet) throws SQLException, IOException {
		mainContent.getChildren().removeAll(allEntries);
		allEntries.getChildren().clear();
		allEntries.setHgap(35);

		int index1 = 0;
		int index2 = 0;
		int count = 0;
		int imagePathCount = 0;

		try {
			while (resultSet.next()) {
				count++;
				imagePathCount++;
				if (count % 2 == 0) {
					index1 += 5;
				} else {
					index1 = 0;
				}
				if (resultSet.getString("img_path").equalsIgnoreCase("/flexiRentSystem/img/no_image.jpg")) {
					allEntries.add(SingleBoxView.generateSingleBox("/flexiRentSystem/img/no_image.jpg",
							resultSet.getString("street_name"), resultSet.getString("description"),
							resultSet.getString("id")), index1, index2);
					imagePathCount--;
				} else {
					allEntries.add(SingleBoxView.generateSingleBox("/flexiRentSystem/img/" + imagePathCount + ".jpg",
							resultSet.getString("street_name"), resultSet.getString("description"),
							resultSet.getString("id")), index1, index2);
				}

				if (count % 2 == 0) {
					index2++;
				}
			}
		} catch (NullPointerException ex) {
			errorController.displayError("Search didn't return any results!");
		}
		mainContent.getChildren().add(allEntries);
	}

	public void bookProperty(String uniqueID) throws IOException, ReturnException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/flexiRentSystem/view/bookWindow.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		BookWindowController controller = loader.getController();
		setPlaceholder(getSpecificProperty(uniqueID));
		controller.setPropertyID(uniqueID);
		controller.setPropertyToBook(placeholder);
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	}

	public RentalProperty getSpecificProperty(String uniqueID) throws SQLException {
		ResultSet propertyResultSet = SelectQueries.selectPropertyById(uniqueID);
		RentalProperty property = null;
		while (propertyResultSet.next()) {
			property = ConvertResultSetToObject.convertPropertyToObject(propertyResultSet);
		}

		ResultSet recordsResultSet = SelectQueries.selectRecordsById(uniqueID);
		while (recordsResultSet.next()) {
			RentalRecord record = ConvertResultSetToObject.convertRecordResultSetToObject(recordsResultSet);
			System.out.println(record.toString());
			property.getRecords().add(0, record);
		}
		return property;
	}

	public void displayDetailedWindow(String uniqueID) throws IOException, SQLException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/flexiRentSystem/view/detailedView.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		DetailedViewController controller = loader.getController();
		setPlaceholder(getSpecificProperty(uniqueID));
		controller.setDetailedProperty(placeholder);
		// 1a.jpg
		String smallImage = getSpecificProperty(uniqueID).getPathToImage();
		String largeImage = smallImage.substring(0, smallImage.length() - 4) + "a.jpg";
		controller.setValues(getSpecificProperty(uniqueID), largeImage);
		controller.setTableViews(placeholder.getRecords());

		Stage detailedStage = StartUpClass.parentStage;
		AnchorPane mainPaneContent = (AnchorPane) detailedStage.getScene().lookup("#mainContent");
		deleteElementsFromDetailedView(detailedStage);
		mainPaneContent.getChildren().setAll(root);

	}

	@FXML
	void openInstructionsWindow(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/flexiRentSystem/view/instructionsView.fxml"));
		Stage detailedStage = StartUpClass.parentStage;
		AnchorPane root = (AnchorPane) loader.load();
		AnchorPane mainPaneContent = (AnchorPane) detailedStage.getScene().lookup("#mainContent");
		deleteElementsFromDetailedView(detailedStage);
		mainPaneContent.getChildren().setAll(root);
	}

	public boolean containsDuplicates(ObservableList<RentalRecord> records, RentalRecord record) {
		boolean containsDuplicates = false;
		for (RentalRecord rentalRecord : records) {
			if (rentalRecord.getCustomerID() == record.getCustomerID()) {
				containsDuplicates = true;
				break;
			}
		}
		return containsDuplicates;
	}

	public void deleteElementsFromDetailedView(Stage viewStage) {

		ComboBox<String> comboboxOne = (ComboBox) viewStage.getScene().lookup("#choiceByStatus");
		comboboxOne.setVisible(false);
		ComboBox<String> comboboxTwo = (ComboBox) viewStage.getScene().lookup("#choiceByBedrooms");
		comboboxTwo.setVisible(false);
		ComboBox<String> comboboxThree = (ComboBox) viewStage.getScene().lookup("#choiceByType");
		comboboxThree.setVisible(false);
		Label search = (Label) viewStage.getScene().lookup("#searchLabel");
		search.setVisible(false);
	}

	public void refresh(ResultSet resultSet) throws SQLException, IOException {
		selectProperties(resultSet);
	}
}

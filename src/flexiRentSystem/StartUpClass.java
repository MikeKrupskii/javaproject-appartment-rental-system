package flexiRentSystem;

import databaseRelated.CreateTable;
import databaseRelated.DataGenerator;
import databaseRelated.InsertRows;
import exceptions.ReturnException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpClass extends Application {

	public static Stage parentStage;

	public static void main(String[] args) throws ReturnException {
		loadDataToDatabaseTables();

		Application.launch(args);
	}

	public static void loadDataToDatabaseTables() throws ReturnException {

//		CreateTable.createRentalPropertyTable();
//		CreateTable.createRentalRecordTable();
//		DataGenerator.generateData();
//		InsertRows.insertPropertyRows();
//		InsertRows.insertRecordsRows();

		// SelectQueries.printAllRecords();
		// DeleteTableRecords.deleteEverythingFromRecords();
		// DeleteTableProperties.deleteEverythingFromProperties();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		parentStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("/flexiRentSystem/view/mainView.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}

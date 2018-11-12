package flexiRentSystem.view;

import java.io.IOException;
import java.sql.SQLException;

import exceptions.ReturnException;
import flexiRentSystem.controller.MainViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SingleBoxView {

	static MainViewController mainController = new MainViewController();

	public static GridPane generateSingleBox(String pathToImage, String streetName, String description,
			String uniqueID) {
		Image image = new Image(pathToImage);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		Text streetLabel = new Text(streetName);
		streetLabel.setFont(new Font(18));
		streetLabel.setFill(Color.BLUE);

		Image infoIcon = new Image("/flexiRentSystem/img/info.png");
		ImageView infoIconView = new ImageView(infoIcon);
		Button infoBtn = new Button("info");
		infoBtn.setId(uniqueID);

		infoBtn.setGraphic(infoIconView);

		Button bookBtn = new Button("Book");

		bookBtn.setPrefWidth(100);
		bookBtn.setPrefHeight(45);
		bookBtn.setId(uniqueID);
		bookBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					mainController.bookProperty(uniqueID);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ReturnException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});

		infoBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					mainController.displayDetailedWindow(uniqueID);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		});

		Text text = new Text();
		text.setText(description);
		text.setFont(new Font(17));
		GridPane gridPane = new GridPane();
		gridPane.setMaxWidth(500);
		gridPane.setPadding(new Insets(50, 50, 30, 30));
		gridPane.setVgap(10);
		gridPane.setHgap(20);
		gridPane.setColumnSpan(imageView, 3);
		gridPane.setColumnSpan(text, 3);
		gridPane.setColumnSpan(streetLabel, 1);
		gridPane.add(imageView, 0, 0);
		gridPane.add(streetLabel, 0, 1);
		gridPane.add(infoBtn, 1, 1);
		gridPane.add(bookBtn, 2, 1);
		gridPane.add(text, 0, 2);
		return gridPane;
	}

}

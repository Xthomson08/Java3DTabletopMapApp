package edu.augustana.Dowitcher3DTTEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import edu.augustana.Dowitcher3DTTEditor.datamodel.*;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;
	private static String prev;
	@SuppressWarnings("exports")
	public static TerrainMap map;
	@SuppressWarnings("exports")
	public static Stage stage;
	public static File savedFile;

	/**
	 * Initializes important things for the app to run, and begins the app
	 * @param stage -- The primary gui stage to hold the app
	 */
	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) throws IOException {
		App.stage = stage;
		scene = new Scene(loadFXML("Intro"), 1500, 700);
		stage.setScene(scene);
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());

		stage.show();

		stage.setOnCloseRequest(event -> {
			event.consume();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText("Unsaved work will disappear.");
			alert.setContentText("Are you sure you want to exit?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				stage.close();
			} else {
			}
		});
	}

	/**
	 * sets the root fxml of the primary stage of the app
	 * @param fxml -- the string name of the new root fxml file 
	 * @throws IOException -- when fxml file does not exist
	 */
	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	/**
	 * Imports a TerrainMap file, which is opened in the editor screen
	 * 
	 * @param file -- terrain map file to import
	 */
	public static void importFile(File file) {
		try {
			map = ProjectIO.loadTerrainMap(file);
			App.setPrev("Intro");
			App.setRoot("EditorScreen");
		} catch (FileNotFoundException e) {
			System.out.println("scanner error");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Sets the current TerrainMap to a new TerrainMap object
	 * 
	 * @param newMap - new TerrainMap
	 */
	@SuppressWarnings("exports")
	public static void setMap(TerrainMap newMap) {
		map = newMap;
	}

	/**
	 * Returns the current TerrainMap being used in the application
	 * 
	 * @return map - TerrainMap
	 */
	@SuppressWarnings("exports")
	public static TerrainMap getMap() {
		return map;
	}

	/**
	 * Launches the Editor window
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch();
	}

	/**
	 * Returns a string representing the previous window view
	 * 
	 * @return prev - window view
	 */
	public static String getPrev() {
		return prev;
	}

	/**
	 * Sets the application's current view as the view before the current. Activated
	 * after pressing a "back" button
	 * 
	 * @param prev - previous view
	 */
	public static void setPrev(String prev) {
		App.prev = prev;
	}

	/**
	 * Opens a new window with a description of the developers
	 * 
	 * @throws IOException
	 */
	public static void openAboutScreen() throws IOException {
		Stage aboutStage = new Stage();
		aboutStage.setTitle("About Dowitcher 3D Tabletop RPG Board Editor");
		Scene aboutScene = new Scene(loadFXML("About"), 600, 400);

		aboutStage.setScene(aboutScene);
		aboutStage.show();

	}

}
package edu.augustana.Dowitcher3DTTEditor;

import java.io.File;

import edu.augustana.Dowitcher3DTTEditor.datamodel.ProjectIO;
import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import javafx.application.Application;
import javafx.stage.Stage;

public class ThreeDTesting extends Application {

	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) throws Exception {

		File file = new File("C:\\Users\\ASUS\\Downloads\\hextest.json");
		TerrainMap map = ProjectIO.loadTerrainMap(file);
		App.setMap(map);
		ThreeDPreviewController preview = new ThreeDPreviewController();
		preview.startHexagonal();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

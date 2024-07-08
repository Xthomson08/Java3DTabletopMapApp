package edu.augustana.Dowitcher3DTTEditor;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import edu.augustana.Dowitcher3DTTEditor.datamodel.ProjectIO;
import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile.SHAPETYPE;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TemplatesController {
	
	@FXML  private Button backButton;
	@FXML  private Button advanceToEditorButton;
	@FXML  private ChoiceBox<String> templatesChoiceBox = new ChoiceBox<>();
	@FXML  private Pane paneToHoldCanvas;
	
	private String[] templates = {"Forests", "Mountains", "Hills", "Ocean", "Desert", "Plains"};
	private MapCanvas templatePreview;
	
	@FXML
	private void initialize() throws IOException {
		templatesChoiceBox.getItems().addAll(templates);
		templatesChoiceBox.getSelectionModel().select(0);
		templatesChoiceBox.setOnAction(e -> changeTemplate());
		App.setMap(new TerrainMap(10, 10, SHAPETYPE.SQUARE));
		templatePreview = new MapCanvas(App.getMap());	
		changeTemplate();
		templatePreview.heightProperty().bind(paneToHoldCanvas.heightProperty());
		templatePreview.widthProperty().bind(paneToHoldCanvas.widthProperty());
		paneToHoldCanvas.getChildren().add(templatePreview);
	}
	
	@FXML
	private void changeTemplate() {
		File file = new File("./src/main/resources/" + templatesChoiceBox.getValue() + ".json");
		TerrainMap template;
		try {
			template = ProjectIO.loadTerrainMap(file);
			templatePreview.setMap(template);
			App.setMap(template);
			templatePreview.draw();
		} catch (IOException e) {
			e.printStackTrace();
    		new Alert(AlertType.WARNING, "Template file does not exist.").show();
		}
	}
	
	@FXML
    private void switchToIntro() throws IOException {
		App.setPrev("Templates");
        App.setRoot("Intro");
    }
  
	@FXML
	private void advanceToEditorAction() throws IOException {
		App.setPrev("Templates");
		App.setRoot("EditorScreen");
	}
    
}
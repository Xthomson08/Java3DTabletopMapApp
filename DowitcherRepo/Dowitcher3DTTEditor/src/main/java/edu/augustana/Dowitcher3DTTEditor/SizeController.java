package edu.augustana.Dowitcher3DTTEditor;

import java.io.IOException;

import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

/**
 * SizeController class handles user input on the size selector fxml page.
 */
public class SizeController {
	@FXML  private TextField widthTextArea;
	@FXML  private TextField heightTextArea;
	@FXML  private Button backButton;
	@FXML  private RadioButton squareTiles;
	@FXML  private RadioButton hexagonalTiles;
	private ToggleGroup editGroup;
	
	@FXML
	private void initialize() {
		editGroup = new ToggleGroup();
		squareTiles.setToggleGroup(editGroup);
		hexagonalTiles.setToggleGroup(editGroup);
		editGroup.selectToggle(squareTiles);
	}

    @FXML
    private void switchToIntro() throws IOException {
    	App.setPrev("SizeSelector");
        App.setRoot("Intro");
    }
    
    @FXML
    private void nextButtonAction() throws IOException {
		App.setPrev("SizeSelector");
    	int mapWidth = Integer.parseInt(widthTextArea.getText());
    	int mapHeight = Integer.parseInt(heightTextArea.getText());

    	if (mapWidth < 1 || mapHeight < 1 || mapHeight > 100 || mapWidth > 100) {
    		new Alert(AlertType.WARNING, "Must input integers between 1 and 100").show();
    	} else {
    		TerrainMap map;
    		if (editGroup.getSelectedToggle() == squareTiles) {
    			map = new TerrainMap(mapWidth, mapHeight, Tile.SHAPETYPE.SQUARE); 
    		}
    		else {
    			map = new TerrainMap(mapWidth, mapHeight, Tile.SHAPETYPE.HEXAGON); 
    		}
    		System.out.println(mapWidth + " " + mapHeight);
    		App.setMap(map);
    		App.setRoot("EditorScreen");
    	}
    }
}
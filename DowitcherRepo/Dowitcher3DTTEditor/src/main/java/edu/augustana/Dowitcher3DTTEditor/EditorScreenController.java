package edu.augustana.Dowitcher3DTTEditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import edu.augustana.Dowitcher3DTTEditor.datamodel.ProjectIO;
import edu.augustana.Dowitcher3DTTEditor.datamodel.RandGen;
import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import edu.augustana.Dowitcher3DTTEditor.tools.AddRemoveTool;
import edu.augustana.Dowitcher3DTTEditor.tools.SelectionTool;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class EditorScreenController {
	@FXML
	private RadioButton addRemoveRadio;
	@FXML
	private Button raiseTileButton;
	@FXML
	private Button lowerTileButton;

	@FXML
	private RadioButton selectTileRadio;
	@FXML
	private ScrollPane paneToHoldCanvas;
	@FXML
	private Slider heightSlider;
	@FXML
	private Button backButton;
	@FXML
	private Button deselectAll;
	@FXML
	private Button SelectAll;
	@FXML
	private Button fillSelectedAreaButton;
	@FXML
	private Button removeInSelectionButton;

	private ToggleGroup editGroup;
	private UndoRedoHandler undoRedoHandler;
	private MapCanvas canvas;

	@FXML
	private void initialize() {
		// Setup tool toggle group
		editGroup = new ToggleGroup();
		addRemoveRadio.setToggleGroup(editGroup);
		selectTileRadio.setToggleGroup(editGroup);
		editGroup.selectToggle(addRemoveRadio);

		canvas = new MapCanvas(App.getMap());
		undoRedoHandler = new UndoRedoHandler(canvas);
		canvas.configureCanvas();
		canvas.draw();
		paneToHoldCanvas.setContent(canvas);
		heightSlider.valueProperty().addListener((ob, oldHeight, newHeight) -> {
			canvas.setSelectedTileHeight(newHeight.doubleValue());
			undoRedoHandler.saveState();
		});

		SelectionTool selectionTool = new SelectionTool(undoRedoHandler);
		AddRemoveTool addRemoveTool = new AddRemoveTool(undoRedoHandler);

		CanvasEditor canvasEditor = new CanvasEditor(addRemoveTool);

		addRemoveRadio.setOnAction(e -> canvasEditor.setTool(addRemoveTool));
		selectTileRadio.setOnAction(e -> canvasEditor.setTool(selectionTool));

		canvas.setOnMouseDragged(e -> canvasEditor.MouseDragged(e));
		canvas.setOnMousePressed(e -> canvasEditor.MousePressed(e));
		canvas.setOnMouseReleased(e -> canvasEditor.MouseReleased(e));

	}

	@FXML
	private void saveAsAction() throws IOException {
		App.setMap(canvas.getMap());
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Project");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Json Files", "*.json"));
		App.savedFile = fileChooser.showSaveDialog(new Stage());

		ProjectIO.saveTerrainMapAsGSON(App.getMap(), App.savedFile);
	}

	@FXML
	private void saveAction() throws IOException {
		App.setMap(canvas.getMap());
		if (App.savedFile == null) {
			saveAsAction();
		} else {
			ProjectIO.saveTerrainMapAsGSON(App.getMap(), App.savedFile);
		}
	}

	@FXML
	private void quitAction() throws IOException {
		App.stage.close();
	}

	@FXML
	private void deselectAllAction() throws IOException {
		canvas.deselectAll();
	}

	@FXML
	private void selectAllAction() throws IOException {
		canvas.selectAll();
	}

	@FXML
	private void exportOBJAction() throws IOException {
		App.setMap(canvas.getMap());
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export Project");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("OBJ Files", "*.OBJ"));
		ProjectIO.saveTerrainMapAsOBJ(App.getMap(), fileChooser.showSaveDialog(new Stage()));
	}

	@FXML
	private void aboutScreen() throws IOException {
		App.openAboutScreen();
	}

	@FXML
	private void clearAllAction() {
		canvas.clearAll();
		undoRedoHandler.saveState();
	}

	@FXML
	private void backToSizeSelector() throws IOException {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("You are about to leave the Editor; Unsaved work will disappear.");
		alert.setContentText("Are you ok with this?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (App.getPrev().equals("Templates")) {
				App.setPrev("EditorScreen");
				App.setRoot("Templates");
			} else {
				App.setPrev("EditorScreen");
				App.setRoot("SizeSelector");
			}
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

//	@SuppressWarnings("exports")
//	public String projectText(ArrayList<ArrayList<Tile>> tiles, TerrainMap map) {
//		return map.toString();
//	}

	@FXML
	private void makeTilesPointyAction() {
		canvas.makeTilesPointy();
		undoRedoHandler.saveState();
	}

	@FXML
	private void raiseSelectedTilesAction() {
		canvas.raiseSelectedTiles();
		undoRedoHandler.saveState();
	}

	@FXML
	private void lowerSelectedTilesAction() {
		canvas.lowerSelectedTiles();
		undoRedoHandler.saveState();
	}

	@FXML
	private void addRowAction() {
		canvas.addRow();
		undoRedoHandler.saveState();
	}

	@FXML
	private void removeRowAction() {
		canvas.removeRow();
		undoRedoHandler.saveState();
	}

	@FXML
	private void addColumnAction() {
		canvas.addColumn();
		undoRedoHandler.saveState();
	}

	@FXML
	private void removeColumnAction() {
		canvas.removeColumn();
		undoRedoHandler.saveState();
	}

	@FXML
	private void removeInSelectionAction() {
		canvas.removeAllSelected();
		undoRedoHandler.saveState();
	}

	@FXML
	private void fillSelectedAreaAction() {
		canvas.addAllSelected();
		undoRedoHandler.saveState();
	}

	@FXML
	private void view3D() {
		App.setMap(canvas.getMap());
		ThreeDPreviewController preview = new ThreeDPreviewController();
		if (App.getMap().getTileShapeType() == Tile.SHAPETYPE.SQUARE) {
			preview.startSquare();
		} else {
			preview.startHexagonal();
		}
	}

	@FXML
	private void randomizeMapAction() {
		canvas.randomizeMap();
		undoRedoHandler.saveState();

	}
	
	@FXML
	private void randomMazeAction() {
		canvas.randomMaze();
		undoRedoHandler.saveState();
	}

	@FXML
	private void redoAction() {
		undoRedoHandler.redo();
	}

	@FXML
	private void undoAction() {
		undoRedoHandler.undo();
//		System.out.println(canvas.getMap());
//		System.out.println(App.getMap());
//		System.out.println("SelectedTiles:" + canvas.getSelectedTiles().toString());
//		System.out.println("SelectedTiles:" + canvas.getSelectedTiles().size());
		canvas.draw();
	}

}

package edu.augustana.Dowitcher3DTTEditor.tools;

import edu.augustana.Dowitcher3DTTEditor.MapCanvas;
import edu.augustana.Dowitcher3DTTEditor.UndoRedoHandler;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SelectionTool extends Tool {
	private Tile start;

	/**
	 * constructor
	 * @param UndoRedoHandler -- handler to allow undos and redos after using the tool
	 */
	public SelectionTool(UndoRedoHandler UndoRedoHandler) {
		super(UndoRedoHandler);
	}

	/**
	 * Selects all tiles from included in the box bounded by the start click and end click as diagonals
	 */
	public void MouseDragged(MouseEvent evt) {
		if (evt.getButton() == MouseButton.SECONDARY) {
			MapCanvas canvas = (MapCanvas) evt.getSource();
			Tile finish = tileAtLocation(evt);
			canvas.selectMultipleTiles(start, finish);
			canvas.draw();
		}
	}

	public void MouseReleased(MouseEvent evt) {

	}

	/**
	 * Selects the tile at the given click location in the canvas
	 */
	public void MousePressed(MouseEvent evt) {
		if (evt.getButton() == MouseButton.SECONDARY) {
			start = tileAtLocation(evt);
		} else {
			MapCanvas canvas = (MapCanvas) evt.getSource();
			Tile tile = tileAtLocation(evt);
			canvas.selectTile(tile);
			canvas.draw();
		}
	}

}
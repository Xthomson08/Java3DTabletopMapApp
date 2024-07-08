package edu.augustana.Dowitcher3DTTEditor.tools;

import edu.augustana.Dowitcher3DTTEditor.App;
import edu.augustana.Dowitcher3DTTEditor.MapCanvas;
import edu.augustana.Dowitcher3DTTEditor.UndoRedoHandler;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import javafx.scene.input.MouseEvent;

public abstract class Tool {
	private UndoRedoHandler UndoRedoHandler;

	/**
	 * constructor
	 * @param UndoRedoHandler -- handler to allow undos and redos after using the tool
	 */
	public Tool(UndoRedoHandler UndoRedoHandler) {
		this.UndoRedoHandler = UndoRedoHandler;
	}

	
	public void MouseDragged(MouseEvent evt) {

	}

	public void MousePressed(MouseEvent evt) {

	}

	public void MouseReleased(MouseEvent evt) {

	}

	/**
	 * Saves the current state of the map (used after an edit is made)
	 */
	public void SaveState() {
		UndoRedoHandler.saveState();
	}

	/**
	 * helper method to get where an event is located in the TerrianMap
	 * @param evt - event to find the map location of 
	 * @return the tile at the location where the event occurred
	 */
	public Tile tileAtLocation(MouseEvent evt) {
		MapCanvas canvas = (MapCanvas) evt.getSource();

		double clickX = evt.getX();
		double clickY = evt.getY();

		int xIndex = (int) (clickX / canvas.getTileWidth());
		int yIndex = (int) (clickY / canvas.getTileLength());
		
		if (!(xIndex >= 0 && xIndex < canvas.getMap().getWidth() && yIndex >= 0 && yIndex < canvas.getMap().getHeight())) 
			return null;

		// Change for when hex map
		if(App.getMap().getTileShapeType() == Tile.SHAPETYPE.HEXAGON) {
			if (yIndex-1 >= 0 && canvas.getMap().getTile(xIndex, yIndex-1).getPolygon(canvas.getTileWidth(), canvas.getTileLength()).contains(clickX, clickY)) { //check top triangle
				yIndex -= 1;
			} else if (xIndex-1 >= 0 && canvas.getMap().getTile(xIndex-1, yIndex).getPolygon(canvas.getTileWidth(), canvas.getTileLength()).contains(clickX, clickY)) { // check bottom triangle
				xIndex -= 1;
			} // else approximation was good.
		}
		
		Tile tile = canvas.getMap().getTile(xIndex, yIndex);
		
		return tile;
	}
}

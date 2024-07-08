package edu.augustana.Dowitcher3DTTEditor.tools;

import edu.augustana.Dowitcher3DTTEditor.MapCanvas;
import edu.augustana.Dowitcher3DTTEditor.UndoRedoHandler;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import javafx.scene.input.MouseEvent;

public class AddRemoveTool extends Tool{	
	
	/**
	 * constructor
	 * @param UndoRedoHandler -- handler to allow undos and redos after using the tool
	 */
	public AddRemoveTool(UndoRedoHandler UndoRedoHandler) {
		super(UndoRedoHandler);
	}
	
	public void MouseDragged(MouseEvent evt) {
		
	}
	
	/**
	 * Sets the tile to exist at the clicked location
	 */
	public void MousePressed(MouseEvent evt) {
		MapCanvas canvas = (MapCanvas) evt.getSource();
		Tile tile = tileAtLocation(evt); 
		canvas.setTileExists(tile);
		canvas.draw();
		SaveState();
	}
	
	public void MouseReleased(MouseEvent evt) {
		
	}
}

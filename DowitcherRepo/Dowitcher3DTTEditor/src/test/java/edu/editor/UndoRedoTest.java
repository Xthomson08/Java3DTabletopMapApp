package edu.editor;

import static org.junit.jupiter.api.Assertions.*;

import edu.augustana.Dowitcher3DTTEditor.App;
import edu.augustana.Dowitcher3DTTEditor.MapCanvas;
import edu.augustana.Dowitcher3DTTEditor.UndoRedoHandler;
import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile.SHAPETYPE;

import org.junit.jupiter.api.Test;

class UndoRedoTest {

	private TerrainMap map;
	private MapCanvas canvas;
	private UndoRedoHandler handler;
	
	private void setup() {
		this.map = new TerrainMap(10, 10, SHAPETYPE.SQUARE);
		App.setMap(map);
		this.canvas = new MapCanvas(map);
		this.handler = new UndoRedoHandler(canvas);
	}
	
	@Test
	void undoAddTile() {
		setup();
		handler.saveState();
		map.getTile(3, 3).setExists(true);
		handler.undo();
		assertEquals(false, map.getTile(3, 3).exists());
	}
	
	

}

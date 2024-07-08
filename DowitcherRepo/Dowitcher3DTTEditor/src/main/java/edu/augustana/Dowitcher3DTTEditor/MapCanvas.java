package edu.augustana.Dowitcher3DTTEditor;

import java.util.ArrayList;
import java.util.HashSet;

import edu.augustana.Dowitcher3DTTEditor.datamodel.RandGen;
import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile.SHAPETYPE;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MapCanvas extends Canvas {
	private GraphicsContext gc;

	private double tileWidth;
	private double tileLength;
	private TerrainMap map;
	private HashSet<Tile> selectedTiles;

	final double MAX_HEIGHT = 100;
	
	/**
	 * constructor
	 * @param map -- The map to represent on the canvas
	 */
	@SuppressWarnings("exports")
	public MapCanvas(TerrainMap map) {
		super();
		gc = this.getGraphicsContext2D();
		this.map = map;
		selectedTiles = new HashSet<Tile>();
		configureCanvas();
	}
	
	/**
	 * Configures the dimensions of the canvas based on the current size and TileShape of the map
	 * Also rests tile tileWidth and tileHeight data fields to represent potentially new sized canvas 
	 */
	public void configureCanvas() {
		if (map.getTileShapeType() == Tile.SHAPETYPE.SQUARE) {
			if (App.getMap().getWidth() >= 50) {
				setWidth(2000);
			} else if (map.getWidth() >= 25) {
				setWidth(1000);
			} else {
				setWidth(500);
			}
			if (map.getHeight() >= 50) {
				setHeight(2000);
			} else if (map.getHeight() >= 25) {
				setHeight(1000);
			} else {
				setHeight(500);
			}
		} else {
			if (map.getWidth() >= 50) {
				setWidth(3000);
			} else if (map.getWidth() >= 25) {
				setWidth(1500);
			} else {
				setWidth(1000);
			}
			if (map.getHeight() >= 50) {
				setHeight(3000);
			} else if (map.getHeight() >= 25) {
				setHeight(1500);
			} else {
				setHeight(1000);
			}
		}

		tileWidth = getWidth() / map.getWidth();
		tileLength = getHeight() / map.getHeight();

	}
	
	/**
	 * Draws the current contents of the TerrainMap as a 2d depth map on the canvas
	 * Lighter tiles have a higher elevation, while darker are lower
	 * "X"s through tiles mean they are "pointy", so RPG minis can't stand on them
	 * Blue indicates a tile does not "exist", so it is not exported (a hole in the map)
	 */
//	@SuppressWarnings("exports")
	public void draw() {
		gc.clearRect(0, 0, getWidth(), getHeight());
		
		ArrayList<double[]> selectedTilesXList = new ArrayList<>();
		ArrayList<double[]> selectedTilesYList = new ArrayList<>();
		
		for (ArrayList<Tile> row : map.getTiles()) {
			for (Tile tile : row) {
				// converting polygon coordinates into double for the canvas
				int[] intXCoords = tile.getPolygon(tileWidth, tileLength).xpoints;
				int[] intYCoords = tile.getPolygon(tileWidth, tileLength).ypoints;
				double[] doubleXCoords = new double[intXCoords.length];
				double[] doubleYCoords = new double[intYCoords.length];
				
				for (int i = 0; i < doubleXCoords.length; i++) {
					doubleXCoords[i] = (double) intXCoords[i];
					doubleYCoords[i] = (double) intYCoords[i];
				}
				
				if (selectedTiles.contains(tile)) { // Stores coords of selected tiles, so save from getting them again later
					selectedTilesXList.add(doubleXCoords);
					selectedTilesYList.add(doubleYCoords);
				}
				// Drawing the shape bounded by coordinates
				gc.setStroke(Color.DARKBLUE);
				gc.setFill(tile.exists() ? Color.grayRgb((int) tile.getZElevation() * 2) : Color.BLUE); // If exists, reflect height of the tile, else draw blue
				gc.fillPolygon(doubleXCoords, doubleYCoords, doubleXCoords.length);
				gc.strokePolygon(doubleXCoords, doubleYCoords, doubleXCoords.length);
				if (tile.getPointy() == true) {
					gc.setStroke(Color.AZURE);
					if (tile.getShapeType() == Tile.SHAPETYPE.HEXAGON) {
						gc.strokeLine(doubleXCoords[1], doubleYCoords[1], doubleXCoords[4], doubleYCoords[4]);
						gc.strokeLine(doubleXCoords[2], doubleYCoords[2], doubleXCoords[5], doubleYCoords[5]);
						gc.strokeLine(doubleXCoords[0], doubleYCoords[0], doubleXCoords[3], doubleYCoords[3]);
					} else {
						gc.strokeLine(doubleXCoords[0], doubleYCoords[0], doubleXCoords[2], doubleYCoords[2]);
						gc.strokeLine(doubleXCoords[1], doubleYCoords[1], doubleXCoords[3], doubleYCoords[3]);
					}
				}
			}
		}
		
		gc.setStroke(Color.YELLOW);
		for (int i = 0; i < selectedTiles.size(); i++) { // Drawing yellow border around selected tiles
			System.out.println();
			gc.strokePolygon(selectedTilesXList.get(i), selectedTilesYList.get(i), selectedTilesXList.get(i).length);
		}
	}

	/**
	 * gets the pixel width of each tile's representation on the canvas
	 * @return a double representing the width of each tile's representation on the canvas
	 */
	public double getTileWidth() {
		return this.tileWidth;
	}

	/**
	 * gets the pixel length of each tile's representation on the canvas
	 * @return a double representing the length of each tile's representation on the canvas
	 */
	public double getTileLength() {
		return this.tileLength;
	}

	/**
	 * Set value of tileWidth field from parameter given
	 * 
	 * @param tileWidth - width of tiles on TerrainMap
	 */
	public void setTileWidth(double tileWidth) {
		this.tileWidth = tileWidth;
	}

	/**
	 * Set value of tileLength field from parameter given
	 * 
	 * @param tileLength - length of tiles on TerrainMap
	 */
	public void setTileLength(double tileLength) {
		this.tileLength = tileLength;
	}

	/**
	 * Sets value of map field from parameter given
	 * 
	 * @param map - TerrainMap Object
	 */
	@SuppressWarnings("exports")
	public void setMap(TerrainMap map) {
		this.map = map;
	}

	/**
	 * sets ZElevation of selected Tile using value of parameter given
	 * 
	 * @param newHeight - Value to set SelectedTileZElevation to
	 */
	public void setSelectedTileHeight(double newHeight) {
		for (Tile tile : selectedTiles) {
			if (!tile.exists()) {
				tile.setExists(true);
			}
			tile.setZElevation(newHeight);
		}
		draw();
	}

	/**
	 * raises Selected Tiles by 5
	 */
	public void raiseSelectedTiles() {
		for (Tile tile : selectedTiles) {
			if (tile.getZElevation() <= (MAX_HEIGHT - 5)) {
				tile.setZElevation(tile.getZElevation() + 5);
			} else {
				tile.setZElevation(MAX_HEIGHT);
			}
		}
		draw();
	}

	/**
	 * lowers Selected Tiles by 5
	 */
	public void lowerSelectedTiles() {
		for (Tile tile : selectedTiles) {
			if (tile.getZElevation() >= 5) {
				tile.setZElevation(tile.getZElevation() - 5);
			} else {
				tile.setZElevation(0);
			}
		}
		draw();
	}

	/**
	 * adds or removes clicked tile from selectedTiles field
	 * 
	 * @param tile - tile that is clicked to be selected or deselected
	 */
	@SuppressWarnings("exports")
	public void selectTile(Tile tile) {
		if (selectedTiles.contains(tile)) {
			selectedTiles.remove(tile);
		} else {
			selectedTiles.add(tile);
		}
	}

	/**
	 * Selects all tiles within the rectangle determined by two diagonal tiles
	 * @param start - the starting tile 
	 * @param finish - the ending tile
	 */
	public void selectMultipleTiles(Tile start, Tile finish) {
		if (finish != null) {
			int startX = Math.min(start.getXIndex(), finish.getXIndex());
			int startY = Math.min(start.getYIndex(), finish.getYIndex());
			int endX = Math.max(start.getXIndex(), finish.getXIndex());
			int endY = Math.max(start.getYIndex(), finish.getYIndex());

			for (int i = startX; i <= endX; i++) {
				for (int j = startY; j <= endY; j++) {
					if (!selectedTiles.contains(map.getTile(i, j))) {
						selectedTiles.add(map.getTile(i, j));
					}
				}
			}
		}
	}
	
	/**
	 * Makes selected tiles pointy
	 */
	public void makeTilesPointy() {
		for (Tile tile : selectedTiles) {
			if (tile.exists() && tile.getPointy() == false) {
				tile.setPointy(true);
			} else {
				tile.setPointy(false);
			}
		}
		draw();

	}

	/**
	 * sets all selected tiles to not exist
	 */
	public void removeAllSelected() {
		for (Tile tile : selectedTiles) {
			if (tile.exists()) {
				tile.setExists(false);
				draw();
			}
		}
	}
	
	/**
	 * sets all selected tiles to exist
	 */
	public void addAllSelected() {
		for (Tile tile : selectedTiles) {
			if (!tile.exists()) {
				tile.setExists(true);
				draw();
			}
		}
	}

	/**
	 * returns hashset of tiles from selectedTiles field
	 * 
	 * @return - HashSet<Tile> hashset of tiles
	 */
	@SuppressWarnings("exports")
	public HashSet<Tile> getSelectedTiles() {
		return selectedTiles;
	}

	/**
	 * 
	 */
	public void deselectAll() {
		selectedTiles.clear();
		draw();
	}

	/**
	 * Selects all tiles in the terrain map
	 */
	public void selectAll() {
		for (int row = 0; row < map.getHeight(); row++) {
			for (int col = 0; col < map.getWidth(); col++) {
				selectedTiles.add(map.getTile(row, col));
				draw();
			}
		}
	}

	/**
	 * Gets the TerrainMap drawn by the canvas
	 * @return the TerrainMap drawn by the canvas
	 */
	@SuppressWarnings("exports")
	public TerrainMap getMap() {
		return map;
	}

	/**
	 * sets a given tile to exist
	 * @param tile -- the tile to set exist
	 */
	@SuppressWarnings("exports")
	public void setTileExists(Tile tile) {
		tile.setExists(!tile.exists());
	}
	
	/**
	 * Adds a row to the working TerrainMap
	 */
	public void addRow() {
		map.addRow();
		System.out.println(map);
		configureCanvas();
		draw();
	}

	/**
	 * Removes a row from the working TerrainMap
	 */
	public void removeRow() {
		map.removeRow();
		System.out.println(map);
		configureCanvas();
		draw();
	}
	
	/**
	 * Adds a column to the working TerrainMap
	 */
	public void addColumn() {
		map.addColumn();
		System.out.println(map);
		configureCanvas();
		draw();
	}

	/**
	 * Removes a column from the working TerrainMap
	 */
	public void removeColumn() {
		map.removeColumn();
		System.out.println(map);
		configureCanvas();
		draw();
	}
	
	/**
	 * Sets all tiles in the map to not exist
	 */
	public void clearAll() {
		map.clearAll();
		draw();
	}

	/**
	 * Sets all tiles in the map to random value such that they have a roughly normal
	 */
	public void randomizeMap() {
		RandGen.randomizeMap(map);
		draw();
	}
	
	/**
	 * Generates a random maze on the map
	 */
	public void randomMaze() {
		RandGen.generateMaze(map);
		draw();
	}

	/**
	 * Generates a new map State; a copy of the map at this exact instant
	 * @return new MapCanvas state
	 */
	public State createMemento() {
		return new State();
	}

	/**
	 * Restores the canvas to a previous map state
	 * @param canvasState -- the state to restore the canvas to
	 */
	public void restoreState(State canvasState) {
		canvasState.restore();
		draw();
	}
	
	public class State {
		private TerrainMap map;
		
		/**
		 * constructor of the MapCanvas State class
		 */
		public State() {
			map = (TerrainMap) MapCanvas.this.map.clone();
		}
		
		/**
		 * Sets the data fields of the current canvas and App class to match that contained in the state
		 */
		public void restore() {
			App.setMap(map.clone());
			setMap(map.clone());
		}
	}

}

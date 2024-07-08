package edu.augustana.Dowitcher3DTTEditor.datamodel;

import java.util.ArrayList;
import java.util.HashSet;

public class TerrainMap implements Cloneable {
	private ArrayList<ArrayList<Tile>> tiles;
	private Tile.SHAPETYPE tileShape = Tile.SHAPETYPE.SQUARE; 
	
	public static final int MAX_SIZE = 100;
	
	/**
	 * constructor
	 * @param width -- width of the map in number of tiles
	 * @param height -- height of the map in number of tiles
	 * @param tileShapeType -- the shape of the tiles to be included in the map
	 */
	public TerrainMap(int width, int height, Tile.SHAPETYPE tileShapeType) {
		// System.out.println("constructor: " + width + " " + height + " " + tileShapeType);
		this.tileShape = tileShapeType;
		tiles = new ArrayList<ArrayList<Tile>>();
		for(int y = 0; y < height; y++ ) {
			tiles.add(new ArrayList<Tile>());
			for (int x = 0; x < width; x++) {
				tiles.get(y).add(new Tile(x, y, tileShapeType)); // TODO: fix x and y in right order?
			}
	    }
	}
	
	/**
	 * Gets the height of the map in tiles
	 * @return the height of the map in tiles
	 */
	public int getHeight() {
		return tiles.size();
	}
	
	/**
	 * Gets the width of the map in tiles
	 * @return the width of the map in tiles
	 */
	public int getWidth() {
		return tiles.get(0).size();
	}
	
	/**
	 * Gets the shape of the tiles included in the map
	 * @return the shape of the tiles included in the map
	 */
	public Tile.SHAPETYPE getTileShapeType() {
		return tileShape;
	}

	/**
	 * @return an 2D array of tile objects representing the map.
	 */
	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}
	
	/**
	 * Sets the 2D arraylist of tiles contained (that basically is the terrain map) to a new 2D array of tiles
	 * @param tiles -- the 2D arraylist of tiles to set the map equal to 
	 */
	public void setTiles(ArrayList<ArrayList<Tile>> tiles) {
		this.tiles = tiles;
	}
	
	/**
	 * gets the tile in the map at a given x and y index
	 * @param xIndex -- the x index of the tile to get
	 * @param yIndex -- the y index of the tile to get
	 * @return the tile at that location
	 */
	public Tile getTile(int xIndex, int yIndex) {
		return tiles.get(yIndex).get(xIndex);
	}
	
	/**
	 * Sets all tiles in the map to not exist
	 */
	public void clearAll() {
		for (ArrayList<Tile> row : tiles) {
			for (Tile tile : row) {
				tile.setExists(false);
			}
		}
	}
	
	/**
	 * Adds a row to the map
	 */
	public void addRow() {
		if (getHeight()+1 > MAX_SIZE)
			return;
		tiles.add(new ArrayList<Tile>()); // Adds a row
		for (int col = 0; col < getWidth(); col++) { // Fills it with tiles
			tiles.get(tiles.size()-1).add(new Tile(col, tiles.size()-1, tileShape));
		}
	}
	
	/**
	 * removes a row from the map
	 */
	public void removeRow() {
		if (getHeight()-1 < 0) 
			return;
		tiles.remove(tiles.get(tiles.size()-1)); // removes a row
	}
	
	/**
	 * adds a column to the map
	 */
	public void addColumn() {
		if (getWidth()+1 > MAX_SIZE)
			return;
		for (int i = 0; i < getHeight(); i++) { // adds a new tile to the end of each row
			tiles.get(i).add(new Tile(tiles.get(i).size(), i, tileShape));
			System.out.println("Added Tile: " + (tiles.get(i).size()-1) + " " + i);
		}
	}
	
	/**
	 * removes a column from the map
	 */
	public void removeColumn() {
		if (getWidth()-1 < 0)
			return;
		for (int i = 0; i < getHeight(); i++) { // removes the last tile in each row
			tiles.get(i).remove(tiles.get(i).get(tiles.get(i).size()-1));
		}
	}
	
	/**
	 * gets the horizontal and vertical neighbors (no diagonal) of a given tile in the map
	 * @param tile -- the tile to get the neighbors of
	 * @return a set of horizontal and vertical neighbors in the map
	 */
	public HashSet<Tile> getNeighbors(Tile tile) {
		HashSet<Tile> neighbors = new HashSet<>();
		if (tile.getXIndex()-1 >= 0) 
			neighbors.add(getTile(tile.getXIndex()-1, tile.getYIndex()));
		if ((tile.getXIndex()+1) < getWidth())
			neighbors.add(getTile(tile.getXIndex()+1, tile.getYIndex()));
		if (tile.getYIndex()-1 >= 0)
			neighbors.add(getTile(tile.getXIndex(), tile.getYIndex()-1));
		if (tile.getYIndex()+1 < getHeight())
			neighbors.add(getTile(tile.getXIndex(), tile.getYIndex()+1));
		return neighbors;
	}
	
	@Override
	public TerrainMap clone() {
		TerrainMap clone = new TerrainMap(getWidth(), getHeight(), getTileShapeType());
		ArrayList<ArrayList<Tile>> clonedTiles = new ArrayList<>();
		for (int row = 0; row < tiles.size(); row++) {
			clonedTiles.add(new ArrayList<Tile>());
			for (Tile tile : tiles.get(row)) {
				clonedTiles.get(row).add(tile.clone());
			}
		}
		clone.setTiles(clonedTiles);
		// System.out.println(clone);
		clone.tileShape = getTileShapeType();
		return clone;
	}
	
	@Override
	public String toString() {
		String message = "";
		for (int i = 0; i < tiles.size(); i++) {
			message = (message + tiles.get(i).toString() + "\n");
		}
		return message;
	}
}

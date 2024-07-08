package edu.augustana.Dowitcher3DTTEditor.datamodel;

import java.awt.Polygon;

public class Tile implements Cloneable {
	public enum SHAPETYPE {
		SQUARE, HEXAGON;
	}

	static final double DEFAULT_HEIGHT = 10;
	private boolean exists;
	private double zElevation;
	private int xIndex;
	private int yIndex;
	private SHAPETYPE shapeType;
	private boolean isPointy;

	public Tile(int xCoord, int yCoord, SHAPETYPE shape) {
		this(false, DEFAULT_HEIGHT, xCoord, yCoord, shape, false);
	}

	private Tile(boolean exists, double zElevation, int xCoord, int yCoord, SHAPETYPE shapeType, boolean isPointy) {
		this.exists = exists;
		this.zElevation = zElevation;
		this.xIndex = xCoord;
		this.yIndex = yCoord;
		this.shapeType = shapeType;
		this.isPointy = isPointy;
	}

	/**
	 * Gets if the tile is supposed to exist (be rendered and exported)
	 * @return if the tile exists or not
	 */
	public boolean exists() {
		return exists;
	}

	/**
	 * Sets if the tile is supposed to exist (be rendered and exported)
	 * @param exists -- if the tile exist or not
	 */
	public void setExists(boolean exists) {
		this.isPointy = false;
		this.exists = exists;
	}

	/**
	 * Gets the vertical dimension of the tile
	 * @return the vertical dimension of the tile
	 */
	public double getZElevation() {
		return zElevation;
	}

	/**
	 * sets the vertical dimension of the tile
	 * @param zElevation -- the vertical dimension of the tile
	 */
	public void setZElevation(double zElevation) {
		this.zElevation = zElevation;
	}

	/**
	 * Gets the tiles x index location in its map
	 * @return the tiles x index location in its map
	 */
	public int getXIndex() {
		return xIndex;
	}

	/**
	 * Gets the tiles y index location in its map
	 * @return the tiles y index location in its map
	 */
	public int getYIndex() {
		return yIndex;
	}

	public void setX(int xCoord) {
		this.xIndex = xCoord;
	}

	/**
	 * Sets the tile's Y location in its map
	 * @param yCoord -- the tile's y location in its map
	 */
	public void setY(int yCoord) {
		this.yIndex = yCoord;
	}

	/**
	 * Gets if the tile is pointy (not able to be stood on by an RPG miniature)
	 * @return if the tile is pointy or not
	 */
	public boolean getPointy() {
		return isPointy;
	}

	/**
	 * Sets if the tile is to be pointy (not able to be stood on by an RPG miniature
	 * @param isPointy - if the tile is pointy or not
	 */
	public void setPointy(boolean isPointy) {
		this.isPointy = isPointy;
	}
	
	/**
	 * Gets the type of shape that the tile is supposed to represent
	 * @return the type of shape the tile is supposed to represent
	 */
	public SHAPETYPE getShapeType() {
		return shapeType;
	}
	
	/**
	 * Gets a polygon object with the same coordinates as the the polygon contained in the MapCanvas. 
	 * 
	 * @param tileWidth -- width of a tile in the mapCanvas in pixels
	 * @param tileLength -- length of a tile in the mapCanvas in pixels
	 * @return
	 */
	public Polygon getPolygon(double tileWidth, double tileLength) {
		int[] xCoords;
		int[] yCoords;
		
		if (this.shapeType == SHAPETYPE.HEXAGON) {
			xCoords = new int[6];
			yCoords = new int[6];
			double yOffset = 0.0;
			if (xIndex % 2 != 0)  // If in odd column, offset y Coordinates by half of a tile length
				yOffset = .5;
			// Calculations for coordinates bounding the hexagon, starting with leftmost point and rotating clockwise
			xCoords[0] = (int) (xIndex * tileWidth); // Left
			xCoords[1] = (int) ((xIndex + 1.0/3.0) * tileWidth); // Top left
			xCoords[2] = (int) ((xIndex + 1) * tileWidth); // Top right
			xCoords[3] = (int) ((xIndex + 4.0/3.0) * tileWidth); // Right
			xCoords[4] = (int) ((xIndex + 1) * tileWidth); // Bottom right
			xCoords[5] = (int) ((xIndex + 1.0/3.0) * tileWidth); // Bottom left
			
			yCoords[0] = (int) ((yIndex + .5 + yOffset) * tileLength);
			yCoords[1] = (int) ((yIndex + yOffset) * tileLength);
			yCoords[2] = (int) ((yIndex + yOffset) * tileLength);
			yCoords[3] = (int) ((yIndex + .5 + + yOffset) * tileLength);
			yCoords[4] = (int) ((yIndex + 1 + yOffset) * tileLength);
			yCoords[5] = (int) ((yIndex + 1 + yOffset) * tileLength);
		} else { // Is a square
			xCoords = new int[4];
			yCoords = new int[4];
			
			xCoords[0] = (int) (xIndex * tileWidth);
			xCoords[1] = (int) ((xIndex+1) * tileWidth);
			xCoords[2] = (int) ((xIndex+1) * tileWidth);
			xCoords[3] = (int) (xIndex * tileWidth);
			
			yCoords[0] = (int) (yIndex * tileLength);
			yCoords[1] = (int) (yIndex * tileLength);
			yCoords[2] = (int) ((yIndex + 1) * tileLength);
			yCoords[3] = (int) ((yIndex + 1) * tileLength);
		}
		
		return new Polygon(xCoords, yCoords, xCoords.length);
	}
	
	@Override
	public Tile clone() {
		try {
			Tile clone = (Tile) super.clone(); // creates a new tile object, so references are different
			// assigns all of clone's data fields to be equal
			clone.exists = this.exists;
			clone.zElevation = this.zElevation;
			clone.xIndex = this.xIndex;
			clone.yIndex = this.yIndex;
			clone.shapeType = this.shapeType;
			clone.isPointy = this.isPointy;
			return clone;
		} catch (CloneNotSupportedException e) {
			assert false;
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String toString() {
		if (exists) {
			return String.format("[%4.1f]", zElevation);
		} else {
			return "[    ]";
		}
	}
}

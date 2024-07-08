package edu.datamodel;

import static org.junit.jupiter.api.Assertions.*;  

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import edu.augustana.Dowitcher3DTTEditor.datamodel.TerrainMap;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile;
import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile.SHAPETYPE;


class MapTest {

	private TerrainMap makeSampleMap() {
		TerrainMap map = new TerrainMap(10, 10, SHAPETYPE.SQUARE);
		return map;
	}
	
	@SuppressWarnings("unused")
	@Test
	void testAddRow() {
		TerrainMap map = makeSampleMap();
		int rowCounter = 0;
		map.addRow();
		for (ArrayList<Tile> tile: map.getTiles()) {
			rowCounter++;
		}
		assertEquals(11, rowCounter);		
	}
	
	@SuppressWarnings("unused")
	@Test
	void testRemoveRow() {
		TerrainMap map = makeSampleMap();
		int rowCounter = 0;
		map.removeRow();
		for (ArrayList<Tile> tile: map.getTiles()) {
			rowCounter++;
		}
		assertEquals(9, rowCounter);	
	}
	
	@SuppressWarnings("unused")
	@Test
	void testAddColumn() {
		TerrainMap map = makeSampleMap();
		int columnCounter = 0;
		map.addColumn();
		for (Tile tile: map.getTiles().get(0)) {
			columnCounter++;
		}
		assertEquals(11, columnCounter);		
	}
	
	@SuppressWarnings("unused")
	@Test
	void testRemoveColumn() {
		TerrainMap map = makeSampleMap();
		int columnCounter = 0;
		map.removeColumn();
		for (Tile tile: map.getTiles().get(0)) {
			columnCounter++;
		}
		assertEquals(9, columnCounter);	
	}
	
	@SuppressWarnings("unused")
	@Test
	void testRemoveRowAfterAddColumn() {
		TerrainMap map = makeSampleMap();
		int rowCounter = 0;
		map.addColumn();
		map.removeRow();
		for (ArrayList<Tile> tile: map.getTiles()) {
			rowCounter++;
		}
		assertEquals(9, rowCounter);	
	}
	
	@SuppressWarnings("unused")
	@Test
	void testRemoveRowAfterRemoveColumn() {
		TerrainMap map = makeSampleMap();
		int rowCounter = 0;
		map.removeColumn();
		map.removeRow();
		for (ArrayList<Tile> tile: map.getTiles()) {
			rowCounter++;
		}
		assertEquals(9, rowCounter);	
	}
	
	@SuppressWarnings("unused")
	@Test
	void testAddRowAfterAddColumn() {
		TerrainMap map = makeSampleMap();
		int rowCounter = 0;
		map.addColumn();
		map.addRow();
		for (ArrayList<Tile> tile: map.getTiles()) {
			rowCounter++;
		}
		assertEquals(11, rowCounter);	
	}
	
	@SuppressWarnings("unused")
	@Test
	void testAddRowAfterRemoveColumn() {
		TerrainMap map = makeSampleMap();
		int rowCounter = 0;
		map.removeColumn();
		map.addRow();
		for (ArrayList<Tile> tile: map.getTiles()) {
			rowCounter++;
		}
		assertEquals(11, rowCounter);	
	}
	
	@SuppressWarnings("unused")
	@Test
	void testRemoveColumnAfterAddRow() {
		TerrainMap map = makeSampleMap();
		int columnCounter = 0;
		map.addRow();
		map.removeColumn();
		for (Tile tile: map.getTiles().get(0)) {
			columnCounter++;
		}
		assertEquals(9, columnCounter);		
	}
	
	@SuppressWarnings("unused")
	@Test
	void testRemoveColumnAfterRemoveRow() {
		TerrainMap map = makeSampleMap();
		int columnCounter = 0;
		map.removeRow();
		map.removeColumn();
		for (Tile tile: map.getTiles().get(0)) {
			columnCounter++;
		}
		assertEquals(9, columnCounter);		
	}
	
	@SuppressWarnings("unused")
	@Test
	void testAddColumnAfterAddRow() {
		TerrainMap map = makeSampleMap();
		int columnCounter = 0;
		map.addRow();
		map.addColumn();
		for (Tile tile: map.getTiles().get(0)) {
			columnCounter++;
		}
		assertEquals(11, columnCounter);		
	}
	
	@SuppressWarnings("unused")
	@Test
	void testAddColumnAfterRemoveRow() {
		TerrainMap map = makeSampleMap();
		int columnCounter = 0;
		map.removeRow();
		map.addColumn();
		for (Tile tile: map.getTiles().get(0)) {
			columnCounter++;
		}
		assertEquals(11, columnCounter);		
	}


}

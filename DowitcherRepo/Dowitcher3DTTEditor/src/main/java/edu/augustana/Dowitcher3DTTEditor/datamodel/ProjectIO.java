package edu.augustana.Dowitcher3DTTEditor.datamodel;

import java.io.File; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.*;

import edu.augustana.Dowitcher3DTTEditor.datamodel.Tile.SHAPETYPE;

//import edu.augustana.Dowitcher3DTTEditor.Gson;
//import edu.augustana.Dowitcher3DTTEditor.GsonBuilder;


public class ProjectIO {
	
	/**
	 * Saves the current state of the canvas as a json file (using gson library)
	 * @param map - map to save
	 * @param mapFile - file to save map to
	 * @throws IOException
	 */
	public static void saveTerrainMapAsGSON(TerrainMap map, File mapFile) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(mapFile);
		String asJson = gson.toJson(map);
		writer.write(asJson);
		writer.close();
		
	}
	
	/**
	 * Saves the provided map as an OBJ file
	 * @param map - the map to save
	 * @param mapFile - file to save the map to
	 * @throws IOException
	 */
	public static void saveTerrainMapAsOBJ(TerrainMap map, File mapFile) throws IOException {
		FileWriter writer = new FileWriter(mapFile);
		writer.append("# " + mapFile + "\n");
		writer.append("# Made with Dowitcher3DTableTopEditor\n");
		writer.append("\n");
		
		writer.append("g terrain map\n");
		writer.append("\n");
		
		if(map.getTileShapeType() == SHAPETYPE.SQUARE) {
			int vCount = 0;
			for(int xIndex = 0; xIndex < map.getTiles().size(); xIndex++) {
				for(int yIndex = 0; yIndex < map.getTiles().get(0).size(); yIndex++) {
					
					if(map.getTile(xIndex, yIndex).exists()) {
						double zElevation = map.getTile(xIndex, yIndex).getZElevation() / 100;
						writer.append("v " + xIndex + " " + yIndex + " 0" + "\n");
						writer.append("v " + xIndex + " " + yIndex + " " + (zElevation) + "\n");
						writer.append("v " + xIndex + " " + (yIndex + 1) + " 0" + "\n");
						writer.append("v " + xIndex + " " + (yIndex + 1) + " " + (zElevation) + "\n");
						writer.append("v " + (xIndex + 1) + " " + yIndex + " 0" + "\n");
						writer.append("v " + (xIndex + 1) + " " + yIndex + " " + (zElevation) + "\n");
						writer.append("v " + (xIndex + 1) + " " + (yIndex + 1) + " 0" + "\n");
						writer.append("v " + (xIndex + 1) + " " + (yIndex + 1) + " " + (zElevation) + "\n");
						
						writer.append("f " + (vCount + 1) + " " + (vCount + 2) + " " + (vCount + 4) + " " + (vCount + 3) + "\n");
						writer.append("f " + (vCount + 3) + " " + (vCount + 7) + " " + (vCount + 8) + " " + (vCount + 4) + "\n");
						writer.append("f " + (vCount + 8) + " " + (vCount + 4) + " " + (vCount + 2) + " " + (vCount + 6) + "\n");
						writer.append("f " + (vCount + 8) + " " + (vCount + 6) + " " + (vCount + 5) + " " + (vCount + 7) + "\n");
						writer.append("f " + (vCount + 5) + " " + (vCount + 7) + " " + (vCount + 3) + " " + (vCount + 1) + "\n");
						writer.append("f " + (vCount + 1) + " " + (vCount + 2) + " " + (vCount + 6) + " " + (vCount + 5) + "\n");
						
						if(map.getTile(xIndex, yIndex).getPointy()) {
							writer.append("v " + (xIndex + .5) + " " + (yIndex + .5) + " " + (zElevation + .5) + "\n");
							
							writer.append("f " + (vCount + 2) + " " + (vCount + 4) + " " + (vCount + 9) + "\n");
							writer.append("f " + (vCount + 2) + " " + (vCount + 6) + " " + (vCount + 9) + "\n");
							writer.append("f " + (vCount + 8) + " " + (vCount + 4) + " " + (vCount + 9) + "\n");
							writer.append("f " + (vCount + 8) + " " + (vCount + 6) + " " + (vCount + 9) + "\n");
							vCount++;
						}
						vCount = vCount + 8;
					}
					
				}	
			}
			
			writer.append("\n");
		}
		else { // Map tileSHAPETYPE is hexagon
			int vCount = 0;
			for(int xIndex = 0; xIndex < map.getTiles().size(); xIndex++) {
				for(int yIndex = 0; yIndex < map.getTiles().get(0).size(); yIndex++) {
					if(map.getTile(xIndex, yIndex).exists()) {
						double zElevation = map.getTile(xIndex, yIndex).getZElevation() / 100;

						if(xIndex % 2 != 0) {
							writer.append("v " + (xIndex + 1.25 - .25 * (xIndex - 1)) + " " + (yIndex + 1) + " 0" + "\n"); // left
							writer.append("v " + (xIndex + 1.25 - .25 * (xIndex - 1)) + " " + (yIndex + 1) + " " + zElevation + "\n"); // left + Z
							writer.append("v " + (xIndex + 1.5 - .25 * (xIndex - 1)) + " " + (yIndex + .5) + " 0" + "\n"); // Top Left
							writer.append("v " + (xIndex + 1.5 - .25 * (xIndex - 1)) + " " + (yIndex + .5) + " " + zElevation + "\n"); // Top Left + Z
							writer.append("v " + (xIndex + 2 - .25 * (xIndex - 1)) + " " + (yIndex + .5) + " 0" + "\n"); // Top Right
							writer.append("v " + (xIndex + 2 - .25 * (xIndex - 1)) + " " + (yIndex + .5) + " " + zElevation + "\n"); // Top Right + Z						
							writer.append("v " + (xIndex + 2.25 - .25 * (xIndex - 1)) + " " + (yIndex + 1) + " 0" + "\n"); // Right
							writer.append("v " + (xIndex + 2.25 - .25 * (xIndex - 1)) + " " + (yIndex + 1) + " " + zElevation + "\n"); // Right + Z		
							writer.append("v " + (xIndex + 2 - .25 * (xIndex - 1)) + " " + (yIndex + 1.5) + " 0" + "\n"); // Bottom Right
							writer.append("v " + (xIndex + 2 - .25 * (xIndex - 1)) + " " + (yIndex + 1.5) + " " + zElevation + "\n"); // Bottom Right + Z		
							writer.append("v " + (xIndex + 1.5 - .25 * (xIndex - 1)) + " " + (yIndex + 1.5) + " 0" + "\n"); // Bottom Left
							writer.append("v " + (xIndex + 1.5 - .25 * (xIndex - 1)) + " " + (yIndex + 1.5) + " " + zElevation + "\n"); // Bottom Left + Z	
							
							writer.append("f " + (vCount + 1) + " " + (vCount + 2) + " " + (vCount + 4) + " " + (vCount + 3) + "\n");
							writer.append("f " + (vCount + 3) + " " + (vCount + 4) + " " + (vCount + 6) + " " + (vCount + 5) + "\n");
							writer.append("f " + (vCount + 5) + " " + (vCount + 6) + " " + (vCount + 8) + " " + (vCount + 7) + "\n");
							writer.append("f " + (vCount + 7) + " " + (vCount + 8) + " " + (vCount + 10) + " " + (vCount + 9) + "\n");
							writer.append("f " + (vCount + 9) + " " + (vCount + 10) + " " + (vCount+ 12) + " " + (vCount + 11) + "\n");
							writer.append("f " + (vCount + 11) + " " + (vCount + 12) + " " + (vCount + 2) + " " + (vCount + 1) + "\n");
							writer.append("f " + (vCount + 2) + " " + (vCount + 4) + " " + (vCount + 6) + " " + (vCount + 8) + " " + (vCount + 10) + " " + (vCount + 12) + "\n");
							writer.append("f " + (vCount + 1) + " " + (vCount + 3) + " " + (vCount + 5) + " " + (vCount + 7) + " " + (vCount + 9) + " " + (vCount + 11) + "\n");	
							
							if(map.getTile(xIndex, yIndex).getPointy()) {
								writer.append("v " + (xIndex + 1.75 - .25 * (xIndex - 1)) + " " + (yIndex + 1) + " " + (zElevation + .5) + "\n"); // Middle Point
								
								writer.append("f " + (vCount + 13) + " " + (vCount + 2) + " " + (vCount + 4) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 4) + " " + (vCount + 6) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 6) + " " + (vCount + 8) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 8) + " " + (vCount + 10) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 10) + " " + (vCount + 12) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 12) + " " + (vCount + 2) + "\n");
								vCount++;
							}
							vCount = vCount + 12;
						}
						
						else {
							writer.append("v " + (xIndex + 1.5 - .25 * xIndex) + " " + (yIndex + 1.5) + " 0" + "\n"); // left
							writer.append("v " + (xIndex + 1.5 - .25 * xIndex) + " " + (yIndex + 1.5) + " " + zElevation + "\n"); // left + Z
							writer.append("v " + (xIndex + 1.75 - .25 * xIndex) + " " + (yIndex + 1) + " 0" + "\n"); // Top Left
							writer.append("v " + (xIndex + 1.75 - .25 * xIndex) + " " + (yIndex + 1) + " " + zElevation + "\n"); // Top Left + Z
							writer.append("v " + (xIndex + 2.25 - .25 * xIndex) + " " + (yIndex + 1) + " 0" + "\n"); // Top Right
							writer.append("v " + (xIndex + 2.25 - .25 * xIndex) + " " + (yIndex + 1) + " " + zElevation + "\n"); // Top Right + Z						
							writer.append("v " + (xIndex + 2.5 - .25 * xIndex) + " " + (yIndex + 1.5) + " 0" + "\n"); // Right
							writer.append("v " + (xIndex + 2.5 - .25 * xIndex) + " " + (yIndex + 1.5) + " " + zElevation + "\n"); // Right + Z		
							writer.append("v " + (xIndex + 2.25 - .25 * xIndex) + " " + (yIndex + 2) + " 0" + "\n"); // Bottom Right
							writer.append("v " + (xIndex + 2.25 - .25 * xIndex) + " " + (yIndex + 2) + " " + zElevation + "\n"); // Bottom Right + Z		
							writer.append("v " + (xIndex + 1.75 - .25 * xIndex) + " " + (yIndex + 2) + " 0" + "\n"); // Bottom Left
							writer.append("v " + (xIndex + 1.75 - .25 * xIndex) + " " + (yIndex + 2) + " " + zElevation + "\n"); // Bottom Left + Z	
							
							writer.append("f " + (vCount + 1) + " " + (vCount + 2) + " " + (vCount + 4) + " " + (vCount + 3) + "\n");
							writer.append("f " + (vCount + 3) + " " + (vCount + 4) + " " + (vCount + 6) + " " + (vCount + 5) + "\n");
							writer.append("f " + (vCount + 5) + " " + (vCount + 6) + " " + (vCount + 8) + " " + (vCount + 7) + "\n");
							writer.append("f " + (vCount + 7) + " " + (vCount + 8) + " " + (vCount + 10) + " " + (vCount + 9) + "\n");
							writer.append("f " + (vCount + 9) + " " + (vCount + 10) + " " + (vCount+ 12) + " " + (vCount + 11) + "\n");
							writer.append("f " + (vCount + 11) + " " + (vCount + 12) + " " + (vCount + 2) + " " + (vCount + 1) + "\n");
							writer.append("f " + (vCount + 2) + " " + (vCount + 4) + " " + (vCount + 6) + " " + (vCount + 8) + " " + (vCount + 10) + " " + (vCount + 12) + "\n");
							writer.append("f " + (vCount + 1) + " " + (vCount + 3) + " " + (vCount + 5) + " " + (vCount + 7) + " " + (vCount + 9) + " " + (vCount + 11) + "\n");	
							
							if(map.getTile(xIndex, yIndex).getPointy()) {
								writer.append("v " + (xIndex + 2 - .25 * xIndex) + " " + (yIndex + 1.5) + " " + (zElevation + .5) + "\n"); // Middle Point
								
								writer.append("f " + (vCount + 13) + " " + (vCount + 2) + " " + (vCount + 4) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 4) + " " + (vCount + 6) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 6) + " " + (vCount + 8) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 8) + " " + (vCount + 10) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 10) + " " + (vCount + 12) + "\n");
								writer.append("f " + (vCount + 13) + " " + (vCount + 12) + " " + (vCount + 2) + "\n");
								vCount++;
							}
							
							vCount = vCount + 12;
						}
					}
				}
			}
			
			writer.append("\n");
			
		}
		
		writer.close();
	}
	
	/**
	 * Loads the map from a json file
	 * @param mapFile -- file to load map from 
	 * @return the TerrainMap saved in the file
	 * @throws IOException -- when the file does not exist
	 */
	public static TerrainMap loadTerrainMap(File mapFile) throws IOException {
		Gson gson = new Gson();
		FileReader reader = new FileReader(mapFile);
		TerrainMap map = gson.fromJson(reader, TerrainMap.class);
		reader.close();
		return map;
	}
	

}

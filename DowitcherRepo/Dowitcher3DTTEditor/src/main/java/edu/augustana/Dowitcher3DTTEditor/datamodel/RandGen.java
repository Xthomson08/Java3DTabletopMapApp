package edu.augustana.Dowitcher3DTTEditor.datamodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class RandGen {
	static final double MAX_HEIGHT = 100;
	static final Random rng = new Random();
	
	/**
	 * randomizes map to heights that fit normal distribution, creating bumpy terrain
	 * @param map -- map to generate over
	 */
	public static void randomizeMap(TerrainMap map) {
		for (ArrayList<Tile> row : map.getTiles()) {
			for (Tile tile : row) {
				tile.setExists(true);
				tile.setZElevation(rng.nextGaussian() * (MAX_HEIGHT / 20) + MAX_HEIGHT / 2);
				if (tile.getZElevation()<=0) {
					tile.setZElevation(0);
				} if ( tile.getZElevation()>=MAX_HEIGHT) {
					tile.setZElevation(MAX_HEIGHT);
				}
			}
		}
	}
	
	/**
	 * Iterative, depth-first search maze generation. 
	 * @param map -- map to overwrite with maze
	 */
	public static void generateMaze(TerrainMap map) {
		map.clearAll();
		// Picks random tile for entrance on  on left edge, and moves one tile inwards 
		// for actual "start" of the generation
		HashSet<Tile> visited = new HashSet<>();
		Stack<Tile> genStack = new Stack<Tile>();
		Tile enter = map.getTile(0, rng.nextInt(map.getHeight()-2) +1);
		enter.setExists(true);
		Tile start= map.getTile(1, enter.getYIndex());
		start.setExists(true);
		visited.add(start);
		genStack.push(start);
		
		while (!genStack.isEmpty()) {
			Tile current = genStack.pop();
			// Making set of unvisited, non-border neighbors
			HashSet<Tile> unvisitedNeighbors = map.getNeighbors(current);
			unvisitedNeighbors.removeAll(visited);
			unvisitedNeighbors.removeIf(neighbor -> (neighbor.getXIndex() == 0 || neighbor.getXIndex() == map.getWidth()-1 
					|| neighbor.getYIndex() == 0 || neighbor.getYIndex() == map.getHeight()-1 ));
			
			if (!unvisitedNeighbors.isEmpty()) { // If tile has unvisited neighbors
				genStack.push(current);
				Object[] neighborArray = unvisitedNeighbors.toArray(); 
				Tile next = (Tile) neighborArray[rng.nextInt(neighborArray.length)]; // pick an unvisited neighbor
				visited.add(next);
				
				HashSet<Tile> nextExistingNeighbors = map.getNeighbors(next);
				System.out.println("Tile " +next.getXIndex() + ", " + next.getYIndex() + ": " + nextExistingNeighbors.size());
				nextExistingNeighbors.removeIf(t -> !(t.exists()));
				System.out.println("Tile " +next.getXIndex() + ", " + next.getYIndex() + ": " + nextExistingNeighbors.size());

				if (!(next.exists()) && nextExistingNeighbors.size() <= 1) {
					next.setExists(true);
					genStack.push(next);
				}
			}
		}
		
		// Makes an exit on right side of map that only has 1 existing neighbor
		Tile exit;
		do {
			exit = map.getTile(map.getWidth()-1, rng.nextInt(map.getHeight()-1)+1);
			HashSet<Tile> exitExistingNeighbors = map.getNeighbors(exit);
			exitExistingNeighbors.removeIf(t -> !(t.exists()));
			if (exitExistingNeighbors.size() == 1)
				exit.setExists(true);
		} while (!exit.exists());
		
		for (ArrayList<Tile> row : map.getTiles()) {
			for (Tile tile : row) {
				if (!tile.exists()) {
					tile.setExists(true);
					tile.setZElevation(50.0);
				} else {
					tile.setZElevation(10.0);
				}
			}
		}
		
	}
	
	
}

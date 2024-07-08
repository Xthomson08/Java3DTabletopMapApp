package edu.augustana.Dowitcher3DTTEditor;

import java.util.Stack;

public class UndoRedoHandler {
	private Stack<MapCanvas.State> undoStack;
	private Stack<MapCanvas.State> redoStack;
	private MapCanvas canvas;
	
	/**
	 * constructor 
	 * 
	 * @param canvas -- canvas to track previous states of
	 */
	public UndoRedoHandler(MapCanvas canvas) {
		undoStack = new Stack<MapCanvas.State>();
		redoStack = new Stack<MapCanvas.State>();
		this.canvas = canvas;
		undoStack.push(canvas.createMemento());
	}
	
	/**
	 * saves the current state of the canvas, and adds that state to the stack of previous states
	 */
	public void saveState() {
		MapCanvas.State canvasState = canvas.createMemento();
		undoStack.push(canvasState);
		redoStack.clear();
	}
	
	/**
	 * reverts to the previous state of the canvas, and adds current state to the redoStack. 
	 */
	public void undo() {
		if (undoStack.size() == 1)
			return;
		MapCanvas.State canvasState = undoStack.pop(); 
		redoStack.push(canvasState); 
		canvas.getSelectedTiles().clear();
		canvas.restoreState(undoStack.peek());
	}
	
	
	/**
	 * reverts to previous state of the canvas, before an performing the last undo
	 */
	public void redo() {
		if (redoStack.isEmpty())
			return;
		MapCanvas.State canvasState = redoStack.pop();
		undoStack.push(canvasState);
		canvas.getSelectedTiles().clear();
		canvas.restoreState(canvasState);
	}
}


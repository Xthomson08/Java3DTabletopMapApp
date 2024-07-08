package edu.augustana.Dowitcher3DTTEditor;

import edu.augustana.Dowitcher3DTTEditor.tools.Tool;
import javafx.scene.input.MouseEvent;

public class CanvasEditor {
	private Tool currentTool;
	
	/**
	 * constructor
	 * @param selectedTool -- current selected tool of the canvas editor object. Uses this to determine what clicks do on canvas
	 */
	@SuppressWarnings("exports")
	public CanvasEditor(Tool selectedTool) {
		this.currentTool = selectedTool;
	}
	
	/**
	 * calls the mouse dragged method of the editor's current tool
	 * @param evt -- the mouse event the tool is acting on
	 */
	@SuppressWarnings("exports")
	public void MouseDragged(MouseEvent evt) {
		currentTool.MouseDragged(evt);
	}
	
	/**
	 * Calls the mouse pressed method of the editor's current tool
	 * @param evt -- the mouse event the tool is acting on
	 */
	@SuppressWarnings("exports")
	public void MousePressed(MouseEvent evt) {
		currentTool.MousePressed(evt);
	}
	
	/**
	 * Calls the mouse released method of the current tool
	 * @param evt -- the mouse event the tool is acting on
	 */
	@SuppressWarnings("exports")
	public void MouseReleased(MouseEvent evt) {
		currentTool.MouseReleased(evt);
	}
	
	/**
	 * sets the current tool to a different Tool object
	 * @param selectedTool -- new tool to set current tool to
	 */
	@SuppressWarnings("exports")
	public void setTool(Tool selectedTool) {
		this.currentTool = selectedTool;
	}
	
	
}

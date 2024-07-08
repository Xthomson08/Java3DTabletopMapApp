package edu.augustana.Dowitcher3DTTEditor;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IntroController {
	@FXML 
	private Button openExistingButton;
	@FXML
	private Button toSizeSelectorButton;
	@FXML
	private Button templatesButton;
	@FXML
	private Stage stage;

    @FXML
    private void openExistingFile() throws IOException {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Existing Project Text File");
    	App.savedFile = fileChooser.showOpenDialog(stage);
    	App.importFile(App.savedFile);
    }
    
    @FXML
    private void switchToSizeSelector() throws IOException {
    	App.setPrev("Intro");
    	App.setRoot("SizeSelector");
    }
    
    @FXML
    private void switchToTemplates() throws IOException {
    	App.setPrev("Intro");
    	App.setRoot("Templates");
    }
    
    @FXML
    private void quitAction() throws IOException {
    	App.stage.close();
    }
    
    @FXML
    private void openAboutScreen() throws IOException{
    	App.openAboutScreen();
    }
}

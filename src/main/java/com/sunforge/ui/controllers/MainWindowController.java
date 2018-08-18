package com.sunforge.ui.controllers;

import com.sunforge.App;
import com.sunforge.logic.Manager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea decklistTextArea;

    @FXML
    private TextField savePathField;

    @FXML
    private Button savePathButton;

    @FXML
    private Button generateButton;

    @FXML
    void initialize() {
        assert decklistTextArea != null : "fx:id=\"decklistTextArea\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert savePathButton != null : "fx:id=\"savePathButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert savePathField != null : "fx:id=\"savePathField\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert generateButton != null : "fx:id=\"generateButton\" was not injected: check your FXML file 'mainWindow.fxml'.";

        savePathButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text format", "*.txt"));
            File saveToFile = fileChooser.showSaveDialog(App.getMainStage());
            if (saveToFile != null){
                savePathField.setText(saveToFile.getAbsolutePath());
            }
        });

        generateButton.setOnAction(event -> {
            Manager.processData(decklistTextArea.getText());
        });

    }
}

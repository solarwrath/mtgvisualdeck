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
import java.util.logging.Logger;

import static com.sunforge.logic.Validator.isValidFilePath;

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
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private Button generateButton;

    private static Logger log = Logger.getLogger(MainWindowController.class.getName());

    @FXML
    void initialize() {
        assert decklistTextArea != null : "fx:id=\"decklistTextArea\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert savePathButton != null : "fx:id=\"savePathButton\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert savePathField != null : "fx:id=\"savePathField\" was not injected: check your FXML file 'mainWindow.fxml'.";
        assert generateButton != null : "fx:id=\"generateButton\" was not injected: check your FXML file 'mainWindow.fxml'.";

        savePathButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
            File saveToFile = fileChooser.showSaveDialog(App.getMainStage());
            if (saveToFile != null) {
                savePathField.setText(saveToFile.getAbsolutePath());
                log.info("User chose save path");
            }
        });

        generateButton.setOnAction(event -> {

            //Validation

            if (isValidFilePath(savePathField.getText())) {
                savePathField.setPromptText("");
                savePathField.getStyleClass().remove("error");
                log.info("Save path passed the validation");
                Manager.processData(decklistTextArea.getText(), titleField.getText(), authorField.getText());
            } else {
                savePathField.setPromptText("Invalid file path!");
                savePathField.getStyleClass().add("error");
                log.warning("Users save path was wrong");
            }

        });

    }
}

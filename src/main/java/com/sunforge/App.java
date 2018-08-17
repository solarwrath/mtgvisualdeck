package com.sunforge;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 */
public class App extends Application {
    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        App.mainStage = mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setMainStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/mainWindow.fxml"));
        Parent root = (Parent) loader.load();
        mainStage.setTitle("MtG: Deck Visualizer");
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }
}

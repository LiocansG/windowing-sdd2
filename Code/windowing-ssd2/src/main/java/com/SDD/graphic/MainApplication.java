package com.SDD.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The `MainApplication` class is a JavaFX application class that extends the `Application` class.
 * It sets up the main stage of the application by loading an FXML file, creating an `Alert` instance,
 * setting the title of the primary stage, and showing the scene.
 */
public class MainApplication extends Application {

    private static Stage stage;
    private static Alert alert;

    /**
     * The main method that launches the application.
     *
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * The overridden `start` method of the `Application` class. It sets up the main stage of the application
     * by loading an FXML file, creating an `Alert` instance, setting the title of the primary stage, and showing
     * the scene.
     *
     * @param primaryStage The primary stage of the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        primaryStage.setTitle("Windowing Project");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Returns the main stage of the application.
     *
     * @return The primary stage of the application.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Returns the `Alert` instance that can be used to display error messages in the application.
     *
     * @return The `Alert` instance that can be used to display error messages in the application.
     */
    public static Alert getAlert(){
        return alert;
    }

}

package org.grupa5.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;


    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("primary"));
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setResizable(false);
        stage.getIcons().add(new Image(App.class.getResourceAsStream( "sudokuTaskbarIcon.png" )));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public void reload(String fxmlFileName) throws IOException {
        stage.getScene().setRoot(loadFXML(fxmlFileName));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"), resourceBundle);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
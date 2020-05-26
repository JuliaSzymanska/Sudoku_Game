package org.grupa5.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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


    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("primary"));
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setResizable(false);
        // TODO: 26.05.2020 Pani artystko, ustaw jak ci sie lepiej wydaje
//        stage.initStyle(StageStyle.UNDECORATED);
        // TODO: to jest ikonka ta windowsowa, takie co sie pokazuja na pasku na dole i na pasku aplikacji po lewej
        //  pobrałem z neta, jeśli ci się chce możesz zrobić własną
        stage.getIcons().add(new Image(App.class.getResourceAsStream( "IconZNeta.png" )));

        stage.setScene(scene);
        stage.show();
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
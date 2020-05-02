package org.grupa5.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

// TODO: SZUKAM ZRODEL:
//  http://ggoralski.pl/?p=1952
//  w filmiku na wikampie chłop modyfikuje klasę bazową to chyba nie bałdzo
//  https://www.dummies.com/programming/java/javafx-binding-properties/

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // TODO: ustawic wszystkie stringi na takie brane z resource bundle
    //  oraz dokonczyc sam resource bundle

    // TODO: dodać  zasób bazujący na ListResourceBundle

    // TODO: dodać kontrolkę przełączającą resourceBundle

    // TODO: dodać dwustronne wiązanie sudoku board i naszej planszy w gui

    // TODO: zapis i wczytywanie prawie dokonczone, wiecej tam gdzie jest implementacja

    private static Parent loadFXML(String fxml) throws IOException {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang", new Locale("en_EN"));
        // TODO: dlaczego default loaduje jako PL? bo jesteśmy w pl?
        //  Z jakiegos powodu jedyny resource jaki mi dziala w resource bundlu to jest 'start' reszta NIE DZIAA
        // ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
        // ResourceBundle resourceBundle = ResourceBundle.getBundle("Lang", new Locale("pl_PL"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml")); //, resourceBundle);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
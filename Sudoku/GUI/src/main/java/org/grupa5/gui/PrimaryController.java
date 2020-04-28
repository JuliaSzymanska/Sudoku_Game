package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

    @FXML
    private ComboBox<Level> boxLevel = new ComboBox<>();

    public enum Level{
        Easy(10),
        Medium(20),
        Hard(30);

        private final int number;

        public int getNumber(){
            return number;
        }

        Level(int number) {
            this.number = number;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        boxLevel.setItems( FXCollections.observableArrayList(Level.values()));
        boxLevel.getItems().addAll(Level.values());
        boxLevel.setValue(Level.Easy);

    }

    @FXML
    private void switchToSecondary() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        Parent rootParent = (Parent) loader.load();
        SecondaryController second = loader.getController();
        second.setNumberOfFields(boxLevel.getSelectionModel().getSelectedItem().getNumber());
        Stage stage = new Stage();
        stage.setScene(new Scene(rootParent));
        stage.show();

//        App.setRoot("secondary");
    }
}

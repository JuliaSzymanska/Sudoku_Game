package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class PrimaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ComboBox<Level> boxLevel = new ComboBox<>();
//        boxLevel.setItems( FXCollections.observableArrayList( List.values()));
    }

    public enum Level{
        Easy, Medium, High
    }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}

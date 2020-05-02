package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class PrimaryController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        boxLevel.setItems( FXCollections.observableArrayList(Level.values()));
    }

    @FXML
    private void switchToSecondary() throws IOException {
//        System.out.println(Level.Easy.getNumber());
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
//        Parent rootParent = (Parent) loader.load();
//        SecondaryController second = loader.getController();
//        second.setNumberOfFields(boxLevel.getSelectionModel().getSelectedItem().getNumber());
//        Stage stage = new Stage();
//        stage.setScene(new Scene(rootParent));
//        stage.show();
        App.setRoot("secondary");
    }
}

package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PrimaryController implements Initializable {

    @FXML
    private Label authors;

    @FXML
    private Label author_1;

    @FXML
    private Label author_2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resourceBundle = ResourceBundle.getBundle("Lang", VariablesCollection.getLocale());
        ResourceBundle resourceAuthors
                = ResourceBundle.getBundle("org.grupa5.gui.resourceBundle.authors", VariablesCollection.getLocale());
        System.out.println(resourceAuthors);
        authors.setText(resourceAuthors.getString("Authors: "));
        author_1.setText(resourceAuthors.getString("Julia Szymanska"));
        author_2.setText(resourceAuthors.getString("Przemyslaw Zdrzalik"));
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}

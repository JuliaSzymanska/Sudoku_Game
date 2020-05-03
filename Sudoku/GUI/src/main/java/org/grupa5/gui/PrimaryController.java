package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrimaryController implements Initializable {

    @FXML
    private Label authors;

    @FXML
    private Label author_1;

    @FXML
    private Label author_2;

    @FXML
    private Button primaryButton;

    @FXML
    private Button language;


    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    public void changeLanguage() {
        // TODO: Julka popraw to proszę, boli mnie ten IF ale narazie dziala to zostawilem
        if (VariablesCollection.getLocale().toString().equals("en_en")) {
            VariablesCollection.setLocale(new Locale("pl_PL"));
        } else {
            VariablesCollection.setLocale(new Locale("en_EN"));
        }
        resourceBundle = ResourceBundle.getBundle("Lang", VariablesCollection.getLocale());
        updateLanguage();
    }

    private void updateLanguage() {
        ResourceBundle resourceAuthors
                = ResourceBundle.getBundle("org.grupa5.gui.resourceBundle.authors", VariablesCollection.getLocale());
        authors.setText(resourceAuthors.getString("Authors: "));
        author_1.setText(resourceAuthors.getString("Julia Szymanska"));
        author_2.setText(resourceAuthors.getString("Przemyslaw Zdrzalik"));
        primaryButton.setText(resourceBundle.getString("start"));
        language.setText(resourceBundle.getString("language"));
    }
}

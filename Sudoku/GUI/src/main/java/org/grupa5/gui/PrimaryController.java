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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrimaryController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(PrimaryController.class);

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
        if (logger.isDebugEnabled()) {
            logger.debug("PrimaryController init");
        }
        ResourceBundle resourceAuthors
                = ResourceBundle.getBundle("org.grupa5.gui.resourceBundle.authors", Locale.getDefault());
        authors.setText(resourceAuthors.getString("Authors: "));
        author_1.setText(resourceAuthors.getString("Julia Szymanska"));
        author_2.setText(resourceAuthors.getString("Przemyslaw Zdrzalik"));
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void changeLanguage() {
        if (Locale.getDefault().equals(new Locale("en", "en"))) {
            Locale.setDefault(new Locale("pl", "pl"));
        } else {
            Locale.setDefault(new Locale("en", "en"));
        }
        resourceBundle = ResourceBundle.getBundle("Lang", Locale.getDefault());
        try {
            updateLanguage();
        } catch (IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error("changeLanguage threw ", e);
            }
        }
    }

    private void updateLanguage() throws IOException {
        App reload = new App();
        System.out.println(Locale.getDefault());
        reload.reload("primary");
    }
}

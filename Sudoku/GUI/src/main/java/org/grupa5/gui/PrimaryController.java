package org.grupa5.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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

    @FXML
    private Label mainLabel;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().
                getResource("Button_Wide_Wood_Border.png").toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);


        BackgroundImage backgroundImage2 = new BackgroundImage(new Image(getClass().
                getResource("Button_Small_Wood_Border_Smaller.png").toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Background background = new Background(backgroundImage);
        Background background2 = new Background(backgroundImage2);

        this.primaryButton.setBackground(background);
        this.language.setBackground(background2);

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

    public void languageButtonPressed(){
        this.language.setBackground(getBackgroundForImage("Button_Small_Wood_Border_Smaller_Wcisniety.png"));
    }

    public void startButtonPressed(){
        this.primaryButton.setBackground(getBackgroundForImage("Button_Wide_Wood_Border_wcisniety.png"));
    }

    public void startButtonReleased(){
        this.primaryButton.setBackground(getBackgroundForImage("Button_Wide_Wood_Border.png"));
    }

    private Background getBackgroundForImage(String image) {
        BackgroundImage backgroundImage2 = new BackgroundImage(new Image(getClass().
                getResource(image).toExternalForm()), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        return new Background(backgroundImage2);
    }

    private void updateLanguage() throws IOException {
        App reload = new App();
        reload.reload("primary");
    }
}

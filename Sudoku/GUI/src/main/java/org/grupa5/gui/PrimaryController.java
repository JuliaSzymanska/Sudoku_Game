package org.grupa5.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import static java.util.ResourceBundle.getBundle;

import java.util.ListResourceBundle;

// TODO: zrobic te lsity bo wywalaja blad przy runie

class authors_pl_PL extends ListResourceBundle {

    private static final Object[][] contents = {
            {"Authors: ", "Autorzy: "},
            {"Julia Szymanska", "Julia Szymańska"},
            {"Przemyslaw Zdrzalik", "Przemysław Zdrzalik"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

class authors extends ListResourceBundle {

    private static final Object[][] contents = {
            {"Authors: ", "Authors: "},
            {"Julia Szymanska", "Julia Szymanska"},
            {"Przemyslaw Zdrzalik", "Przemyslaw Zdrzalik"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}

public class PrimaryController implements Initializable {

    @FXML
    private Label authors;

    @FXML
    private Label author_1;

    @FXML
    private Label author_2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        authors.setText(this.resourceAuthors.getString("authors"));
//        ResourceBundle resourceAuthors
//                = ResourceBundle.getBundle("authors", new Locale("pl_PL"));
//        System.out.println(resourceAuthors.getString("Authors: "));
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}

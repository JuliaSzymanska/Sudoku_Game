package org.grupa5.gui;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {
    // komentarz PL
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
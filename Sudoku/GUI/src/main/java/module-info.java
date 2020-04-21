module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.grupa5.gui to javafx.fxml;
    exports org.grupa5.gui;
}
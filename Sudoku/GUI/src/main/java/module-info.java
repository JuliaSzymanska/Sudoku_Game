module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;

    opens org.grupa5.gui to javafx.fxml;
    exports org.grupa5.gui;
}
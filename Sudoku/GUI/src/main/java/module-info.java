module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires Dao;
    requires slf4j.api;
    requires Exceptions;
    requires javafx.media;

    opens org.grupa5.gui to javafx.fxml;
    exports org.grupa5.gui;
}
module org.example.smartplantcare {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;

    opens org.example.smartplantcare to javafx.fxml;
    exports org.example.smartplantcare;
    exports org.example.smartplantcare.database;
    opens org.example.smartplantcare.database to javafx.fxml;
}
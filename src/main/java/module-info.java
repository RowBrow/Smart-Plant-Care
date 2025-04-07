module org.example.smartplantcare {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires eu.hansolo.tilesfx;

    opens org.example.smartplantcare to javafx.fxml;
    exports org.example.smartplantcare;
}
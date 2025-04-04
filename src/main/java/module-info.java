module org.example.smartplantcare {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.smartplantcare to javafx.fxml;
    exports org.example.smartplantcare;
}
package frontend;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
public class SPCApp extends Application {
    @Override
    public void start(Stage stage) {
        Button button1=new Button("Click me!");
        VBox box=new VBox(button1);
        Scene scene = new Scene(box);
        stage.setTitle("My JavaFX program");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
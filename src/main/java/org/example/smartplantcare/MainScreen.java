package org.example.smartplantcare;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MainScreen extends Application {
    public MainScreen() throws FileNotFoundException {  }


    public static Canvas canvas = new Canvas(600,300);

    @Override
    public void start(Stage stage) {
        //LeftBar
        VBox left = LeftBar.createLeft();
        left.setPadding(new Insets(5)); // padding

        //Status Panel
        StatusPanel statusPanel = new StatusPanel(canvas);
        statusPanel.drawStatus();

        //We merge the status panel and the slider panel

        //With Dashboard:
        //VBox right = new VBox(canvas,ChartPanel.createChartPanel());

        //With SliderPanel
        VBox right = new VBox(canvas,SliderPanel.sliderPanel());
        right.setPrefSize(600,500);

        //We merge all components
        HBox root = new HBox(left,right);
        root.setMargin(left, new Insets(20));  // margin

        Scene scene = new Scene(root,800,500);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
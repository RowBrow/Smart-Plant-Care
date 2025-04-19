package org.example.smartplantcare;

import javafx.application.Application;
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
    private static BorderPane borderPane = new BorderPane();
    private static VBox sliderPanel;
    private static Pane chartPanel;
    private static BorderPane right = new BorderPane();
    private static VBox left;
    @Override
    public void start(Stage stage) throws InterruptedException {

        //LeftBar
        left = LeftBar.createLeft();

        //Status Panel
        StatusPanel statusPanel = new StatusPanel(canvas);
        statusPanel.drawStatus();

        //We merge the status panel and the slider panel

        //Dashboard:
        chartPanel = ChartPanel.createChartPanel();

        //Slider
        sliderPanel = SliderPanel.sliderPanel();

        right.setTop(canvas);
        //We start with Dashboard
        right.setCenter(sliderPanel);
        right.setPrefSize(600,600);


        //We merge leftBar and Dashboard
        borderPane.setLeft(left);
        borderPane.setCenter(right);

        Scene scene = new Scene(borderPane,800,600);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(Pane pane) {
        right.setCenter(pane);
        right.setPrefSize(600,500);
    }

    //Return left panel
    public static Pane getSliderPanel() {
        return sliderPanel;
    }

    //Return right panel
    public static Pane getChartPanel() {
        return chartPanel;
    }

    public static void main(String[] args) {
        launch();
    }
}
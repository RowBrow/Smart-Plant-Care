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
    Model db = new Model();

    public static Canvas canvas = new Canvas(600,300);
    private static BorderPane borderPane = new BorderPane();
    private static VBox sliderPanel;
    private static Pane chartPanel;
    private static BorderPane right = new BorderPane();
    private static VBox left;
    private StatusPanel statusPanel;
    @Override
    public void start(Stage stage) throws InterruptedException {

        //LeftBar
        left = LeftBar.createLeft();

        //Status Panel
        statusPanel = new StatusPanel(canvas);
        statusPanel.drawStatus(0,0,0,0);

        //We merge the status panel and the slider panel

        //Dashboard:
        chartPanel = ChartPanel.createChartPanel();

        //Slider
        sliderPanel = SliderPanel.sliderPanel();

        //Dummy button to change values randomly
        Button button1 = new Button("Change");
        button1.setOnAction(e -> {redrawValues();});
        right.setTop(button1);
        right.setCenter(canvas);
        //We start with Dashboard
        right.setBottom(sliderPanel);
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
        right.setBottom(pane);
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

    public void redrawValues(){
        Random rand = new Random();
        int w = rand.nextInt(100);
        int x = rand.nextInt(100);
        int y = rand.nextInt(100);
        int z = rand.nextInt(100);
        statusPanel.drawStatus(w,x,y,z);
    }

    public static void main(String[] args) {
        launch();
    }


}
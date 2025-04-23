package org.example.smartplantcare;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.smartplantcare.database.Measurement;
import org.example.smartplantcare.database.Model;

import java.io.FileNotFoundException;
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

        //updating values every 30 seconds
        Timeline simulateSensor = new Timeline(
                new KeyFrame(Duration.seconds(30), e -> {
                    updateMeasurement();
                })
        );
        simulateSensor.setCycleCount(Timeline.INDEFINITE);
        simulateSensor.play();

        //LeftBar
        left = LeftBar.createLeft();

        //Status Panel
        statusPanel = new StatusPanel(canvas);
        updateMeasurement();

        //We merge the status panel and the slider panel

        //Dashboard:
        chartPanel = ChartPanel.createChartPanel();

        //Slider
        sliderPanel = SliderPanel.sliderPanel();

        //Dummy button to change values randomly
        Button button1 = new Button("Change");
        button1.setOnAction(e -> {updateMeasurement();});
        right.setTop(button1);
        right.setCenter(canvas);
        //We start with Dashboard
        right.setBottom(sliderPanel);
        right.setPrefSize(800,800);


        //We merge leftBar and Dashboard
        borderPane.setLeft(left);
        borderPane.setCenter(right);

        Scene scene = new Scene(borderPane,1000,800);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(Pane pane) {
        right.setBottom(pane);
        right.setPrefSize(800,700);
    }

    //Return left panel
    public static Pane getSliderPanel() {
        return sliderPanel;
    }

    //Return right panel
    public static Pane getChartPanel() {
        return chartPanel;
    }

    public void updateMeasurement() {
        Measurement measurement = db.getLatestData();
        statusPanel.drawStatus(
                measurement.getLight(),
                measurement.getTemp(),
                measurement.getWater(),
                measurement.getHumidity()
        );
    }

    public static void main(String[] args) {
        final String MQTT_DATA_GATHERING_TOPIC = "HiGrowSensor/send_data";
        final String MQTT_BROKER = "tcp://public.cloud.shiftr.io:1883";
        final String MQTT_CLIENT_ID = "Smart_Plant_Care_App";
        final String MQTT_USERNAME = "public";
        final String MQTT_PASSWORD = "public";
        MemoryPersistence memoryPersistence = new MemoryPersistence();
        final int qos = 2;

        try {
            MqttClient mqttClient = new MqttClient(MQTT_BROKER, MQTT_CLIENT_ID, memoryPersistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setAutomaticReconnect(true);
            connOpts.setConnectionTimeout(10);
            connOpts.setUserName(MQTT_USERNAME);
            connOpts.setPassword(MQTT_PASSWORD.toCharArray());
            System.out.println("Connecting to broker: "+ MQTT_BROKER);
            mqttClient.connect(connOpts);
            System.out.println("Connected");

            mqttClient.subscribe(MQTT_DATA_GATHERING_TOPIC);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Connection lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    System.out.println(topic + " : " + new String(mqttMessage.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });

        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        launch();
    }
}
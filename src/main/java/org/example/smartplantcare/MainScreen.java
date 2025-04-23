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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.*;

public class MainScreen extends Application {
    public MainScreen() {}
    static Model db = new Model();

    public static Canvas canvas = new Canvas(700,300);
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
                new KeyFrame(Duration.seconds(1), e -> {
                    updateMeasurement();
                })
        );
        simulateSensor.setCycleCount(Timeline.INDEFINITE);
        simulateSensor.play();

        // LeftBar
        left = LeftBar.createLeft();

        // Status Panel
        statusPanel = new StatusPanel(canvas);
        updateMeasurement();

        // We merge the status panel and the slider panel

        // Dashboard:
        chartPanel = new ChartPanel();

        //Slider
        sliderPanel = SliderPanel.sliderPanel();

        //Dummy button to change values randomly
        Button button1 = new Button("Change");
        button1.setOnAction(e -> {updateMeasurement();});
        right.setTop(button1);
        right.setCenter(canvas);
        //We start with Dashboard
        right.setBottom(chartPanel);
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
        if (measurement != null) {
            statusPanel.drawStatus(
                    measurement.getLight(),
                    measurement.getTemp(),
                    measurement.getWater(),
                    measurement.getHumidity()
            );
        }
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
            // Initialize the MQTT client
            MqttClient mqttClient = new MqttClient(MQTT_BROKER, MQTT_CLIENT_ID, memoryPersistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();

            // Set connection/reconnection options
            connOpts.setCleanSession(true);
            connOpts.setAutomaticReconnect(true);
            connOpts.setConnectionTimeout(10);

            // Authentication
            connOpts.setUserName(MQTT_USERNAME);
            connOpts.setPassword(MQTT_PASSWORD.toCharArray());

            // Connect to broker
            System.out.println("Connecting to broker: "+ MQTT_BROKER);
            mqttClient.connect(connOpts);
            System.out.println("Connected");

            // Subscription
            mqttClient.subscribe(MQTT_DATA_GATHERING_TOPIC);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Connection lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    System.out.println(topic + " : " + new String(mqttMessage.getPayload()));
                    JSONObject json = new JSONObject(new String(mqttMessage.getPayload()));
                    int water = json.getInt("water");
                    float humidity = json.getFloat("humidity");
                    float temperature = json.getFloat("temperature");
                    int light = json.getInt("light");
                    String deviceId = json.getString("deviceId");

                    LocalDateTime now = LocalDateTime.now();
                    String standardizedNow = now.format(DateTimeFormatter.ISO_DATE_TIME);

                    Measurement measurement = new Measurement(standardizedNow, light, temperature, water, humidity);

                    db.insertMeasurement(measurement);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        // Launch the JavaFX application
        launch(args);

        // Close the program when the UI is closed
        // using the close button
        System.exit(0);
    }
}
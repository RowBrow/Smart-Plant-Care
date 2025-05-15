package org.example.smartplantcare;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.smartplantcare.model.Measurement;
import org.example.smartplantcare.model.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.*;

public class MainApp extends Application {
    public MainApp() {}
    static Model model = new Model();

    private static final HBox mainPane = new HBox();
    private static VBox sliderPanel;
    private static Pane chartPanel;
    private static final VBox right = new VBox();
    private StatusPanel statusPanel;

    @Override
    public void start(Stage stage) {
        //updating values every 30 seconds
        Timeline simulateSensor = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    updateMeasurement();
                })
        );
        simulateSensor.setCycleCount(Timeline.INDEFINITE);
        simulateSensor.play();

        // LeftBar
        VBox left = new LeftBar();

        // Status Panel
        statusPanel = new StatusPanel();
        updateMeasurement();

        // We merge the status panel and the slider panel

        // Dashboard:
        chartPanel = new ChartPanel();

        //Slider
        sliderPanel = new SliderPanel();

        // We start with Dashboard
        right.getChildren().add(statusPanel);
        right.getChildren().add(chartPanel);
        right.setAlignment(Pos.CENTER);

        VBox.setVgrow(right, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);

        right.setPadding(new Insets(20));

        //We merge leftBar and Dashboard
        mainPane.getChildren().addAll(left, right);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(Pane pane) {
        right.getChildren().set(1, pane);
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
        Measurement measurement = model.getLatestMeasurement();
        measurement = new Measurement("0", "0", 10, 600, 500, 600);
        if (measurement != null) {
            statusPanel.drawStatus(
                    measurement.light(),
                    measurement.temp(),
                    measurement.water(),
                    measurement.humidity()
            );
        }
    }

    public static void main(String[] args) {
        // Initialize the MQTT client
        initializeMQTTClient();

        // Launch the JavaFX application
        launch(args);

        // Close the program when the UI is closed
        // using the close button
        System.exit(0);
    }

    private static class MQTTDataGatheringCallback implements MqttCallback {
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

            Measurement measurement = new Measurement(standardizedNow, deviceId, light, temperature, water, humidity);

            model.insertMeasurement(measurement);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

        }
    }

    private static void initializeMQTTClient() {
        final String MQTT_DATA_GATHERING_TOPIC = "HiGrowSensor/send_data";
        final String MQTT_ACTION_SENDING_TOPIC = "HiGrowSensor/send_action";
        final String MQTT_BROKER = "tcp://public.cloud.shiftr.io:1883";
        final String MQTT_CLIENT_ID = "Smart_Plant_Care_App";
        final String MQTT_USERNAME = "public";
        final String MQTT_PASSWORD = "public";
        MemoryPersistence memoryPersistence = new MemoryPersistence();

        try {
            // Initialize the MQTT client
            MqttClient mqttClient = new MqttClient(MQTT_BROKER, MQTT_CLIENT_ID, memoryPersistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();

            // Set connection/reconnection options
            connOpts.setCleanSession(true);
            connOpts.setAutomaticReconnect(true);
            connOpts.setConnectionTimeout(10);

            // Authenticate MQTT connection
            connOpts.setUserName(MQTT_USERNAME);
            connOpts.setPassword(MQTT_PASSWORD.toCharArray());

            // Connect to broker
            System.out.println("Connecting to broker: "+ MQTT_BROKER);
            mqttClient.connect(connOpts);
            System.out.println("Connected");

            // Subscribe to data gathering topic
            mqttClient.subscribe(MQTT_DATA_GATHERING_TOPIC);

            // Set callback class for processing gathered data
            mqttClient.setCallback(new MQTTDataGatheringCallback());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
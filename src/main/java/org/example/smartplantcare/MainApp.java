package org.example.smartplantcare;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.smartplantcare.controller.DashboardController;
import org.example.smartplantcare.database.DBConnection;
import org.example.smartplantcare.model.Measurement;
import org.example.smartplantcare.model.DashboardModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.example.smartplantcare.view.AdvancedModeView;
import org.example.smartplantcare.view.DashboardView;
import org.json.*;

public class MainApp extends Application {
    /// The MQTT client responsible for
    /// - receiving sensor measurements from devices
    /// - sending commands to said devices
    static MqttClient mqttClient;

    // Dashboard classes
    static DashboardModel dashboardModel = new DashboardModel();
    static DashboardView dashboardView = new DashboardView();
    private static final DashboardController controller = new DashboardController(dashboardView, dashboardModel);

    // AdvancedMode classes
    static AdvancedModeView advancedModeView = new AdvancedModeView();

    private static final HBox mainPane = new HBox();
    private static final VBox right = new VBox();

    @Override
    public void start(Stage stage) {
        // LeftBar
        VBox sideBar = new SideBar();

        // Initialize the application
        // with the dashboardView as the
        // active view
        right.getChildren().add(dashboardView);
        right.setAlignment(Pos.CENTER);

        // Make the right view grow respective
        // to the application window
        VBox.setVgrow(right, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);

        // Give padding to the right view
        right.setPadding(new Insets(20));

        //We merge leftBar and Dashboard
        mainPane.getChildren().addAll(sideBar, right);

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene(Pane pane) {
        right.getChildren().set(0, pane);
    }

    /// Return the Dashboard view
    public static Pane getDashboardScene() {
        return dashboardView;
    }

    /// Return the AdvancedMode view
    public static Pane getAdvancedModeScene() {
        return advancedModeView;
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
        DBConnection dbConnection = DBConnection.getInstance();
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

            Measurement measurement = new Measurement(deviceId, standardizedNow, light, temperature, water, humidity);
            dbConnection.insertMeasurement(measurement);
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            System.out.println("[INFO]: Message sent to: " + iMqttDeliveryToken.getTopics());
            try {
                System.out.println("[INFO]: Message content:\n" + iMqttDeliveryToken.getMessage().toString());
            } catch (MqttException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /// Initializes the MQTT client
    /// with the gives
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
            mqttClient = new MqttClient(MQTT_BROKER, MQTT_CLIENT_ID, memoryPersistence);
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
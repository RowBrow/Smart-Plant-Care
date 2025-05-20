package org.example.smartplantcare;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.smartplantcare.controller.AdvancedModeController;
import org.example.smartplantcare.controller.DashboardController;
import org.example.smartplantcare.database.SPCMqttCallback;
import org.example.smartplantcare.model.AdvancedModeModel;
import org.example.smartplantcare.model.DashboardModel;

import org.example.smartplantcare.view.AdvancedModeView;
import org.example.smartplantcare.view.DashboardView;

public class MainApp extends Application {
    /// The MQTT client responsible for
    /// - receiving sensor measurements from devices
    /// - sending commands to said devices
    private static MqttClient mqttClient;

    public static final String MQTT_BROKER = "tcp://public.cloud.shiftr.io:1883";
    public static final String MQTT_CLIENT_ID = "Smart_Plant_Care_App";
    public static final String MQTT_USERNAME = "public";
    public static final String MQTT_PASSWORD = "public";

    // Dashboard classes
    static DashboardView dashboardView;
    static DashboardModel dashboardModel;
    private static DashboardController dashboardController;

    // AdvancedMode classes
    static AdvancedModeView advancedModeView;
    static AdvancedModeModel advancedModeModel;
    private static AdvancedModeController advancedModeController;



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
        initializeMVC();
        // Launch the JavaFX application
        launch(args);

        // Close the program when the UI is closed
        // using the close button
        System.exit(0);
    }



    /// Initializes the MQTT client
    private static void initializeMQTTClient() {
        final String MQTT_DATA_GATHERING_TOPIC = "HiGrowSensor/send_data";
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
            mqttClient.setCallback(new SPCMqttCallback());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initializeMVC(){
        dashboardView = new DashboardView();
        dashboardModel = new DashboardModel();
        dashboardController = new DashboardController(dashboardView, dashboardModel);

        // AdvancedMode classes
        advancedModeView = new AdvancedModeView();
        advancedModeModel = new AdvancedModeModel();
        advancedModeController = new AdvancedModeController(advancedModeView, advancedModeModel, mqttClient);


    }
}
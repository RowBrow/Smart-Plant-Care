package org.example.smartplantcare.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.example.smartplantcare.MainApp;
import org.example.smartplantcare.model.AdvancedModeModel;
import org.example.smartplantcare.view.AdvancedModeView;
import org.json.JSONObject;


public class AdvancedModeController {
    private MemoryPersistence memoryPersistence = new MemoryPersistence();
    private static MqttClient client;

    final String MQTT_ACTION_SENDING_TOPIC = "HiGrowSensor/send_action";

    private final AdvancedModeView view;
    private final AdvancedModeModel model;

    private int lightThreshold;
    private int waterThreshold;

    private String currentDeviceId = "233417020993736";

    private int UPDATE_INTERVAL = 3;

    public AdvancedModeController(AdvancedModeView view, AdvancedModeModel model, MqttClient client) {
        try {
            client = new MqttClient(MainApp.MQTT_BROKER, MainApp.MQTT_CLIENT_ID, memoryPersistence);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        MqttConnectOptions connOpts = new MqttConnectOptions();

        // Set connection/reconnection options
        connOpts.setCleanSession(true);
        connOpts.setAutomaticReconnect(true);
        connOpts.setConnectionTimeout(10);

        // Authenticate MQTT connection
        connOpts.setUserName(MainApp.MQTT_USERNAME);
        connOpts.setPassword(MainApp.MQTT_PASSWORD.toCharArray());
        try {
            client.connect(connOpts);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        this.view = view;
        this.model = model;

        // Set a timer that updates the statusPanel periodically
        Timeline simulateSensor = new Timeline(
                new KeyFrame(Duration.seconds(2), _ -> {
                    updateMeasurement();
                })
        );
        simulateSensor.setCycleCount(Timeline.INDEFINITE);
        simulateSensor.play();

        /// This timeline checks if the light and water values are below the threshold,
        /// if so it commands the Higrow to turn light on
        MqttClient finalClient = client;
        Timeline sendAction = new Timeline(
                new KeyFrame(Duration.seconds(UPDATE_INTERVAL), _ -> {
                    model.getLatestMeasurement();
                    JSONObject action = new JSONObject();
                    Boolean lightOn = model.currentLight < lightThreshold;
                    Boolean waterOn = model.currentWater < waterThreshold;
                    action.put("lightOn", lightOn);
                    action.put("waterOn", waterOn);
                    action.put("deviceId", currentDeviceId);
                    try {
                        finalClient.publish(MQTT_ACTION_SENDING_TOPIC, new MqttMessage(action.toString().getBytes()));
                        System.out.println(MQTT_ACTION_SENDING_TOPIC + ": " + action);
                    } catch (MqttException e) {
                        throw new RuntimeException(e);
                    }
                })
        );

        sendAction.setCycleCount(Timeline.INDEFINITE);
        sendAction.play();

        view.sliderPanel.sliderLight.valueProperty().addListener(
                new ChangeListener<>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {

                        view.sliderPanel.valueLight.setText(String.valueOf(newValue.intValue()));
                        lightThreshold = newValue.intValue();
                    }
                });

        view.sliderPanel.sliderWater.valueProperty().addListener(
                new ChangeListener<>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {

                        view.sliderPanel.valueWater.setText(String.valueOf(newValue.intValue()));
                        waterThreshold = newValue.intValue();
                    }
                });

        view.sliderPanel.sliderTemp.valueProperty().addListener(
                new ChangeListener<>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {

                        view.sliderPanel.valueTemp.setText(String.valueOf(newValue.intValue()));

                    }
                });
    }

    /// Updates the measurements stored
    /// in the model and reflect those
    /// changes in the view
    public void updateMeasurement() {
        // Update measurements stored in
        // the model.
        model.getLatestMeasurement();

        // Update the view according
        // to the
        view.statusPanel.drawStatus(
                model.currentLight,
                model.currentTemp,
                model.currentWater,
                model.currentHumidity
        );
    }
}

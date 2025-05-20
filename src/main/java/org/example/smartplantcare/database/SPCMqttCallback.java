package org.example.smartplantcare.database;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.example.smartplantcare.model.Measurement;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SPCMqttCallback implements MqttCallback {
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

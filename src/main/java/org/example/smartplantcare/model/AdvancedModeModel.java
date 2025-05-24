package org.example.smartplantcare.model;

import org.example.smartplantcare.database.DBConnection;

public class AdvancedModeModel {

    //Connection with DB
    DBConnection db = DBConnection.getInstance();

    // last reading
    public Integer currentLight = 0;
    public Float currentTemp = 0.0f;
    public Integer currentWater = 0;
    public Float currentHumidity = 0.0f;

    public String currentDeviceId = "233417020993736";

    public void getLatestMeasurement() {

        Measurement measurement = db.getOneMeasurement("SELECT * FROM measurement where device_id = '" + currentDeviceId + "' ORDER BY timestamp DESC LIMIT 1");
        if (measurement != null) {
            currentLight = measurement.light();
            currentTemp = measurement.temp();
            currentWater = measurement.water();
            currentHumidity = measurement.humidity();
        } else {
            System.out.println("[WARN]: No measurement found");
        }
    }
}

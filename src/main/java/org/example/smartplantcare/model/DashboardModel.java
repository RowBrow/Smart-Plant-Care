package org.example.smartplantcare.model;
import org.example.smartplantcare.database.DBConnection;

import java.sql.SQLException;
import java.util.List;

/// Stores the `state` of dashboard
public class DashboardModel {
  DBConnection db = new DBConnection();
  public String currentDeviceId = "";
  public String currentDeviceName = "";

  public Integer currentLight = 0;
  public Float currentTemp = 0.0f;
  public Integer currentWater = 0;
  public Float currentHumidity = 0.0f;

  public List<Measurement> measurmentList;


  // getting latest value
  public Measurement getLatestMeasurement() {
    return db.queryOneMeasurement(
            """
            SELECT * FROM measurement
            ORDER BY timestamp DESC LIMIT 1
            """
    );
  }

  public void insertMeasurement(Measurement measurement) throws SQLException {
    db.insertMeasurement(measurement);
  }

  public void getAllMeasurementsForDevice(String deviceId) {
    db.getMeasurements("SELECT * FROM measurement" +
            " WHERE deviceId = '" + deviceId + "'" +
            " ORDER BY timestamp DESC");
  }
}

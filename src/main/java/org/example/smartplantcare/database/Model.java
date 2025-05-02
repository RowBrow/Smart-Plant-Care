package org.example.smartplantcare.database;
import java.sql.SQLException;

public class Model{
  MyDB db = new MyDB();

  // getting latest value
  public Measurement getLatestData() {
    return db.queryOneMeasurement(
            "SELECT * FROM measurement\n" +
            "ORDER BY datetime DESC LIMIT 1;");
  }

  public void insertMeasurement(Measurement measurement) throws SQLException {
    String deviceId = measurement.getDeviceId();
    String datetime = measurement.getDatetime();
    int light = measurement.getLight();
    float temp = measurement.getTemp();
    int water = measurement.getWater();
    float humidity = measurement.getHumidity();
    String insertSQL = "INSERT OR IGNORE INTO measurement (device_id, timestamp, light, temp, water, humidity) VALUES (?, ?, ?, ?, ?, ?)";
    db.insertMeasurement(insertSQL, deviceId, datetime, light, temp, water, humidity);
  }
}

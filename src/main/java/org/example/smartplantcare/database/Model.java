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
    String datetime = measurement.getDatetime();
    float light = measurement.getLight();
    float temp = measurement.getTemp();
    float water = measurement.getWater();
    float humidity = measurement.getHumidity();
    String insertSQL = "INSERT OR IGNORE INTO measurement (datetime, light, temp, water, humidity) VALUES (?, ?, ?, ?, ?)";
    db.insertMeasurement(insertSQL, datetime, light, temp, water, humidity);
  }


}

package org.example.smartplantcare.model;
import org.example.smartplantcare.database.MyDB;

import java.sql.SQLException;

public class Model {
  MyDB db = new MyDB();

  // getting latest value
  public Measurement getLatestData() {
    return db.queryOneMeasurement(
            "SELECT * FROM measurement\n" +
            "ORDER BY datetime DESC LIMIT 1;");
  }

  public void insertMeasurement(Measurement measurement) throws SQLException {
    db.insertMeasurement(measurement);
  }
}

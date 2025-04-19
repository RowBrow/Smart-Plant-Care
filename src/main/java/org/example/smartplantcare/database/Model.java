package org.example.smartplantcare.database;
import java.util.*;

public class Model{
  MyDB db = new MyDB();

  // getting latest value
  public Measurement getLatestData() {
    return db.queryOneMeasurement(
            "SELECT * FROM measurement\n" +
            "ORDER BY datetime DESC LIMIT 1;");
  }
}

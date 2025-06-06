package org.example.smartplantcare.model;
import org.example.smartplantcare.database.DBConnection;
import org.example.smartplantcare.UIComponents.ChartPanel.ChartType;

import java.util.ArrayList;
import java.util.List;

/// Stores the data of dashboard
/// required by the controller
public class DashboardModel {
  DBConnection db = DBConnection.getInstance();
  public List<String> deviceIdList = new ArrayList<>();
  /// Holds the deviceId of the device
  /// currently inspected.
  ///
  /// Currently hard-coded to the ID
  /// of the singular device we're using
  /// as a proof of concept.
  public String currentDeviceId = "233417020993736";
  public String currentDeviceName = "HiGrow Monitor";

  // statusPanel measurements
  public Integer currentLight = 0;
  public Float currentTemp = 0.0f;
  public Integer currentWater = 0;
  public Float currentHumidity = 0.0f;

  // Data for the chartPanel and which
  // type of chart should be shown
  public ChartType chartType = ChartType.LIGHT;
  public List<Measurement> measurementList;

  /// Gets the latest measurement made
  /// by the device with ID `currentDeviceId`
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

  /// Updates `measurementList` with measurements
  /// made by device with ID `currentDeviceId`
  public void updateMeasurementList() {
    measurementList = db.getMeasurements("SELECT * FROM measurement WHERE device_id = '" + currentDeviceId + "' ORDER BY timestamp");
  }

  /// Updates the chart type desired by the user
  public void updateChartType(ChartType chartType) {
    this.chartType = chartType;
  }
}

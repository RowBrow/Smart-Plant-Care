package org.example.smartplantcare.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.example.smartplantcare.model.DashboardModel;
import org.example.smartplantcare.view.ChartPanel;
import org.example.smartplantcare.view.DashboardView;

public class DashboardController {
    private final DashboardView view;
    private final DashboardModel model;

    public DashboardController(DashboardView view, DashboardModel model) {
        this.view = view;
        this.model = model;

        // Set a timer that updates the statusPanel periodically
        Timeline simulateSensor = new Timeline(
                new KeyFrame(Duration.seconds(1), _ -> updateMeasurement())
        );
        simulateSensor.setCycleCount(Timeline.INDEFINITE);
        simulateSensor.play();

        // Set listeners for the buttons
        // so the chart changes based on
        // button presses
        view.lightChartButton.setOnAction(_ -> {
            model.updateChartType(ChartPanel.ChartType.LIGHT);
            model.updateMeasurementList();
            view.chartPanel.drawChart(model.chartType, model.measurementList);
        });

        view.tempChartButton.setOnAction(_ -> {
            model.updateChartType(ChartPanel.ChartType.TEMP);
            model.updateMeasurementList();
            view.chartPanel.drawChart(model.chartType, model.measurementList);
        });
        view.humidChartButton.setOnAction(_ -> {
            model.updateChartType(ChartPanel.ChartType.HUMIDITY);
            model.updateMeasurementList();
            view.chartPanel.drawChart(model.chartType, model.measurementList);
        });
        view.waterChartButton.setOnAction(_ -> {
            model.updateChartType(ChartPanel.ChartType.WATER);
            model.updateMeasurementList();
            view.chartPanel.drawChart(model.chartType, model.measurementList);
        });
    }

    /// Updates the measurements stored
    /// in the model and reflect those
    /// changes in the view
    public void updateMeasurement() {
        model.getLatestMeasurement();
        view.statusPanel.drawStatus(
                model.currentLight,
                model.currentTemp,
                model.currentWater,
                model.currentHumidity
        );
    }
}

package org.example.smartplantcare.controller;

import org.example.smartplantcare.model.DashboardModel;
import org.example.smartplantcare.model.Measurement;
import org.example.smartplantcare.view.DashboardView;

import java.util.List;

public class DashboardController {
    private DashboardView view;
    private DashboardModel model;

    public DashboardController(DashboardView view, DashboardModel model) {
        this.view = view;
        this.model = model;

        //view.lightChartButton.setOnAction(e -> {})

        updateStatusPanel();
    }

    private void updateStatusPanel() {
        Measurement latest = model.getLatestMeasurement();
        if (latest != null) {
            view.statusPanel.drawStatus(
                    latest.light(),
                    latest.temp(),
                    latest.water(),
                    latest.humidity()
            );
        }
    }

}

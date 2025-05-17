package org.example.smartplantcare.view;

import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class DashboardView extends VBox {
    public final StatusPanel statusPanel = new StatusPanel();
    public final ChartPanel chartPanel = new ChartPanel();

    // Expose the buttons in `chartPanel` for ease of access
    public Button lightChartButton = chartPanel.lightChartButton;
    public Button tempChartButton = chartPanel.tempChartButton;
    public Button waterChartButton = chartPanel.waterChartButton;
    public Button humidChartButton = chartPanel.humidChartButton;

    public DashboardView() {
        setAlignment(Pos.CENTER);
        getChildren().addAll(statusPanel, chartPanel);
    }
}

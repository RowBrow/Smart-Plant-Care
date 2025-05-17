package org.example.smartplantcare.view;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.TextAlignment;
import javafx.scene.chart.XYChart.Series;
import org.example.smartplantcare.HelperMethods;
import org.example.smartplantcare.model.Measurement;

import java.util.List;

import static org.example.smartplantcare.HelperMethods.vspace;

public class ChartPanel extends VBox {
    public Button lightChartButton = HelperMethods.button("Light");
    public Button tempChartButton = HelperMethods.button("Temp");
    public Button waterChartButton = HelperMethods.button("Water");
    public Button humidChartButton = HelperMethods.button("Humid");
    private final Series<String, Number> series = new Series<>();

    public enum ChartType {
        LIGHT,
        TEMP,
        WATER,
        HUMIDITY
    }

    public Tile chart;

    private static final double TILE_WIDTH = 700;
    private static final double TILE_HEIGHT = 280;

    /// Populates the chart based on the desired type
    /// of chart and by measurements given
    public void drawChart(ChartType chartType, List<Measurement> measurements) {
        series.getData().clear();

        // Put a maximum to the data points shown
        // and check that it is less the total data
        // points possible to show
        int NUM_OF_DATA_POINTS = 100;
        if (measurements.size() < NUM_OF_DATA_POINTS) {
            NUM_OF_DATA_POINTS = measurements.size();
        }

        // Make proper
        for (int i = 0; i < NUM_OF_DATA_POINTS; i++) {
            Measurement measurement = measurements.get(i);
            String[] parts = measurement.timestamp().split("T");
            String timeLabel = (parts.length >= 2) ? parts[1].substring(0, 8) : measurement.timestamp();

            switch (chartType) {
                case LIGHT -> series.getData().add(new XYChart.Data<>(timeLabel, measurement.light()));
                case TEMP -> series.getData().add(new XYChart.Data<>(timeLabel, measurement.temp()));
                case WATER -> series.getData().add(new XYChart.Data<>(timeLabel, measurement.water()));
                case HUMIDITY -> series.getData().add(new XYChart.Data<>(timeLabel, measurement.humidity()));
            }
        }

        chart.setTitle(switch (chartType) {
            case LIGHT -> "Light Intensity Per Day";
            case TEMP -> "Temperature Per Day";
            case WATER -> "Water Level Per Day";
            case HUMIDITY -> "Humidity Per Day";
        });
    }

    public ChartPanel() {
        CategoryAxis xAxis = new CategoryAxis();
        chart = TileBuilder.create()
                .skinType(Tile.SkinType.SMOOTHED_CHART)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Light Intensity Per Day")
                .titleAlignment(TextAlignment.CENTER)
                .chartType(Tile.ChartType.AREA)
                .smoothing(true)
                .tilesFxSeries(new TilesFXSeries<>(series,
                        Tile.BLUE,
                        new LinearGradient(0, 0, 0, 1,
                                true, CycleMethod.NO_CYCLE,
                                new Stop(0, Tile.LIGHT_GREEN),
                                new Stop(1, Color.TRANSPARENT))))
                .build();

        // Create the button section and align it properly
        HBox buttons = new HBox(lightChartButton, tempChartButton, waterChartButton, humidChartButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(buttons, vspace(29), chart);
    }
}

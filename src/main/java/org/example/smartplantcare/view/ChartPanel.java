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

import java.sql.*;
import java.util.List;

import static org.example.smartplantcare.HelperMethods.vspace;

public class ChartPanel extends VBox {
    private String deviceId;
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

//    lightChart.setOnAction(e -> {createChartPanel();});  // createChartPanel should be replaced into each chart

    private static final double TILE_WIDTH = 700;
    private static final double TILE_HEIGHT = 280;


    // TODO: Create the function
    //  that draws the graph depending
    //  on the type of chart wanted by
    //  the user (light/temperature/etc.)

    public void drawChart(ChartType chartType,List<Measurement> measurements) {
        series.getData().clear();
        for (Measurement m : measurements) {
            String[] parts = m.timestamp().split("T");
            String timeLabel = (parts.length >= 2) ? parts[1].substring(0, 8) : m.timestamp();

            switch (chartType) {
                case LIGHT -> series.getData().add(new XYChart.Data<>(timeLabel, m.light()));
                case TEMP -> series.getData().add(new XYChart.Data<>(timeLabel, m.temp()));
                case WATER -> series.getData().add(new XYChart.Data<>(timeLabel, m.water()));
                case HUMIDITY -> series.getData().add(new XYChart.Data<>(timeLabel, m.humidity()));
            }
        }
        //changing Title
        /*
        chart.setTitle(switch (chartType) {
            case LIGHT -> "Light Intensity Per Day";
            case TEMP -> "Temperature Per Day";
            case WATER -> "Water Level Per Day";
            case HUMIDITY -> "Humidity Per Day";
        });
        */
    }

    public void getLightDataFromDB() {
        series.getData().clear();
        String DB_URL = "jdbc:sqlite:identifier.sqlite";

        // Select the last 20 measurements and show their time and light readings.
        String query = "SELECT * FROM measurement ORDER BY timestamp LIMIT 20";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String timestamp = rs.getString("timestamp");
                int light = rs.getInt("light");

/*              // 시간 정보만 추출 (예: "05:12 PM")
                String timeLabel = timestamp.substring(9);  // "hh:mm:ss AM/PM" 추출
                series.getData().add(new XYChart.Data<>(timeLabel, light));
                // timestamp 형식: "20250419 05:00:00 PM"
*/
                //방법1: 시간 문자열 추출 : 공백 이후 문자열
                String[] parts = timestamp.split("T");
                String timeLabel = (parts.length >= 2) ? parts[1].substring(0,8) : timestamp;

                series.getData().add(new XYChart.Data<>(timeLabel, light));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChartPanel(String deviceId) {
        this.deviceId = deviceId;
    }

    public ChartPanel() {
        // getLightDataFromDB();

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

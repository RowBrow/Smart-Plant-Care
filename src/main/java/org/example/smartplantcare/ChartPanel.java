package org.example.smartplantcare;

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

import java.sql.*;

import static org.example.smartplantcare.HelperMethods.vspace;

public class ChartPanel extends VBox {
    public Button lightChart = HelperMethods.button("Light");
    public Button tempChart = HelperMethods.button("Temp");
    public Button waterChart = HelperMethods.button("Water");
    public Button humidChart = HelperMethods.button("Humid");
    private final Series<String, Number> series = new Series<>();

    public Tile chart;

//    lightChart.setOnAction(e -> {createChartPanel();});  // createChartPanel should be replaced into each chart

    private static final double TILE_WIDTH = 700;
    private static final double TILE_HEIGHT = 280;

    public void getLightDataFromDB() {
        series.getData().clear();
        String DB_URL = "jdbc:sqlite:identifier.sqlite";

        // Select the last 20 measurements and show their time and light readings.
        String query = "SELECT timestamp, light FROM measurement ORDER BY timestamp LIMIT 20";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String datetime = rs.getString("datetime");
                int light = rs.getInt("light");

/*              // 시간 정보만 추출 (예: "05:12 PM")
                String timeLabel = datetime.substring(9);  // "hh:mm:ss AM/PM" 추출
                series.getData().add(new XYChart.Data<>(timeLabel, light));
                // datetime 형식: "20250419 05:00:00 PM"
*/
                //방법1: 시간 문자열 추출 : 공백 이후 문자열
                String[] parts = datetime.split("T");
                String timeLabel = (parts.length >= 2) ? parts[1].substring(0,8) : datetime;

                series.getData().add(new XYChart.Data<>(timeLabel, light));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChartPanel() {
        getLightDataFromDB();

        CategoryAxis xAxis = new CategoryAxis();

        // 필요한 라벨만 리스트에 추가
/*      xAxis.setCategories(FXCollections.observableArrayList(
                "0:00 AM", "2:00 AM","4:00 AM", "6:00 AM", "8:00 AM",
                "10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM","8:00 PM", "10:00 PM","12:00 PM"
        ));
        xAxis.setTickLabelFont(Font.font("Arial", 14));
*/
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
        StackPane root = new StackPane(chart);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(new HBox(lightChart, tempChart, waterChart, humidChart), vspace(29), root);
    }
}

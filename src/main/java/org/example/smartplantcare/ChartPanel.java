package org.example.smartplantcare;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.TilesFXSeries;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Arrays;

public class ChartPanel {

    private static final double TILE_WIDTH = 400;
    private static final double TILE_HEIGHT = 400;

    public static XYChart.Series<String, Number> getLightDataFromDB() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String DB_URL = "jdbc:sqlite:identifier.sqlite";

        String query = "SELECT datetime, light FROM plantData WHERE datetime LIKE '20250419%' ORDER BY datetime ASC";

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
                String[] parts = datetime.split(" ");
                String timeLabel = (parts.length >= 3) ? parts[1].substring(0, 5) + " " + parts[2] : datetime;

                series.getData().add(new XYChart.Data<>(timeLabel, light));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return series;
    }

    public static StackPane createChartPanel() {
        XYChart.Series<String, Number> series = getLightDataFromDB();

        CategoryAxis xAxis = new CategoryAxis();

        // 필요한 라벨만 리스트에 추가
/*      xAxis.setCategories(FXCollections.observableArrayList(
                "0:00 AM", "2:00 AM","4:00 AM", "6:00 AM", "8:00 AM",
                "10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM","8:00 PM", "10:00 PM","12:00 PM"
        ));

        xAxis.setTickLabelFont(Font.font("Arial", 14));
*/
        Tile areaChartTile = TileBuilder.create()
                .skinType(Tile.SkinType.SMOOTHED_CHART)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Light Intensity per day")
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

        StackPane root = new StackPane(areaChartTile);
        return root;
    }
}

//package org.example.smartplantcare;
//
//import eu.hansolo.tilesfx.Tile;
//import eu.hansolo.tilesfx.TileBuilder;
//import eu.hansolo.tilesfx.chart.TilesFXSeries;
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.chart.XYChart;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.CycleMethod;
//import javafx.scene.paint.LinearGradient;
//import javafx.scene.paint.Stop;
//import javafx.stage.Stage;
//
//import java.util.Arrays;
//
//public class ChartPanel extends Application {
//    private static final double TILE_WIDTH = 150;
//    private static final double TILE_HEIGHT = 150;
//
//    public ChartPanel
//    @Override
//    public void start(Stage stage) {
//
//        // AreaChart Data
//        XYChart.Series<String, Number> series1 = new XYChart.Series();
//        series1.getData().add(new XYChart.Data("MO", 23));
//        series1.getData().add(new XYChart.Data("TU", 21));
//        series1.getData().add(new XYChart.Data("WE", 20));
//        series1.getData().add(new XYChart.Data("TH", 22));
//        series1.getData().add(new XYChart.Data("FR", 24));
//        series1.getData().add(new XYChart.Data("SA", 22));
//        series1.getData().add(new XYChart.Data("SU", 20));
//
//
//        Tile areaChartTile = TileBuilder.create()
//                .skinType(Tile.SkinType.SMOOTHED_CHART)
//                .prefSize(TILE_WIDTH, TILE_HEIGHT)
//                .title("SmoothedChart Tile")
//                .chartType(Tile.ChartType.AREA)
//                .smoothing(true)
//                .tilesFxSeries(new TilesFXSeries<>(series1,
//                        Tile.BLUE,
//                        new LinearGradient(0, 0, 0, 1,
//                                true, CycleMethod.NO_CYCLE,
//                                new Stop(0, Tile.BLUE),
//                                new Stop(1, Color.TRANSPARENT))))
//                .build();
//
//        StackPane root = new StackPane(areaChartTile);
//        Scene scene = new Scene(root, 400, 200);
//
//        stage.setTitle("TileFX SparkLine Chart (TileBuilder)");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//
//
//}

package org.example.smartplantcare;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class StatusPanel extends VBox {
    private HBox welcomePane = new HBox();
    private HBox measurementPane;

    private final Text welcomeText = new Text("Welcome");
    private Text plantName = new Text("Plant Name");

    private final Text lightMeasurement = new Text("-");
    private final Text temperatureMeasurement = new Text("-");
    private final Text waterMeasurement = new Text("-");
    private final Text humidityMeasurement = new Text("-");

    public StatusPanel() {
        // Set fonts and sizes for welcome text
        int WELCOME_TEXT_FONT_SIZE = 20;
        welcomeText.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, WELCOME_TEXT_FONT_SIZE));
        plantName.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, WELCOME_TEXT_FONT_SIZE));

        // Create labels for measurement status
        Label lightLabel = new Label("Light");
        Label temperatureLabel = new Label("Temperature");
        Label waterLabel = new Label("Water");
        Label humidityLabel = new Label("Humidity");

        // Set fonts and sizes for measurement labels
        int STATUS_LABEL_FONT_SIZE = 30;
        lightLabel.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_LABEL_FONT_SIZE));
        temperatureLabel.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_LABEL_FONT_SIZE));
        waterLabel.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_LABEL_FONT_SIZE));
        humidityLabel.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_LABEL_FONT_SIZE));

        // Set fonts and sizes for measurement texts
        int STATUS_MEASUREMENT_FONT_SIZE = 20;
        lightMeasurement.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_MEASUREMENT_FONT_SIZE));
        temperatureMeasurement.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_MEASUREMENT_FONT_SIZE));
        waterMeasurement.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_MEASUREMENT_FONT_SIZE));
        humidityMeasurement.setFont(Font.font(String.valueOf(Font.getDefault()), FontWeight.BOLD, STATUS_MEASUREMENT_FONT_SIZE));

        // Give padding and proper alignment to welcomePane
        this.setPadding(new Insets(20));
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        welcomePane = new HBox(welcomeText, region, plantName);
        HBox.setMargin(welcomePane, new Insets(0, 0, 0, 0));

        // Add the labels and texts to measurementPane
        // and make them properly aligned
        final Region filler1 = new Region(), filler2 = new Region(), filler3 = new Region();
        HBox.setHgrow(filler1, Priority.ALWAYS);
        HBox.setHgrow(filler2, Priority.ALWAYS);
        HBox.setHgrow(filler3, Priority.ALWAYS);
        measurementPane = new HBox(new VBox(lightLabel, lightMeasurement), filler1,
                new VBox(temperatureLabel, temperatureMeasurement), filler2,
                new VBox(waterLabel, waterMeasurement), filler3,
                new VBox(humidityLabel, humidityMeasurement));

        // Add both panes to StatusPanel
        this.getChildren().addAll(welcomePane, measurementPane);
    }

    public void drawStatus(float light, float temperature, float water, float humidity) {
        // Update the measurement texts based
        // on the new measurement
        lightMeasurement.setText(String.valueOf(light));
        temperatureMeasurement.setText(temperature + "°");
        waterMeasurement.setText(String.valueOf(water));
        humidityMeasurement.setText(humidity + "%");
    }

    private void drawText(int x, int y,int sz, String s) {
        int x0=20,xd=160, sz1=20, sz2=40;
        //GraphicsContext gc=canvas.getGraphicsContext2D();
        //gc.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD ,sz));
        //gc.fillText(s, x,y);
    }
}

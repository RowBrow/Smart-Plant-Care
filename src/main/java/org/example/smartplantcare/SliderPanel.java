package org.example.smartplantcare;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import static org.example.smartplantcare.HelperMethods.*;

import java.awt.*;
import java.util.List;

public class SliderPanel extends VBox {
    public Slider sliderLight = HelperMethods.slider();
    public Slider sliderTemp = HelperMethods.slider();
    public Slider sliderWater = HelperMethods.slider();

    public Button buttonProfile = HelperMethods.button("Profiles");
    public Button buttonSaveProfile = HelperMethods.button("Save to profiles");

    public Label valueLight = new Label(" ");
    public Label valueTemp = new Label(" ");
    public Label valueWater = new Label(" ");
    public TextField numWater = new TextField();

    public ComboBox<String> combo = new ComboBox<>();
    public List<String> list = List.of("a month","a week", "a day");

    public SliderPanel() {

        valueLight.setStyle("-fx-font-size: 18px;");
        valueTemp.setStyle("-fx-font-size: 18px;");
        valueWater.setStyle("-fx-font-size: 18px;");



        combo.getItems().addAll(list);

        VBox sliderBox = new VBox(
                new HBox(label("Light"), sliderLight, valueLight),
                new HBox(label("Temp"), sliderTemp, valueTemp),
                new HBox(label("Water"), sliderWater,valueWater));
        sliderBox.setPrefSize(600, 150);

        HBox buttonBox = new HBox(hspace(400), buttonProfile, buttonSaveProfile, hspace(50));
        buttonBox.setPrefSize(600, 50);

        this.getChildren().addAll(
                vspace(80),
                sliderBox,
                buttonBox
        );
    }
}

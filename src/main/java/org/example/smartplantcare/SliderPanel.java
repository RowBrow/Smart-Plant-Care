package org.example.smartplantcare;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import static org.example.smartplantcare.HelperMethods.*;
import static org.example.smartplantcare.MainScreen.*;

public class SliderPanel {
    public static Slider sliderLight = HelperMethods.slider();
    public static Slider sliderTemp = HelperMethods.slider();
    public static Slider sliderWater = HelperMethods.slider();

    public static Button buttonProfile = HelperMethods.button("Profiles");
    public static Button buttonSaveProfile = HelperMethods.button("Save to profiles");

    public static Label valueLight = new Label(" ");
    public static Label valueTemp = new Label(" ");
    public static Label valueWater = new Label(" ");

    static {
        sliderLight.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        valueLight.setText(String.valueOf(newValue.intValue()));
                    }
                });
        sliderTemp.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        valueTemp.setText(String.valueOf(newValue.intValue()));
                    }
                });

        sliderWater.valueProperty().addListener(
                new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number>
                                                observable, Number oldValue, Number newValue) {
                        valueWater.setText(String.valueOf(newValue.intValue()));
                    }
                });
    }

    public static VBox sliderPanel() {  // sPanel, buttonbox
        VBox sliderbox = new VBox(
                new HBox(label("Light"), sliderLight, valueLight),
                new HBox(label("Temp"), sliderTemp, valueTemp),
                new HBox(label("Water"), sliderWater,valueWater));
        sliderbox.setPrefSize(600, 150);

        HBox buttonbox = new HBox(hspace(400), buttonProfile, buttonSaveProfile, hspace(50));
        buttonbox.setPrefSize(600, 50);

        return new VBox(vspace(80), sliderbox,buttonbox);
    }


}

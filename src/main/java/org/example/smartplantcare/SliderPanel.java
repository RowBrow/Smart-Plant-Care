package org.example.smartplantcare;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static org.example.smartplantcare.HelperMethods.*;
import static org.example.smartplantcare.MainScreen.*;

public class SliderPanel {
    public static Slider sliderLight = HelperMethods.slider();
    public static Slider sliderTemp = HelperMethods.slider();
    public static Slider sliderWater = HelperMethods.slider();

    public static Button buttonProfile = HelperMethods.button("Profiles");
    public static Button buttonSaveProfile = HelperMethods.button("Save to profiles");

    public static VBox sliderPanel() {  // sPanel, buttonbox
        VBox sliderbox = new VBox(
                new HBox(label("Light"), sliderLight),
                new HBox(label("Temp"), sliderTemp),
                new HBox(label("Water"), sliderWater));
        sliderbox.setPrefSize(600, 150);

        HBox buttonbox = new HBox(hspace(400), buttonProfile, buttonSaveProfile, hspace(50));
        buttonbox.setPrefSize(600, 50);

        return new VBox(sliderbox,buttonbox);
    }
}

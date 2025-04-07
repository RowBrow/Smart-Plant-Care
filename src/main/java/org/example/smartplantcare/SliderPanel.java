package org.example.smartplantcare;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static org.example.smartplantcare.HelperMethods.*;
import static org.example.smartplantcare.MainScreen.*;

public class SliderPanel {
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

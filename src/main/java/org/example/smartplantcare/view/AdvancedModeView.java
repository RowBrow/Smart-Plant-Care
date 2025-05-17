package org.example.smartplantcare.view;

import javafx.scene.layout.VBox;
import org.example.smartplantcare.SliderPanel;

public class AdvancedModeView extends VBox {
    private VBox statusPanel;
    private VBox sliderPanel;

    public AdvancedModeView() {
        statusPanel = new StatusPanel();
        sliderPanel = new SliderPanel();

        this.getChildren().addAll(statusPanel, sliderPanel);
    }
}

package org.example.smartplantcare.view;

import javafx.scene.layout.VBox;
import org.example.smartplantcare.UIComponents.SliderPanel;
import org.example.smartplantcare.UIComponents.StatusPanel;

public class AdvancedModeView extends VBox {
    public StatusPanel statusPanel;
    public SliderPanel sliderPanel;
    private VBox actionBar;

    public AdvancedModeView() {
        statusPanel = new StatusPanel();
        sliderPanel = new SliderPanel();

        this.getChildren().addAll(statusPanel, sliderPanel);
    }
}

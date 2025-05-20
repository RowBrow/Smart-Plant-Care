package org.example.smartplantcare.view;

import javafx.scene.layout.VBox;
import org.example.smartplantcare.ActionBarView;
import org.example.smartplantcare.SliderPanel;

public class AdvancedModeView extends VBox {
    public StatusPanel statusPanel;
    public SliderPanel sliderPanel;
    private VBox actionBar;

    public AdvancedModeView() {
        statusPanel = new StatusPanel();
        sliderPanel = new SliderPanel();
        actionBar = new ActionBarView();

        this.getChildren().addAll(statusPanel, sliderPanel, actionBar);
    }
}

package org.example.smartplantcare;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ActionBarView extends VBox {
    private Label waterLabel = new Label("Water:");
    private Label lightLabel = new Label("Light:");

    private CheckBox waterCheckBox = new CheckBox();
    private CheckBox lightCheckBox = new CheckBox();

    private Button sendActionButton = new Button("Send Action");

    public ActionBarView() {
        HBox waterOptionBox = new HBox(waterLabel, waterCheckBox);
        waterOptionBox.setAlignment(Pos.CENTER);
        HBox lightOptionBox = new HBox(lightLabel, lightCheckBox);
        lightOptionBox.setAlignment(Pos.CENTER);
        this.getChildren().addAll(waterOptionBox, lightOptionBox, sendActionButton);
        this.setAlignment(Pos.CENTER);
    }
}

package org.example.smartplantcare.UIComponents;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class HelperMethods {
    public final static String BUTTON_STYLE = "-fx-font-family:sans-serif; -fx-font-weight:bold; -fx-font-size: 15px;" ;

    public static Button button(String s) {
        Button b = new Button(s);
        b.setPrefSize(150,30);
        b.setWrapText(true);
        b.setStyle(BUTTON_STYLE);
        return b;
    }

    public static List<Button> buttons(String ... strs) {
        List<Button> buttons = new ArrayList<>();
        for(String s: strs) {
            buttons.add(button(s));
        }
        return buttons;
    }

    public static Label label(String s) {
        Label l = new Label(s);
        l.setPrefSize(150,60);
        l.setStyle(BUTTON_STYLE);
        return l;
    }
    public static HBox hspace(int i) {
        HBox hbox = new HBox();
        hbox.setPrefSize(i,2);
        return hbox;
    }

    public static VBox vspace(int i) {
        VBox vbox = new VBox();
        vbox.setPrefSize(2,i);
        return vbox;
    }

    public static Slider slider() {
        Slider slider = new Slider(0,100,0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(false);
        slider.setPrefSize(500,150);
        return slider;
    }
}

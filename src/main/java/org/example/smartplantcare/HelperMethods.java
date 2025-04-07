package org.example.smartplantcare;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class HelperMethods {
    public static String style1="-fx-font-family:sans-serif; -fx-font-weight:bold; -fx-font-size: 12px;" ;

    public static Button button(String s){
        Button b = new Button(s);
        b.setPrefSize(150,30);
        b.setWrapText(true);
        b.setStyle(style1);
        return b;
    }
    public static List<Button> buttons(String ... strs){
        List<Button> buttons = new ArrayList<>();
        for(String s:strs)buttons.add(button(s));
        return buttons;
    }

    public static Label label(String s){
        Label l = new Label(s); l.setPrefSize(100,30); l.setStyle(style1); return l;
    }
    public static HBox hspace(int i) {
        HBox hbox = new HBox(); hbox.setPrefSize(i,2); return hbox;
    }

    public static VBox vspace(int i) {
        VBox vbox = new VBox(); vbox.setPrefSize(2,i); return vbox;
    }

    public static Slider slider() {
        Slider slider = new Slider();
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setPrefSize(400,50);
        return slider;
    }
}

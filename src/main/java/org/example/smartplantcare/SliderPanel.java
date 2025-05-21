package org.example.smartplantcare;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.util.Duration;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import static org.example.smartplantcare.HelperMethods.*;

import java.awt.*;
import java.awt.TextField;
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

        // We create the tooltips or info icons, where if we hover over them we will see displayed the meaning of the 0-100 scale

        //we get the icons and set the size
        FontIcon lightInfoIcon = new FontIcon(FontAwesomeSolid.INFO_CIRCLE);
        FontIcon waterInfoIcon = new FontIcon(FontAwesomeSolid.INFO_CIRCLE);
        FontIcon tempInfoIcon = new FontIcon(FontAwesomeSolid.INFO_CIRCLE);

        lightInfoIcon.setIconSize(18);
        waterInfoIcon.setIconSize(18);
        tempInfoIcon.setIconSize(18);

        // we set the message that will be displayed
        Tooltip lightToolTip = new Tooltip("0: Complete darkness, 100: Maximum brightness (e.g., direct flashlight on the sensor)");
        Tooltip waterToolTip = new Tooltip("0: Completely dry, 100: Fully submerged in water");
        Tooltip tempToolTip = new Tooltip("Temperature in ° Celsius");

        // we associate each icon with the tooltip
        Tooltip.install(lightInfoIcon, lightToolTip);
        Tooltip.install(waterInfoIcon, waterToolTip);
        Tooltip.install(tempInfoIcon, tempToolTip);

        //we set the delay that we want the message to appear after the user hovers over the icon
        lightToolTip.setShowDelay(Duration.millis(100));
        waterToolTip.setShowDelay(Duration.millis(100));
        tempToolTip.setShowDelay(Duration.millis(100));

        VBox sliderBox = new VBox(
                new HBox(label("Light"), sliderLight, valueLight, hspace(10), lightInfoIcon),
                new HBox(label("Temp"), sliderTemp, valueTemp, hspace(10), tempInfoIcon),
                new HBox(label("Water"), sliderWater,valueWater, hspace(10), waterInfoIcon));
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

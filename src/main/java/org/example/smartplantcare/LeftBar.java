package org.example.smartplantcare;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.util.List;

public class LeftBar extends VBox {
    static Image logo = new Image(LeftBar.class.getResource("/images/logo.png").toExternalForm());
    public static String style="-fx-font-family:poppins-italic-text; -fx-font-weight:bold; -fx-font-size: 12px;";

    public LeftBar() {
        List<Button> navButton = HelperMethods.buttons("DashBoard", "AutoMode", "Advanced Mode", "Plant list", "Settings", "FeedBack");
        navButton.get(0).setOnMouseClicked(e -> {
            MainApp.switchScene(MainApp.getChartPanel());
        });

        navButton.get(2).setOnMouseClicked(e -> {
            MainApp.switchScene(MainApp.getSliderPanel());
        });
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(150);  // Resize width
        logoImageView.setPreserveRatio(true);

        Region filler = new Region();
        VBox.setVgrow(filler, Priority.ALWAYS);
        this.getChildren().addAll(
                logoImageView,
                navButton.get(0), navButton.get(1),
                navButton.get(2), navButton.get(3), filler,
                navButton.get(4), navButton.get(5));
    }
}

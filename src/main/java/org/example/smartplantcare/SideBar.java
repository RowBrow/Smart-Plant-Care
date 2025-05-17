package org.example.smartplantcare;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.util.List;

public class SideBar extends VBox {
    static Image logo = new Image(SideBar.class.getResource("/images/logo.png").toExternalForm());
    public static String style="-fx-font-family:poppins-italic-text; -fx-font-weight:bold; -fx-font-size: 12px;";

    public SideBar() {
        List<Button> navButton = HelperMethods.buttons("DashBoard", "AutoMode", "Advanced Mode", "Plant list", "Settings", "FeedBack");
        navButton.get(0).setOnMouseClicked(_ -> MainApp.switchScene(MainApp.getDashboardScene()));

        navButton.get(2).setOnMouseClicked(_ -> MainApp.switchScene(MainApp.getAdvancedModeScene()));
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

package org.example.smartplantcare;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.util.List;

import static org.example.smartplantcare.HelperMethods.label;
import static org.example.smartplantcare.HelperMethods.vspace;

public class LeftBar {
    public static VBox createLeft(String style, Image logo, List<Button> navButton) {
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(150);  // Resize width
        logoImageView.setPreserveRatio(true);

        VBox left = new VBox();
        left.setPrefSize(200, 500);
        left.getChildren().addAll(
                label("two leafs"), vspace(50),
                logoImageView,
                navButton.get(0), navButton.get(1),
                navButton.get(2), navButton.get(3), vspace(300),
                navButton.get(4), navButton.get(5));

        return left;
    }
}


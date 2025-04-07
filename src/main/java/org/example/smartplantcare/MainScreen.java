package org.example.smartplantcare;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MainScreen extends Application {
    public MainScreen() throws FileNotFoundException {  }

    //font style
    public static String style1="-fx-font-family:sans-serif; -fx-font-weight:bold; -fx-font-size: 12px;" ;
    public static String style2="-fx-font-family:poppins-italic-text; -fx-font-weight:bold; -fx-font-size: 12px;" ;

    public static Slider sliderLight = HelperMethods.slider();
    public static Slider sliderTemp = HelperMethods.slider();
    public static Slider sliderWater = HelperMethods.slider();

    public static Button buttonProfile = HelperMethods.button("Profiles");
    public static Button buttonSaveProfile = HelperMethods.button("Save to profiles");
    public static List<Button> navButton = HelperMethods.buttons("DashBoard","AutoMode","Advanced Mode","Plant list","Settings","FeedBack");

    public static Canvas canvas = new Canvas(600,300);

    @Override
    public void start(Stage stage) {
        Image logo = new Image(getClass().getResource("/images/logo.png").toExternalForm());

        String fontPath = getClass().getResource("/fonts/Poppins-ThinItalic.ttf").toExternalForm();
        fontPath = URLDecoder.decode(fontPath, StandardCharsets.UTF_8);

        System.out.println("Font path: " + fontPath); // Debugging

        //Font.loadFont(fontPath, 10);

        //Font.getFamilies().forEach(System.out::println);
        //We set the CSS classes to the components:

        /*font
        dashboardButton = new Button("Dashboard", new FontIcon(FontAwesomeSolid.CHART_BAR));
        dashboardButton.setGraphicTextGap(20);
        dashboardButton.getStyleClass().add("poppins-italic-text");

        (sideBar).setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        */

        VBox left = LeftBar.createLeft(style2, logo, navButton);
        VBox right = new VBox(canvas,SliderPanel.sliderPanel());
        right.setPrefSize(600,500);
        HBox root = new HBox(left,right);

        StatusPanel statusPanel = new StatusPanel(canvas);
        statusPanel.drawStatus();

        Scene scene = new Scene(root,800,500);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package frontend;

import javafx.scene.text.Font;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SPCApp extends Application {


    Button dashboardButton = new Button();
    Button advancedButton = new Button("Advanced");
    Button feedbackButton = new Button("Feedback");

    public SPCApp() throws FileNotFoundException {
    }

    @Override
    public void start(Stage stage) {

        String fontPath = getClass().getResource("/fonts/Poppins-ThinItalic.ttf").toExternalForm();
        fontPath = URLDecoder.decode(fontPath, StandardCharsets.UTF_8);

        System.out.println("Font path: " + fontPath); // Debugging

        //Font.loadFont(fontPath, 10);

        //Font.getFamilies().forEach(System.out::println);
        //We set the CSS classes to the components:



        dashboardButton = new Button("Dashboard", new FontIcon(FontAwesomeSolid.CHART_BAR));
        dashboardButton.setGraphicTextGap(20);
        dashboardButton.getStyleClass().add("poppins-italic-text");

        Image logo = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        ImageView logoImageView = new ImageView(logo);

        logoImageView.setFitWidth(150);  // Resize width
        logoImageView.setPreserveRatio(true);

        VBox sideBar=new VBox();
        sideBar.setPadding(new Insets(20)); // Inner padding for the VBox
        sideBar.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Set margins for each component inside the VBox
        VBox.setMargin(logoImageView, new Insets(30, 0, 0, 40)); // Top, Right, Bottom, Left
        VBox.setMargin(dashboardButton, new Insets(10));
        VBox.setMargin(advancedButton, new Insets(10));
        VBox.setMargin(feedbackButton, new Insets(10));

        // Create a spacer to push Settings button to the bottom
        Region spacer = new Region();
        spacer.setMinHeight(Region.USE_COMPUTED_SIZE);
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Add elements to the VBox
        sideBar.getChildren().addAll(logoImageView, dashboardButton, advancedButton, spacer, feedbackButton);


        Scene scene = new Scene(sideBar, 250, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
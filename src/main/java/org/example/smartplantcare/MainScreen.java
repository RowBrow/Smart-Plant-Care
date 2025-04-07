package org.example.smartplantcare;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.FileNotFoundException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MainScreen extends Application {
    public MainScreen() throws FileNotFoundException {
    }
    String style1="-fx-font-family:sans-serif; -fx-font-weight:bold; -fx-font-size: 12px;" ;
    String style2="-fx-font-family:poppins-italic-text; -fx-font-weight:bold; -fx-font-size: 12px;" ;
    Slider sliderLight =slider();


    private Slider slider() {
        Slider slider = new Slider();
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setPrefSize(400,50);
        return slider;
    }

    Slider sliderTemp =slider();
    Slider sliderWater =slider();
    Button buttonProfile = button("Profiles");
    Button buttonSaveProfile = button("Save to profiles");
    List<Button> navButton = buttons("DashBoard","AutoMode","Advanced Mode","Plant list","Settings","FeedBack");
    Canvas canvas = new Canvas(600,300);

    @Override
    public void start(Stage stage) {

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

        Image logo = new Image(getClass().getResource("/images/logo.png").toExternalForm());
        ImageView logoImageView = new ImageView(logo);

        logoImageView.setFitWidth(150);  // Resize width
        logoImageView.setPreserveRatio(true);

        VBox left = new VBox();
        left.setPrefSize(200,500);
        left.getChildren().addAll(label("two leafs"),vspace(50));
        left.getChildren().addAll( logoImageView, navButton.get(0),navButton.get(1),
                navButton.get(2),navButton.get(3),vspace(300),
                navButton.get(4),navButton.get(5));
        VBox sliderbox = new VBox(
                new HBox(label("Light"),sliderLight),
                new HBox(label("Temp"),sliderTemp),
                new HBox(label("Water"),sliderWater));
        sliderbox.setPrefSize(600,150);
        HBox buttonbox = new HBox(hspace(400),buttonProfile,buttonSaveProfile,hspace(50));
        buttonbox.setPrefSize(600,50);
        VBox right = new VBox(canvas,sliderbox,buttonbox);
        right.setPrefSize(600,500);
        HBox root = new HBox(left,right);
        drawCanvas();
        Scene scene = new Scene(root,800,500);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        stage.setTitle("Smart Plant Care");
        stage.setScene(scene);
        stage.show();
    }

    void drawCanvas(){
        drawText(50,50,25,"Welcome, "); // add user name
        drawText(450,50,15,"Flowering Plant"); // selected plants from list should be shown here
        drawText(450,80,10,"Monstera Deliciosa");
        int x0=50,xd=130, sz1=20, sz2=40;
        drawText(x0,200,sz1,"Light");
        drawText(x0,250,sz2,"50%");
        drawText(x0+xd,200,sz1,"Temp");
        drawText(x0+xd,250,sz2,"15\u00B0C");
        drawText(x0+2*xd,200,sz1,"Water");
        drawText(x0+2*xd,250,sz2,"50%");
        drawText(x0+3*xd,200,sz1,"Humidity");
        drawText(x0+3*xd,250,sz2,"50%");
    }
    private void drawText(int x, int y,int sz, String s) {
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.setFont(Font.font(Font.getDefault().getFamily(),FontWeight.BOLD ,sz));
        gc.fillText(s, x,y);
    }

    // move this part into Fucntions
    Button button(String s){
        Button b = new Button(s);
        b.setPrefSize(150,30);
        b.setWrapText(true);
        b.setStyle(style1);
        return b;
    }
    List<Button> buttons(String ... strs){
        List<Button> buttons = new ArrayList<>();
        for(String s:strs)buttons.add(button(s));
        return buttons;
    }
    Label label(String s){
        Label l = new Label(s); l.setPrefSize(100,30); l.setStyle(style2); return l;
    }
    HBox hspace(int i) {
        HBox hbox = new HBox(); hbox.setPrefSize(i,2); return hbox;
    }

    VBox vspace(int i) {
        VBox vbox = new VBox(); vbox.setPrefSize(2,i); return vbox;
    }

    public static void main(String[] args) {
        launch();
    }
}
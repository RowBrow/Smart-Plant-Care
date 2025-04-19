package org.example.smartplantcare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends Application {
    @Override
    public void start(Stage stage) {
        //creating label username
        Text text1 = new Text("Username");

        //creating label password
        Text text2 = new Text("Password");

        //Creating Text Filed for username
        TextField textField1 = new TextField();

        //Creating Text Filed for password
        PasswordField textField2 = new PasswordField();

        //Creating Buttons
        Button button1 = new Button("Login");
        //Button button2 = new Button("Clear");

        //Creating a Grid Pane
        GridPane gridPane = new GridPane();

        //Setting size for the pane
        gridPane.setMinSize(400, 200);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        //Arranging all the nodes in the grid
        gridPane.add(text1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(text2, 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(button1, 0, 2);
        //gridPane.add(button2, 1, 2);
        gridPane.setStyle("-fx-background-color: BEIGE;");

       /* button1.setOnAction(e -> {
            String username = text1.getText();
            String password = text2.getText(); // 실제론 해시 사용 추천

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password); // 실제 앱에서는 해시된 비밀번호 사용 권장
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Login succeed, user: " + rs.getString("name"));
                    // move to the next screen
                } else {
                    System.out.println("Login failed");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        */

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        //Parent root = loader.load();
        button1.getScene().getWindow();

        //stage.setScene(new Scene(root));

        //Creating a scene object
        Scene scene = new Scene(gridPane);

        //Setting title to the Stage
        stage.setTitle("Login page");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}



// from https://www.tutorialspoint.com/javafx/javafx_ui_controls.htm
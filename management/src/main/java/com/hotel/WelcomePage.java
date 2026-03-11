package com.hotel;

import com.hotel.view.ReceptionLoginSignupPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomePage {

    private final Stage primaryStage;

    public WelcomePage(Stage stage) {
        this.primaryStage = stage;
    }

    public void show() {
        primaryStage.setScene(getView(primaryStage));
        primaryStage.setTitle("Welcome");
        primaryStage.show();
    }

    public static Scene getView(Stage primaryStage) {
        // Background Image
        Image backgroundImage = new Image(WelcomePage.class.getResource("/Assets/Images/loginbackground.png").toExternalForm());
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1920);
        backgroundImageView.setFitHeight(1080);
        backgroundImageView.setPreserveRatio(false);

        // Welcome Text
        Text welcomeText = new Text("Welcome to Our Restaurant");
        welcomeText.setFont(Font.font("Arial", 60));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setEffect(new DropShadow(5, Color.BLACK));

        // User Side Button
        Button nextButton = new Button("UserSide Login");
        nextButton.setFont(Font.font("Arial", 20));
        nextButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-background-radius: 10;");
       nextButton.setOnAction(e -> {
        UserLoginSignupPage userLoginSignupPage = new UserLoginSignupPage();
    // TableSelectionPage tableSelectionPage = new TableSelectionPage(primaryStage);
    primaryStage.setScene(userLoginSignupPage.getView(primaryStage));
});



                // About Us & Reception Buttons (top-right)
        Button receptionLoginButton = new Button("Reception Login");
        receptionLoginButton.setFont(Font.font("Arial", 20));
        receptionLoginButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 8;");
        receptionLoginButton.setOnAction(e -> {
            primaryStage.setScene(ReceptionLoginSignupPage.getView(primaryStage));
        });

        Button aboutUsButton = new Button("About Us");
        aboutUsButton.setFont(Font.font("Arial", 20));
        aboutUsButton.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-background-radius: 8;");
        aboutUsButton.setOnAction(e -> {
            AboutUsPage aboutUsPage = new AboutUsPage(primaryStage);
            primaryStage.setScene(aboutUsPage.getView());
        });

        HBox topRightButtons = new HBox(40, aboutUsButton,nextButton ,receptionLoginButton);
        topRightButtons.setPadding(new Insets(10));
        topRightButtons.setAlignment(Pos.CENTER);
       
        // VBox for Welcome text and User button
        VBox messageBox = new VBox(40, welcomeText,topRightButtons);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(50));
        messageBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-background-radius: 20;");

        StackPane root = new StackPane(backgroundImageView, messageBox);
        StackPane.setAlignment(messageBox, Pos.CENTER);

        primaryStage.setMaximized(true);

        return new Scene(root, 1550,800);
    }
}

package com.hotel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ThankyouPage {

    public static Scene getView(Stage primaryStage) {
        // Root layout
        StackPane root = new StackPane();

        // Background image
        ImageView background = new ImageView(new Image(
                ThankyouPage.class.getResource("/Assets/Images/image.png").toExternalForm()));
        background.setFitWidth(1920);
        background.setFitHeight(1080);
        background.setPreserveRatio(false);

        // Semi-transparent glass panel
        VBox contentBox = new VBox(25);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(50));
        contentBox.setMaxWidth(600);
        contentBox.setStyle(
                "-fx-background-color: rgba(253, 253, 253, 0.27); " +
                "-fx-background-radius: 20; " +
                "-fx-border-radius: 20; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 2;"
        );
        contentBox.setEffect(new DropShadow(20, Color.BLACK));

        // Thank You Label
        Label thankYouLabel = new Label("Thank You for Visiting!");
        thankYouLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 42));
        thankYouLabel.setTextFill(Color.WHITE);

        // Subtext
        Label subText = new Label("We hope you had a great experience.");
        subText.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
        subText.setTextFill(Color.LIGHTGRAY);

        // Back Button
        Button backButton = new Button("Back to Home");
        backButton.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-padding: 10 30; " +
                "-fx-background-color: #00cec9; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 30;"
        );
        backButton.setOnMouseEntered(e -> backButton.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-padding: 10 30; " +
                "-fx-background-color: #0984e3; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 30;"
        ));
        backButton.setOnMouseExited(e -> backButton.setStyle(
                "-fx-font-size: 18px; " +
                "-fx-padding: 10 30; " +
                "-fx-background-color: #00cec9; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 30;"
        ));

        backButton.setOnAction(e -> {
            primaryStage.setScene(WelcomePage.getView(primaryStage));
        });

        contentBox.getChildren().addAll(thankYouLabel, subText, backButton);

        // Add components to root
        root.getChildren().addAll(background, contentBox);
        primaryStage.setMaximized(true);

        return new Scene(root, 1550,800);
    }
}
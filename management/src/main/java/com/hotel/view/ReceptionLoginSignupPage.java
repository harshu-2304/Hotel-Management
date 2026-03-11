package com.hotel.view;

import com.hotel.FirebaseAuthService;
import com.hotel.WelcomePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ReceptionLoginSignupPage {

    public static Scene getView(Stage primaryStage) {
        StackPane root = new StackPane();

        // Background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("/Assets/Images/loginbackground.png", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1920, 1080, false, false, false, false)
        );
        root.setBackground(new Background(backgroundImage));

        // Center pane with shadowed card
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(500);
        card.setStyle(
                "-fx-background-color: rgba(255,255,255,0.96);" +
                "-fx-border-radius: 12px;" +
                "-fx-background-radius: 12px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 4, 4);"
        );

        Label title = new Label("Reception Login / Signup");
        title.setFont(Font.font("Arial", 32));
        title.setTextFill(Color.web("#2d3436"));

        TextField emailField = new TextField();
        emailField.setPromptText("Reception Email");
        emailField.setPrefHeight(45);
        emailField.setFont(Font.font(15));
        emailField.setStyle("-fx-background-radius: 8;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(45);
        passwordField.setFont(Font.font(15));
        passwordField.setStyle("-fx-background-radius: 8;");

        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        messageLabel.setFont(Font.font(14));

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("Login");
        Button signupBtn = new Button("Signup");

        loginBtn.setPrefWidth(120);
        signupBtn.setPrefWidth(120);

        loginBtn.setStyle("-fx-background-color: #0984e3; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 8;");
        signupBtn.setStyle("-fx-background-color: #00b894; -fx-text-fill: white; -fx-font-size: 15px; -fx-background-radius: 8;");

        buttonBox.getChildren().addAll(loginBtn, signupBtn);

        Button backBtn = new Button("← Back to Welcome");
        backBtn.setFont(Font.font("Arial", 14));
        backBtn.setStyle("-fx-background-color: #d63031; -fx-text-fill: white; -fx-background-radius: 8;");
        backBtn.setOnAction(e -> primaryStage.setScene(WelcomePage.getView(primaryStage)));

        card.getChildren().addAll(title, emailField, passwordField, buttonBox, messageLabel, backBtn);

        VBox wrapper = new VBox(card);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(60));

        root.getChildren().add(wrapper);

        // Login Action
        loginBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter both email and password.");
                return;
            }

            FirebaseAuthService.login(email, password, success -> {
                if (success) {
                    messageLabel.setTextFill(Color.GREEN);
                    messageLabel.setText("Login successful!");
                    primaryStage.setScene(ReceptionDashboard.getView(primaryStage));
                } else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Login failed. Please check credentials.");
                }
            });
        });

        // Signup Action
        signupBtn.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter both email and password.");
                return;
            }

            FirebaseAuthService.signup(email, password, success -> {
                if (success) {
                    messageLabel.setTextFill(Color.GREEN);
                    messageLabel.setText("Signup successful! Please login.");
                } else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Signup failed. Email may already be in use.");
                }
            });
        });
        primaryStage.setMaximized(true);
        return new Scene(root, 1750, 980);
    }
}


package com.hotel;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserLoginSignupPage {
    private static String currentUserEmail;


    public static Scene getView(Stage primaryStage) {

        primaryStage.setMaximized(true);
        // === Background Image ===
        Image bgImage = new Image(UserLoginSignupPage.class.getResource("/Assets/Images/userlogin_back.jpg").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                bgImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false)
        );

        // === Root StackPane ===
        StackPane root = new StackPane();
        root.setBackground(new Background(background));
        root.setPadding(new Insets(50)); // Equal padding from all sides

        // === Inner Frame ===
        VBox frame = new VBox(20);
        frame.setAlignment(Pos.CENTER);
        frame.setPadding(new Insets(40));
        frame.setMaxWidth(400);
        frame.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: rgba(0,0,0,0.1);" +
                "-fx-border-width: 2px;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 5);"
        );

        // === Title ===
        Label title = new Label("Welcome Back");
        title.setFont(Font.font("Arial", 32));
        title.setTextFill(Color.web("#333"));

        // === Input Fields ===
        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");
        emailField.setMaxWidth(Double.MAX_VALUE);
        emailField.setFont(Font.font(14));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(Double.MAX_VALUE);
        passwordField.setFont(Font.font(14));

        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        messageLabel.setFont(Font.font(14));

        // === Buttons ===
        Button loginBtn = new Button("Sign In");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setFont(Font.font(16));
        loginBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #36d1dc, #5b86e5);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;"
        );
        // loginBtn.setOnAction(e -> {
        //     TableSelectionPage tableSelectionPage = new TableSelectionPage(primaryStage);
        //     primaryStage.setScene(tableSelectionPage.getView());
        // });

        Button signupBtn = new Button("Sign Up");
        signupBtn.setMaxWidth(Double.MAX_VALUE);
        signupBtn.setFont(Font.font(16));
        signupBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #ff9a9e, #fad0c4);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 10;"
        );


    loginBtn.setOnAction(e -> {
    String email = emailField.getText().trim();
    String password = passwordField.getText().trim();
    if (email.isEmpty() || password.isEmpty()) {
        messageLabel.setText("Please enter both email and password.");
        return;
    }
    FirebaseAuthService.login(email, password, success -> {
        if (success) {
            Platform.runLater(() -> {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful!");
                currentUserEmail = email; 
                primaryStage.setScene(TableSelectionPage.getView(primaryStage)); // ✅ UI thread
            });
        } else {
            Platform.runLater(() -> {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Login failed. Check credentials.");
            });
        }
    });
});



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
                    messageLabel.setText("Signup successful! You can now log in.");
                } else {
                    messageLabel.setTextFill(Color.RED);
                    messageLabel.setText("Signup failed. Email may already be used.");
                }
            });
        });

        // === Back Button ===
        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font(14));
        backBtn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-underline: true;"
        );
        backBtn.setOnAction(e -> primaryStage.setScene(WelcomePage.getView(primaryStage)));

        // === Add All to Frame ===
        frame.getChildren().addAll(
                title,
                emailField,
                passwordField,
                loginBtn,
                signupBtn,
                messageLabel
        );

        // === Layout ===
        VBox layout = new VBox(10, frame, backBtn);
        layout.setAlignment(Pos.CENTER);

        root.getChildren().add(layout);
        

        return new Scene(root, 1550, 800);
    }
    public static String getCurrentUserEmail() {
    return currentUserEmail;
}

}

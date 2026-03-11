package com.hotel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AboutUsPage {

    private final Stage stage;

    public AboutUsPage(Stage stage) {
        this.stage = stage;
    }

    public Scene getView() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Title
        Text title = new Text("About Smart Dien");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        title.setFill(Color.web("#9e126b"));
        title.setEffect(new DropShadow(10, Color.LIGHTGRAY));

        // Image Grid with labels
        GridPane imageGrid = new GridPane();
        imageGrid.setAlignment(Pos.CENTER);
        imageGrid.setHgap(40);
        imageGrid.setVgap(30);
        imageGrid.setPadding(new Insets(20));

        imageGrid.add(createImageCard("Assets/Images/core2web.png", "Core2Web"), 0, 0);
        imageGrid.add(createImageCard("Assets/Images/Shashi_sir.jpg", "Shashi Sir"), 1, 0);

        // Description
        Text description = new Text(
               

                        "\"The Hotel Table Management System is a JavaFX-based desktop application that streamlines hotel restaurant operations. It allows users to select tables, authenticate via Firebase login/signup, browse a GST-inclusive Veg/Non-Veg menu with images, choose a payment method (Cash, Card, UPI), and complete their order. A Thank You screen is shown after successful payment. The app also includes a reception-side dashboard to manage tables and customer data.\n\n"

                        +"Built with Java 21, JavaFX, and Firebase, the project uses core Java concepts like Object-Oriented Programming, event handling, exception handling, scene switching, and resource loading. It follows clean architecture using one primary stage and multiple scenes, all styled at a fixed resolution.\n\n" 
                                                        
                        + "This website is the result of the skills and knowledge we've gained at Core@2Web, a training institute that helped us transform our passion for technology into real-world applications.\n\n"
                        + "Our passionate team behind Smart Daen includes:\n"

                        + "• Team Leader: Tejaswi Pasalkar\n\n"
                        + "• Team Members: \n"
                                         +"Sejal Londhe\n"
                                         +"Pragati Desai\n"
                                         +"Mayur Chavan\n\n"
                                                        
                        + "We’re incredibly grateful for the guidance and support provided by the amazing faculty at Core@2Web. "
                        + "Special mention to Shashi Sir, whose teaching style and encouragement truly inspired us.\n\n"
                        + "Core@2Web isn't just a class – it’s a community of learners and mentors who empower you to achieve your goals in the tech world."
        );
        description.setFont(Font.font("Segoe UI", 16));
        description.setFill(Color.web("#222"));
        description.setWrappingWidth(screenBounds.getWidth() * 0.75);
        description.setLineSpacing(6);

        // Back Button
        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        backBtn.setStyle(
                "-fx-background-color: #b30944;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 8 24;" +
                        "-fx-cursor: hand;"
        );
        backBtn.setOnAction(e -> {
            WelcomePage welcomePage = new WelcomePage(stage);
            welcomePage.show();
        });

        VBox content = new VBox(40, title, imageGrid, description, backBtn);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40));
        content.setMaxWidth(screenBounds.getWidth() * 0.85);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        StackPane root = new StackPane(scrollPane);
        root.setStyle("-fx-background-color: #fdfcfc;");

        return new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    }

    private VBox createImageCard(String imagePath, String label) {
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream("/" + imagePath));
        } catch (Exception e) {
            System.out.println("Failed to load image: " + imagePath);
            image = new Image("https://via.placeholder.com/100");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(140);
        imageView.setFitHeight(140);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setEffect(new DropShadow(6, Color.DARKGRAY));

        Text text = new Text(label);
        text.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        text.setFill(Color.web("#444"));

        VBox box = new VBox(10, imageView, text);
        box.setAlignment(Pos.CENTER);
        stage.setMaximized(true);
        
        return box;
    }
}

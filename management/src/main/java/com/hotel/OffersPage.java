package com.hotel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OffersPage {

    public static Scene getView(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("""
            -fx-background-color: linear-gradient(to bottom, #fdfcfc, #f2e8dc);
            -fx-padding: 30;
        """);

        Label title = new Label("✨ Premium Offers");
        title.setFont(Font.font("Segoe UI Black", 42));
        title.setStyle("-fx-text-fill: #3e2723;");

        VBox titleBox = new VBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 20, 0));

        VBox offerBox = new VBox(25);
        offerBox.setPadding(new Insets(20));
        offerBox.setAlignment(Pos.TOP_CENTER);

        offerBox.getChildren().addAll(
                createOfferCard("🎉 25% Off All Orders", "Enjoy a flat 25% discount on all menu items for this weekend only.", "/Assets/Images/discount.jpg", true),
                createOfferCard("🍽 Combo Delight", "Order any 2 main courses and get a dessert absolutely free!", "/Assets/Images/offer.jpg", true),
                createOfferCard("🔥 Flash Deal", "Limited time 15% off on all spicy items in the menu.", "/Assets/Images/spicy.jpg", true),
                createOfferCard("🥂 VIP Member Bonus", "Get ₹200 off if you're a registered Smart Dine VIP user.", "/Assets/Images/vip.jpg", true),
                createOfferCard("🎊 Family Feast Offer", "Order for 4+ people and get a 1L cold drink free!", "/Assets/Images/family.jpg", true)
        );

        ScrollPane scrollPane = new ScrollPane(offerBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-border-color: transparent;");

        Button backBtn = new Button("← Back to Menu");
        backBtn.setFont(Font.font("Segoe UI", 18));
        backBtn.setStyle("""
            -fx-background-color: linear-gradient(to right, #673ab7, #512da8);
            -fx-text-fill: white;
            -fx-background-radius: 25;
            -fx-padding: 10 30;
        """);

        backBtn.setOnMouseEntered(e -> backBtn.setStyle("""
            -fx-background-color: linear-gradient(to right, #7e57c2, #673ab7);
            -fx-text-fill: white;
            -fx-background-radius: 25;
            -fx-padding: 10 30;
        """));

        // backBtn.setOnMouseExited(e -> backBtn.setStyle("""
        //     -fx-background-color: linear-gradient(to right, #673ab7, #512da8);
        //     -fx-text-fill: white;
        //     -fx-background-radius: 25;
        //     -fx-padding: 10 30;
        // """));

        backBtn.setOnAction(e -> primaryStage.setScene(MenuPage.getView(primaryStage))); // MenuPage navigation

        VBox centerSection = new VBox(scrollPane, backBtn);
        centerSection.setSpacing(35);
        centerSection.setAlignment(Pos.CENTER);
        centerSection.setPadding(new Insets(10, 40, 20, 40));

        root.setTop(titleBox);
        root.setCenter(centerSection);

        return new Scene(root, 1750, 800);
    }

    private static VBox createOfferCard(String title, String description, String imagePath, boolean clickable) {
        VBox card = new VBox(12);
        card.setPadding(new Insets(22));
        card.setPrefWidth(900);
        card.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 18;
            -fx-border-color: #dddddd;
            -fx-border-radius: 18;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.1, 0, 6);
        """);

        HBox content = new HBox(15);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setStyle("-fx-cursor: hand;");

        // Image
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(OffersPage.class.getResourceAsStream(imagePath));
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
        }
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        VBox textBox = new VBox(5);
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI Semibold", 24));
        titleLabel.setStyle("-fx-text-fill: #5d4037;");

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Georgia", 16));
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-text-fill: #6d6d6d;");

        textBox.getChildren().addAll(titleLabel, descLabel);
        content.getChildren().addAll(imageView, textBox);
        card.getChildren().add(content);

        if (clickable) {
            card.setOnMouseClicked(e -> showCelebration(title));
        }

        return card;
    }

    private static void showCelebration(String offerTitle) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("🎆 Celebration Time!");

        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: white; -fx-border-radius: 15; -fx-background-radius: 15;");

        ImageView boomImage = new ImageView();
        try {
            boomImage.setImage(new Image(OffersPage.class.getResourceAsStream("/Assets/Images/offer.jpeg.jpg")));
        } catch (Exception ex) {
            System.out.println("Boom image not found");
        }
        boomImage.setFitWidth(300);
        boomImage.setPreserveRatio(true);

        Label msg = new Label("You grabbed the offer:\n" + offerTitle);
        msg.setFont(Font.font("Segoe UI Black", 20));
        msg.setStyle("-fx-text-fill: #4caf50;");
        msg.setWrapText(true);
        msg.setAlignment(Pos.CENTER);

        Button okBtn = new Button("OK");
        okBtn.setFont(Font.font("Segoe UI", 16));
        okBtn.setOnAction(e -> dialog.close());

        vbox.getChildren().addAll(boomImage, msg, okBtn);

        Scene scene = new Scene(vbox, 400, 350);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
}
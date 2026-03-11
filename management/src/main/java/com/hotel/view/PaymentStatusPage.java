package com.hotel.view;

import com.hotel.Payment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PaymentStatusPage {

    public static Scene getView(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fdfefe;");

        // ===== Header =====
        HBox header = new HBox();
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #d63031;");
        header.setAlignment(Pos.CENTER);

        Label title = new Label("💳 Payment Status");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);
        header.getChildren().add(title);
        root.setTop(header);

        // ===== Center Content =====
        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        // Card-style container
        VBox listCard = new VBox(25);
        listCard.setPadding(new Insets(35));
        listCard.setAlignment(Pos.TOP_LEFT);
        listCard.setPrefSize(800, 500);
        listCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-radius: 15px;");
        listCard.setEffect(new DropShadow(12, Color.gray(0.5)));

        Label listTitle = new Label("Recent Payment Records");
        listTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        listTitle.setTextFill(Color.web("#2d3436"));

        ListView<String> paymentList = new ListView<>();
        paymentList.getItems().addAll(Payment.getAllPayments());
        paymentList.setStyle("-fx-font-size: 18px; -fx-background-radius: 10px;");
        paymentList.setPrefHeight(350);
        paymentList.setPrefWidth(730);

        listCard.getChildren().addAll(listTitle, paymentList);

        // Back Button
        Button backBtn = new Button("← Back to Dashboard");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        backBtn.setStyle("-fx-background-color: #d63031; -fx-text-fill: white; -fx-background-radius: 10px;");
        backBtn.setPrefWidth(250);
        backBtn.setPrefHeight(45);
        backBtn.setOnAction(e -> primaryStage.setScene(ReceptionDashboard.getView(primaryStage)));

        centerBox.getChildren().addAll(listCard, backBtn);
        root.setCenter(centerBox);
        primaryStage.setMaximized(true);

        return new Scene(root, 1750, 980);
    }
public static Pane getContent(Stage primaryStage) {
    BorderPane root = new BorderPane();
    root.setStyle("-fx-background-color: #fdfefe;");

    HBox header = new HBox();
    header.setPadding(new Insets(20));
    header.setStyle("-fx-background-color: #d63031;");
    header.setAlignment(Pos.CENTER);

    Label title = new Label("💳 Payment Status");
    title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
    title.setTextFill(Color.WHITE);
    header.getChildren().add(title);
    root.setTop(header);

    VBox centerBox = new VBox(30);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.setPadding(new Insets(50));

    VBox listCard = new VBox(25);
    listCard.setPadding(new Insets(35));
    listCard.setAlignment(Pos.TOP_LEFT);
    listCard.setPrefSize(800, 500);
    listCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-radius: 15px;");
    listCard.setEffect(new DropShadow(12, Color.gray(0.5)));

    Label listTitle = new Label("Recent Payment Records");
    listTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
    listTitle.setTextFill(Color.web("#2d3436"));

    ListView<String> paymentList = new ListView<>();
    paymentList.getItems().addAll(Payment.getAllPayments());
    paymentList.setStyle("-fx-font-size: 18px; -fx-background-radius: 10px;");
    paymentList.setPrefHeight(350);
    paymentList.setPrefWidth(730);

    listCard.getChildren().addAll(listTitle, paymentList);
    centerBox.getChildren().addAll(listCard);
    root.setCenter(centerBox);
    return root;
}

}



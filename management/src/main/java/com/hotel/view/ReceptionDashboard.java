package com.hotel.view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import com.hotel.WelcomePage;
import com.hotel.view.OrderViewerPage;


public class ReceptionDashboard {
    private static BorderPane root;
    private static VBox contentArea;

    public static Scene getView(Stage primaryStage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #dfe6e9, #b2bec3);");

        // Sidebar (left)
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setStyle("-fx-background-color: #2d3436;");
        sidebar.setPrefWidth(300);
        sidebar.setPrefHeight(Double.MAX_VALUE);

        Label title = new Label("Reception Panel");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        Button tableBtn = createNavButton("🪑 Table Availability", primaryStage);
        Button orderBtn = createNavButton("📥 Incoming Orders", primaryStage);
        Button paymentBtn = createNavButton("💳 Payments", primaryStage);
       // Button historyBtn = createNavButton("📜 Order History", primaryStage);
        Button billBtn = createNavButton("🧾 Generate Bills", primaryStage);

        tableBtn.setOnAction(e -> setContent(TableControlPage.getContent(primaryStage)));
        orderBtn.setOnAction(e -> setContent(OrderViewerPage.getContent(primaryStage)));
        paymentBtn.setOnAction(e -> setContent(PaymentStatusPage.getContent(primaryStage)));
       // historyBtn.setOnAction(e -> setContent(OrderHistoryPage.getContent(primaryStage)));
        billBtn.setOnAction(e -> setContent(BillGenerationPage.getContent(primaryStage)));

        // Back button
        Button backBtn = new Button("← Back to Welcome");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        backBtn.setStyle("-fx-background-color: #d63031; -fx-text-fill: white; -fx-background-radius: 10;");
        backBtn.setOnAction(e -> primaryStage.setScene(WelcomePage.getView(primaryStage)));

        Region spacer = new Region(); // Pushes the back button to the bottom
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(title, tableBtn, orderBtn, paymentBtn,billBtn, spacer, backBtn);

        // Content Area (center)
        contentArea = new VBox();
        contentArea.setPadding(new Insets(30));
        contentArea.setAlignment(Pos.CENTER);
        contentArea.getChildren().add(new Label("👈 Select an option from the sidebar"));

        root.setLeft(sidebar);
        root.setCenter(contentArea);

        primaryStage.setMaximized(true);
        return new Scene(root, 1550, 800);
    }

    private static Button createNavButton(String text, Stage stage) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btn.setPrefWidth(240);
        btn.setStyle("-fx-background-color: #636e72; -fx-text-fill: white; -fx-background-radius: 8;");
        return btn;
    }

    private static void setContent(Pane content) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }
}

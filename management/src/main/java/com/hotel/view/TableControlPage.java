package com.hotel.view;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class TableControlPage {

    public static Map<Integer, String> tableStatusMap = new HashMap<>();

    public static Scene getView(Stage primaryStage) {
        initializeTableStatus();  // Ensure all tables are initialized

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0f7fa, #b2ebf2);");

        Label title = new Label("📋 Table Control Dashboard");
        title.setFont(Font.font("Arial", 36));
        title.setTextFill(Color.web("#006064"));
        title.setPadding(new Insets(30, 0, 30, 0));

        VBox topSection = new VBox(title);
        topSection.setAlignment(Pos.CENTER);
        root.setTop(topSection);

        GridPane tableGrid = new GridPane();
        tableGrid.setHgap(40);
        tableGrid.setVgap(40);
        tableGrid.setPadding(new Insets(50));
        tableGrid.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 12; i++) {
            int tableNum = i;

            VBox tableBox = new VBox(10);
            tableBox.setAlignment(Pos.CENTER);
            tableBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #b0bec5; -fx-border-radius: 15; -fx-background-radius: 15;");
            tableBox.setPadding(new Insets(20));
            tableBox.setPrefSize(150, 120);

            Label tableLabel = new Label("Table " + tableNum);
            tableLabel.setFont(Font.font("Arial", 18));
            tableLabel.setTextFill(Color.web("#37474f"));

            String currentStatus = tableStatusMap.get(tableNum);
            Button toggleButton = new Button(currentStatus);
            toggleButton.setPrefWidth(100);
            toggleButton.setStyle(getButtonStyle(currentStatus));

            toggleButton.setOnAction(e -> {
                String newStatus = toggleButton.getText().equals("Available") ? "Booked" : "Available";
                toggleButton.setText(newStatus);
                tableStatusMap.put(tableNum, newStatus);
                toggleButton.setStyle(getButtonStyle(newStatus));
            });

            tableBox.getChildren().addAll(tableLabel, toggleButton);
            tableGrid.add(tableBox, (i - 1) % 4, (i - 1) / 4);
        }

        Button backBtn = new Button("← Back to Dashboard");
        backBtn.setFont(Font.font("Arial", 16));
        backBtn.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white; -fx-background-radius: 20;");
        backBtn.setPadding(new Insets(10, 20, 10, 20));
        backBtn.setOnAction(e -> primaryStage.setScene(ReceptionDashboard.getView(primaryStage)));

        VBox bottomSection = new VBox(backBtn);
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setPadding(new Insets(30));

        root.setCenter(tableGrid);
        root.setBottom(bottomSection);

        return new Scene(root, 1550, 800);
    }

    private static void initializeTableStatus() {
        for (int i = 1; i <= 12; i++) {
            tableStatusMap.putIfAbsent(i, "Available");
        }
    }

    private static String getButtonStyle(String status) {
        if (status.equals("Available")) {
            return "-fx-background-color: #43a047; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px;";
        } else {
            return "-fx-background-color: #e53935; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px;";
        }
    }
    public static Pane getContent(Stage primaryStage) {
    initializeTableStatus();

    BorderPane root = new BorderPane();
    root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0f7fa, #b2ebf2);");

    Label title = new Label("📋 Table Control Dashboard");
    title.setFont(Font.font("Arial", 36));
    title.setTextFill(Color.web("#006064"));
    title.setPadding(new Insets(30, 0, 30, 0));

    VBox topSection = new VBox(title);
    topSection.setAlignment(Pos.CENTER);
    root.setTop(topSection);

    GridPane tableGrid = new GridPane();
    tableGrid.setHgap(40);
    tableGrid.setVgap(40);
    tableGrid.setPadding(new Insets(50));
    tableGrid.setAlignment(Pos.CENTER);

    for (int i = 1; i <= 12; i++) {
        int tableNum = i;

        VBox tableBox = new VBox(10);
        tableBox.setAlignment(Pos.CENTER);
        tableBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #b0bec5; -fx-border-radius: 15; -fx-background-radius: 15;");
        tableBox.setPadding(new Insets(20));
        tableBox.setPrefSize(150, 120);

        Label tableLabel = new Label("Table " + tableNum);
        tableLabel.setFont(Font.font("Arial", 18));
        tableLabel.setTextFill(Color.web("#37474f"));

        String currentStatus = tableStatusMap.get(tableNum);
        Button toggleButton = new Button(currentStatus);
        toggleButton.setPrefWidth(100);
        toggleButton.setStyle(getButtonStyle(currentStatus));

        toggleButton.setOnAction(e -> {
            String newStatus = toggleButton.getText().equals("Available") ? "Booked" : "Available";
            toggleButton.setText(newStatus);
            tableStatusMap.put(tableNum, newStatus);
            toggleButton.setStyle(getButtonStyle(newStatus));
        });

        tableBox.getChildren().addAll(tableLabel, toggleButton);
        tableGrid.add(tableBox, (i - 1) % 4, (i - 1) / 4);
    }

    root.setCenter(tableGrid);
    return root;
}

 
}

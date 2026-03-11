package com.hotel.view;


import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class TableLayoutPage {

    private static final String[] TABLES = {"A1", "A2", "A3", "B1", "B2", "B3"};

    public static void show(Stage stage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Live Table Layout");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        GridPane tableGrid = new GridPane();
        tableGrid.setHgap(30);
        tableGrid.setVgap(30);
        tableGrid.setAlignment(Pos.CENTER);

        Firestore db = FirestoreClient.getFirestore();

        for (int i = 0; i < TABLES.length; i++) {
            String tableId = TABLES[i];
            Button tableBtn = new Button(tableId);
            tableBtn.setPrefSize(150, 80);

            // Real-time status updates
            db.collection("tables").document(tableId).addSnapshotListener((doc, err) -> {
                if (doc != null && doc.exists()) {
                    String status = doc.getString("status");
                    Platform.runLater(() -> updateTableStyle(tableBtn, status));
                }
            });

            tableGrid.add(tableBtn, i % 3, i / 3);
        }

        Button backBtn = new Button("Back");
        backBtn.setPrefSize(200, 50);
        backBtn.setOnAction(e -> AdminPage.show(stage));

        root.getChildren().addAll(title, tableGrid, backBtn);
        Scene scene = new Scene(root, 1750, 980);
        stage.setScene(scene);
        stage.setTitle("Table Layout");
        stage.show();
    }
    

    private static void updateTableStyle(Button btn, String status) {
        btn.setUserData(status);
        switch (status) {
            case "free" -> btn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            case "occupied" -> btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            case "reserved" -> btn.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
            default -> btn.setStyle("-fx-background-color: gray;");
        }
    }
}

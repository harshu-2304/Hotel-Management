package com.hotel.view;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class DashboardPage {

    public static void show(Stage stage) {
        VBox root = new VBox(30);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Admin Dashboard - Waiting & Reservations");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        ListView<String> waitingList = new ListView<>();
        ListView<String> reservationList = new ListView<>();

        waitingList.setPrefHeight(300);
        reservationList.setPrefHeight(300);

        Firestore db = FirestoreClient.getFirestore();

        // Live waiting guests
        db.collection("waiting_guests").addSnapshotListener((snapshots, e) -> {
            if (snapshots != null) {
                List<String> guests = new ArrayList<>();
                for (DocumentSnapshot doc : snapshots.getDocuments()) {
                    String name = doc.getString("name");
                    guests.add(name != null ? name : "Unknown Guest");
                }
                Platform.runLater(() -> waitingList.getItems().setAll(guests));
            }
        });

        // Live reservations
        db.collection("reservations").addSnapshotListener((snapshots, e) -> {
            if (snapshots != null) {
                List<String> reservations = new ArrayList<>();
                for (DocumentSnapshot doc : snapshots.getDocuments()) {
                    String guest = doc.getString("guest");
                    String table = doc.getString("table");
                    String time = doc.getString("time");
                    reservations.add(guest + " - Table " + table + " at " + time);
                }
                Platform.runLater(() -> reservationList.getItems().setAll(reservations));
            }
        });

        Label waitingLabel = new Label("Waiting Guests:");
        waitingLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label reservationLabel = new Label("Reservations:");
        reservationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button backBtn = new Button("Back");
        backBtn.setPrefSize(200, 50);
        backBtn.setOnAction(e -> AdminPage.show(stage));

        root.getChildren().addAll(title, waitingLabel, waitingList, reservationLabel, reservationList, backBtn);

        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.setMaximized(true);
        stage.show();
    }
}
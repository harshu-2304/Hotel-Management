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

import java.util.*;

public class AdminSeatsAndPaymentsPage {

    private static final String[] SEATS = {"A1", "A2", "A3", "B1", "B2"};
    private static Stage primaryStage;
    private static Scene scene;

    public static void show(Stage stage) {
       
        primaryStage = stage;
        showSeatManagement();
    }

    private static void showSeatManagement() {
        Firestore db = FirestoreClient.getFirestore();

        VBox mainLayout = new VBox(30);
        mainLayout.setPadding(new Insets(50));
        mainLayout.setAlignment(Pos.CENTER);

        Label title = new Label("Manage Seats");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        GridPane seatGrid = new GridPane();
        seatGrid.setHgap(20);
        seatGrid.setVgap(20);
        seatGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < SEATS.length; i++) {
            String seatId = SEATS[i];
            Button seatBtn = new Button(seatId);
            seatBtn.setPrefSize(120, 60);

            db.collection("tables").document(seatId).addSnapshotListener((doc, error) -> {
                if (doc != null && doc.exists()) {
                    String status = doc.getString("status");
                    Platform.runLater(() -> updateSeatButtonStyle(seatBtn, status));
                }
            });

            seatBtn.setOnAction(e -> {
                String currentStatus = seatBtn.getUserData() != null ? seatBtn.getUserData().toString() : "free";
                String newStatus = switch (currentStatus) {
                    case "free" -> "occupied";
                    case "occupied" -> "reserved";
                    default -> "free";
                };
                Map<String, Object> data = new HashMap<>();
                data.put("status", newStatus);
                db.collection("tables").document(seatId).set(data);
            });

            seatGrid.add(seatBtn, i % 3, i / 3);
        }

        Button paymentBtn = new Button("Manage Payments");
        paymentBtn.setPrefSize(200, 50);
        paymentBtn.setOnAction(e -> showPaymentPage());

        mainLayout.getChildren().setAll(title, seatGrid, paymentBtn);
        scene = new Scene(mainLayout, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage Seats");
        primaryStage.show();
    }

    private static void showPaymentPage() {
        Firestore db = FirestoreClient.getFirestore();

        VBox root = new VBox(30);
        root.setPadding(new Insets(50));
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Manage Payments");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        ListView<HBox> paymentList = new ListView<>();

        db.collection("payments").addSnapshotListener((snapshots, error) -> {
            if (snapshots == null) return;

            List<HBox> items = new ArrayList<>();
            for (DocumentSnapshot doc : snapshots.getDocuments()) {
                String guest = doc.getString("guest");
                String table = doc.getString("table");
                Double amount = doc.getDouble("amount");
                String status = doc.getString("status");
                String id = doc.getId();

                Label info = new Label((guest != null ? guest : "Guest") + " | Table: " + table + " | ₹" + amount);
                info.setPrefWidth(600);

                Button statusBtn = new Button(status.equals("paid") ? "Paid" : "Mark as Paid");
                statusBtn.setStyle(status.equals("paid")
                        ? "-fx-background-color: green; -fx-text-fill: white;"
                        : "-fx-background-color: orange; -fx-text-fill: black;");
                statusBtn.setDisable(status.equals("paid"));

                statusBtn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mark as paid?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.YES) {
                            Map<String, Object> data = new HashMap<>();
                            data.put("status", "paid");
                            db.collection("payments").document(id).update(data);
                        }
                    });
                });

                HBox row = new HBox(30, info, statusBtn);
                row.setPadding(new Insets(10));
                row.setAlignment(Pos.CENTER_LEFT);
                items.add(row);
            }

            Platform.runLater(() -> {
                paymentList.getItems().setAll(items);
                paymentList.scrollTo(items.size() - 1);
            });
        });

        Button backBtn = new Button("Back");
        backBtn.setPrefSize(200, 50);
        backBtn.setOnAction(e -> showSeatManagement());

        root.getChildren().addAll(title, paymentList, backBtn);
        scene.setRoot(root);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Manage Payments");
    }

    private static void updateSeatButtonStyle(Button seatBtn, String status) {
        seatBtn.setUserData(status);
        seatBtn.setStyle(switch (status) {
            case "free" -> "-fx-background-color: green; -fx-text-fill: white;";
            case "occupied" -> "-fx-background-color: red; -fx-text-fill: white;";
            case "reserved" -> "-fx-background-color: orange; -fx-text-fill: white;";
            default -> "-fx-background-color: gray;";
        });
    }
}

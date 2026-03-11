package com.hotel.view;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.hotel.WelcomePage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class AdminPage {

    public static void show(Stage stage) {
        FirebaseIntializer.initialize();  

        VBox root = new VBox(30);
        root.setPadding(new Insets(100));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Welcome, Admin!");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        Button dashboardBtn = new Button("View Dashboard");
        dashboardBtn.setPrefSize(300, 60);
        dashboardBtn.setOnAction(e -> {
            logAdminAction("dashboard_opened");
            DashboardPage.show(stage);
        });

        Button tableLayoutBtn = new Button("View Table Layout");
        tableLayoutBtn.setPrefSize(300, 60);
        tableLayoutBtn.setOnAction(e -> {
            logAdminAction("table_layout_opened");
            TableLayoutPage.show(stage);
        });

        Button manageSeatsBtn = new Button("Manage Seats & Payments");
        manageSeatsBtn.setPrefSize(300, 60);
        manageSeatsBtn.setOnAction(e -> {
            logAdminAction("manage_seats_opened");
            AdminSeatsAndPaymentsPage.show(stage);
        });

        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefSize(300, 60);
        logoutBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Logout");
            alert.setHeaderText("Are you sure you want to logout?");
            alert.setContentText("Unsaved changes will be lost.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    logAdminAction("admin_logged_out");
                    WelcomePage welcomePage = new WelcomePage(stage);
                    welcomePage.show();
                }
            });
        });

        root.getChildren().addAll(title, dashboardBtn, tableLayoutBtn, manageSeatsBtn, logoutBtn);

        Scene scene = new Scene(root, 1920, 1080);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.setMaximized(true);
        stage.show();
    }

    private static void logAdminAction(String action) {
        Firestore db = FirestoreClient.getFirestore();
        Map<String, Object> log = new HashMap<>();
        log.put("action", action);
        log.put("timestamp", Instant.now().toString());

        try {
            db.collection("admin_logs").add(log).get();  
            System.out.println("Action logged: " + action);
        } catch (Exception e) {
            System.err.println("Log failed: " + e.getMessage());
        }
    }
}

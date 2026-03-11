package com.hotel.view;


import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FirestorageCRUD_Operations {

    Scene adminScene;
    Stage primaryStage;

    public void setScene(Scene adminScene) {
        this.adminScene = adminScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public VBox createAdminHome(String mail) {
        VBox vb = new VBox(15);
        vb.setStyle("-fx-padding: 40; -fx-alignment: top-left;");

        Text tx = new Text("Admin View: " + mail);
        vb.getChildren().add(tx);

        Text loadingText = new Text("Loading users...");
        vb.getChildren().add(loadingText);

        fetchDataAsync("users", users -> {
            vb.getChildren().remove(loadingText);
            for (QueryDocumentSnapshot doc : users) {
                String email = doc.contains("email") ? doc.getString("email") : "N/A";
                String password = doc.contains("password") ? doc.getString("password") : "N/A";

                Text userText = new Text("Email: " + email + ", Password: " + password);

                Button notifyBtn = new Button("Notify " + email);
                notifyBtn.setOnAction(e -> {
                    Map<String, Object> notification = new HashMap<>();
                    notification.put("title", "Welcome!");
                    notification.put("message", "Thanks for joining the app.");
                    notification.put("timestamp", System.currentTimeMillis());
                    notification.put("to", email);
                    FirestoreClient.getFirestore().collection("notifications").add(notification);
                });

                HBox userBox = new HBox(20, userText, notifyBtn);
                vb.getChildren().add(userBox);
            }
        });

        addFirestoreSection(vb, "dashboard", "\n - Dashboard Data -");
        addFirestoreSection(vb, "table_layout", "\n- Table Layout -");
        addFirestoreSection(vb, "admin_seats_payments", "\n - Admin Seat & Payment Logs -");

        return vb;
    }

    private void addFirestoreSection(VBox vb, String collection, String title) {
        Text sectionTitle = new Text(title);
        vb.getChildren().add(sectionTitle);

        fetchDataAsync(collection, docs -> {
            for (QueryDocumentSnapshot doc : docs) {
                vb.getChildren().add(new Text(doc.getData().toString()));
            }
        });
    }

    public void fetchDataAsync(String collection, Consumer<List<QueryDocumentSnapshot>> onComplete) {
        new Thread(() -> {
            try {
                QuerySnapshot snapshot = FirestoreClient.getFirestore().collection(collection).get().get();
                List<QueryDocumentSnapshot> docs = snapshot.getDocuments();
                Platform.runLater(() -> onComplete.accept(docs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
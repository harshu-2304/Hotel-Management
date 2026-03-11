package com.hotel.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderViewerPage {

    public static void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("📦 Incoming Orders");
        title.setFont(Font.font("Arial", 30));
        VBox topBox = new VBox(title);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(20, 0, 20, 0));
        root.setTop(topBox);

        TableView<Map<String, Object>> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Map<String, Object>, String> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(
                () -> String.valueOf(data.getValue().get("orderId"))));

        TableColumn<Map<String, Object>, String> itemsCol = new TableColumn<>("Items");
        itemsCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            Object itemsObj = data.getValue().get("items");
            if (itemsObj instanceof List<?>) {
                return String.join(", ", ((List<?>) itemsObj).stream().map(Object::toString).toList());
            }
            return "No items";
        }));

        TableColumn<Map<String, Object>, String> tableCol = new TableColumn<>("Table Number");
        tableCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            Object tableNum = data.getValue().get("tableNumber");
            return tableNum != null ? tableNum.toString() : "N/A";
        }));

        TableColumn<Map<String, Object>, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            Object status = data.getValue().get("status");
            return status != null ? status.toString() : "Pending";
        }));

        tableView.getColumns().addAll(orderIdCol, itemsCol, tableCol, statusCol);

        ObservableList<Map<String, Object>> dataList = FXCollections.observableArrayList();
        tableView.setItems(dataList);

        root.setCenter(tableView);

        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font(16));
        backBtn.setStyle("-fx-background-color: #006064; -fx-text-fill: white; -fx-background-radius: 20;");
        backBtn.setOnAction(e -> stage.setScene(ReceptionDashboard.getView(stage)));

        VBox bottomBox = new VBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        root.setBottom(bottomBox);

        Firestore db = FirestoreClient.getFirestore();
         ApiFuture<QuerySnapshot> future = db.collection("orders").get();
        //ApiFuture<QuerySnapshot> future = db.collection("order").orderBy("timestamp", Query.Direction.ASCENDING).get();


        future.addListener(() -> {
            try {
                QuerySnapshot snapshot = future.get();
                for (QueryDocumentSnapshot doc : snapshot.getDocuments()) {
                    Map<String, Object> data = new HashMap<>();

                    data.put("orderId", doc.getId());
                    data.put("items", doc.get("items"));
                    data.put("tableNumber", doc.get("tableNumber") != null ? doc.get("tableNumber") : "N/A");
                    data.put("status", doc.get("status") != null ? doc.get("status") : "Pending");

                    Platform.runLater(() -> dataList.add(data));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, Runnable::run);

        Scene scene = new Scene(root, 1500, 800);
        stage.setScene(scene);
        stage.setTitle("Order Viewer");
        stage.show();
    }
public static Pane getContent(Stage primaryStage) {
    VBox root = new VBox();
    root.setSpacing(20);
    root.setPadding(new Insets(30));
    root.setStyle("-fx-background-color: linear-gradient(to right, #dfe6e9, #b2bec3);");
    VBox.setVgrow(root, Priority.ALWAYS);

    Label title = new Label("📥 Incoming Orders");
    title.setFont(Font.font("Arial", 28));
    title.setTextFill(Color.DARKSLATEBLUE);

    TableView<Map<String, Object>> tableView = new TableView<>();
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    VBox.setVgrow(tableView, Priority.ALWAYS);  // ✅ Make table grow with VBox

    TableColumn<Map<String, Object>, String> orderIdCol = new TableColumn<>("Order ID");
    orderIdCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            String.valueOf(data.getValue().get("orderId"))
    ));

    TableColumn<Map<String, Object>, String> tableCol = new TableColumn<>("Table");
    tableCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            String.valueOf(data.getValue().get("tableNumber"))
    ));

    TableColumn<Map<String, Object>, String> itemsCol = new TableColumn<>("Items");
    itemsCol.setCellValueFactory(data -> {
        Object itemsObj = data.getValue().get("items");
        if (itemsObj instanceof Map<?, ?>) {
            StringBuilder sb = new StringBuilder();
            Map<?, ?> itemsMap = (Map<?, ?>) itemsObj;
            for (Map.Entry<?, ?> entry : itemsMap.entrySet()) {
                String itemName = String.valueOf(entry.getKey());
                Object value = entry.getValue();
                if (value instanceof Map<?, ?> valueMap) {
                    Object qty = valueMap.get("quantity");
                    Object unitPrice = valueMap.get("unitPrice");
                    Object totalPrice = valueMap.get("totalPrice");
                    sb.append(itemName)
                            .append(" (Qty: ").append(qty)
                            .append(", ₹").append(unitPrice)
                            .append(" x ").append(qty)
                            .append(" = ₹").append(totalPrice).append(")\n");
                } else {
                    sb.append(itemName).append(": ").append(value.toString()).append("\n");
                }
            }
            return new javafx.beans.property.SimpleStringProperty(sb.toString().trim());
        }
        return new javafx.beans.property.SimpleStringProperty("No items");
    });

    TableColumn<Map<String, Object>, String> statusCol = new TableColumn<>("Status");
    statusCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
            String.valueOf(data.getValue().getOrDefault("status", "Pending"))
    ));

    tableView.getColumns().addAll(orderIdCol, tableCol, itemsCol, statusCol);

    ObservableList<Map<String, Object>> orders = FXCollections.observableArrayList();
    tableView.setItems(orders);

    Firestore db = FirebaseIntializer.db;
    ApiFuture<QuerySnapshot> future = db.collection("order").get();
    future.addListener(() -> {
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                Map<String, Object> order = new HashMap<>();
                order.put("orderId", doc.getId());
                order.put("tableNumber", doc.get("tableNumber") != null ? doc.get("tableNumber") : "N/A");
                order.put("status", doc.get("status") != null ? doc.get("status") : "Pending");
                order.put("items", doc.get("items")); // The full map with qty/unit/total

                Platform.runLater(() -> orders.add(order));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }, Runnable::run);

    root.getChildren().addAll(title, tableView);
    VBox.setVgrow(tableView, Priority.ALWAYS);  // ✅ Allow tableView to grow
    return root;
}


}

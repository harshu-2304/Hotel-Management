package com.hotel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import com.hotel.view.FirebaseIntializer;

public class VegMenuPage {

    public static Scene getView(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fcfcfc;");

        Label title = new Label("🥬 Veg Food Menu");
        title.setFont(Font.font("Arial", 38));
        title.setStyle("-fx-text-fill: #2c3e50;");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(30, 0, 10, 0));

        HBox navBar = new HBox(15);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(0, 0, 20, 40));
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Search...");
        searchField.setPrefWidth(250);
        searchField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 8 15;");

        Button allBtn = new Button("All Items");
        Button vegBtn = new Button("🥬 Veg Menu");
        Button nonVegBtn = new Button("🍗 Non-Veg Menu");
        Button offersBtn = new Button("🔥 Offers");

        String buttonStyle = "-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 10 20;";
        allBtn.setStyle("-fx-background-color: linear-gradient(to right, #3498db, #6dd5fa);" + buttonStyle);
        vegBtn.setStyle("-fx-background-color: linear-gradient(to right, #2ecc71, #27ae60);" + buttonStyle);
        nonVegBtn.setStyle("-fx-background-color: linear-gradient(to right, #e67e22, #f39c12);" + buttonStyle);
        offersBtn.setStyle("-fx-background-color: linear-gradient(to right, #9b59b6, #8e44ad);" + buttonStyle);

        allBtn.setOnAction(e -> primaryStage.setScene(MenuPage.getView(primaryStage)));
        vegBtn.setOnAction(e -> primaryStage.setScene(VegMenuPage.getView(primaryStage)));
        nonVegBtn.setOnAction(e -> primaryStage.setScene(NonVegMenuPage.getView(primaryStage)));
        offersBtn.setOnAction(e -> primaryStage.setScene(OffersPage.getView(primaryStage)));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(allBtn, vegBtn, nonVegBtn, offersBtn, spacer, searchField);

        VBox topSection = new VBox(titleBox, navBar);
        root.setTop(topSection);

        FlowPane itemPane = new FlowPane();
        itemPane.setPadding(new Insets(20));
        itemPane.setHgap(30);
        itemPane.setVgap(40);
        itemPane.setAlignment(Pos.TOP_CENTER);

        itemPane.getChildren().addAll(
                MenuPage.createMenuItem("Veg_Kolhapuri", 180, "/Assets/Images/veg_kolhapuri.jpeg"),
                MenuPage.createMenuItem("Veg Pizza", 220, "/Assets/Images/veg pizza.jpg"),
                MenuPage.createMenuItem("Aloo Gobi", 150, "/Assets/Images/Aloo_gobi.jpg"),
                MenuPage.createMenuItem(" Paneer", 200, "/Assets/Images/paneer.jpg"),
                MenuPage.createMenuItem("Veg Pulao", 210, "/Assets/Images/veg_pullao.jpeg"),
                MenuPage.createMenuItem("Veg Biryani", 190, "/Assets/Images/Veg_biryani.jpg"),
                MenuPage.createMenuItem("Chana Masala", 170, "/Assets/Images/chana_masala.jpg"),
                MenuPage.createMenuItem("Misal Pav", 160, "/Assets/Images/misal_pav.jpeg"),
                MenuPage.createMenuItem("kaju kari", 140, "/Assets/Images/kaju_curry.jpg"),
                MenuPage.createMenuItem("Chana Masala", 140, "/Assets/Images/chole_bhature.jpeg")
        );

        ScrollPane scrollPane = new ScrollPane(itemPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPadding(new Insets(0, 40, 20, 40));

        HBox bottomButtons = new HBox(50);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(30, 0, 30, 0));

        /*Button orderBtn = new Button("🛒 Order Food");
        orderBtn.setFont(Font.font(18));
        orderBtn.setStyle("-fx-background-color: #1e88e5; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 10 30;");*/

        Button payBtn = new Button("💳 Proceed to Payment");
        payBtn.setFont(Font.font(18));
        payBtn.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d); -fx-text-fill: white; -fx-background-radius: 30; -fx-padding: 10 30;");
        // payBtn.setOnAction(e -> {
        //     Map<String, Double> selected = new HashMap<>(MenuPage.getSelectedItems());
        //     if (!selected.isEmpty()) {
        //         primaryStage.setScene(PaymentPage.getView(primaryStage, selected, "Veg"));
        //     } else {
        //         Alert alert = new Alert(Alert.AlertType.WARNING, "Please select items to proceed.");
        //         alert.showAndWait();
        //     }
        // });
        payBtn.setOnAction(e -> {
    Map<String, Double> selected = new HashMap<>(MenuPage.getSelectedItems());
    if (!selected.isEmpty()) {

        Map<String, Object> orderData = new HashMap<>();
        orderData.put("tableNumber", TableSelectionPage.selectedTableNumber != null ? TableSelectionPage.selectedTableNumber : "N/A");
        orderData.put("orderTime", System.currentTimeMillis());
        orderData.put("orderType", "Veg");

        Map<String, Object> items = new HashMap<>();
        for (Map.Entry<String, Double> entry : selected.entrySet()) {
            String itemName = entry.getKey();
            double totalPrice = entry.getValue();
            int quantity = MenuPage.itemQuantities.getOrDefault(itemName, 0);
            double unitPrice = quantity > 0 ? totalPrice / quantity : 0;

            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("quantity", quantity);
            itemDetails.put("unitPrice", unitPrice);
            itemDetails.put("totalPrice", totalPrice);

            items.put(itemName, itemDetails);
        }

        orderData.put("items", items);

        FirebaseIntializer.db.collection("order")
            .add(orderData)
            .addListener(() -> System.out.println("Veg order saved to Firebase."), Runnable::run);

        primaryStage.setScene(PaymentPage.getView(primaryStage, selected, "Veg"));

    } else {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please select items to proceed.");
        alert.showAndWait();
    }
});


        bottomButtons.getChildren().addAll( payBtn);

        VBox centerBox = new VBox(20, scrollPane, bottomButtons);
        centerBox.setAlignment(Pos.CENTER);

        root.setCenter(centerBox);
        primaryStage.setMaximized(true);

        return new Scene(root, 1550, 800);
    }
}

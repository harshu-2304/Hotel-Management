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

public class NonVegMenuPage {

    public static Scene getView(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fff3e0;");

        Label title = new Label("🍗 Non-Veg Food Menu");
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
        searchField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 8 25;");

        Button allBtn = new Button("All Items");
        Button vegBtn = new Button("Veg Menu");
        Button nonVegBtn = new Button("🍗 Non-Veg Menu");
        Button offersBtn = new Button("🔥 Offers");

        String btnStyle = "-fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 10 20;";
        allBtn.setStyle("-fx-background-color: linear-gradient(to right, #3498db, #6dd5fa);" + btnStyle);
        vegBtn.setStyle("-fx-background-color: linear-gradient(to right, #2ecc71, #27ae60);" + btnStyle);
        nonVegBtn.setStyle("-fx-background-color: linear-gradient(to right, #e67e22, #f39c12);" + btnStyle);
        offersBtn.setStyle("-fx-background-color: linear-gradient(to right, #9b59b6, #8e44ad);" + btnStyle);

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
                MenuPage.createMenuItem("Chicken Curry", 280, "/Assets/Images/Instant-Pot-Chicken-Curry-Recipe.jpg"),
                MenuPage.createMenuItem("Egg Masala", 150, "/Assets/Images/egg_masala.jpg"),
                MenuPage.createMenuItem("Fish Fry", 235, "/Assets/Images/fish fry.jpg"),
                MenuPage.createMenuItem("Butter Chicken", 320, "/Assets/Images/butter_chicken.jpg"),
                MenuPage.createMenuItem("Mutton Korma", 400, "/Assets/Images/mutton_korma.jpg"),
                MenuPage.createMenuItem("Mutton Ran", 500, "/Assets/Images/mutton_ran.jpeg"),
                MenuPage.createMenuItem("Non veg Pizza", 200, "/Assets/Images/non_veg_pizza.jpg"),
                MenuPage.createMenuItem("Chicken Ran", 340, "/Assets/Images/Chiken_Ran.jpeg"),
                MenuPage.createMenuItem("Chiken Biryani", 240, "/Assets/Images/Chicken_biryani2.jpeg"),
                MenuPage.createMenuItem("Mutton Biryani", 340, "/Assets/Images/mutton_biryani.jpg")
        );

        ScrollPane scrollPane = new ScrollPane(itemPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPadding(new Insets(0, 40, 20, 40));

        HBox bottomButtons = new HBox(50);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(30, 0, 30, 0));

        // Button orderBtn = new Button("🛒 Order Food");
        // orderBtn.setFont(Font.font(18));
        // orderBtn.setStyle("-fx-background-color: #1e88e5; -fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 10 30;");

        Button payBtn = new Button("💳 Proceed to Payment");
        payBtn.setFont(Font.font(18));
        payBtn.setStyle("-fx-background-color: linear-gradient(to right, #43cea2, #185a9d); -fx-text-fill: white; -fx-background-radius: 30; -fx-padding: 10 30;");
        // payBtn.setOnAction(e -> {
        //     Map<String, Double> selected = new HashMap<>(MenuPage.getSelectedItems());
        //     if (!selected.isEmpty()) {
        //         primaryStage.setScene(PaymentPage.getView(primaryStage, selected, "NonVeg"));
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
        orderData.put("orderType", "NonVeg");

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
            .addListener(() -> System.out.println("Non-Veg order saved to Firebase."), Runnable::run);

        primaryStage.setScene(PaymentPage.getView(primaryStage, selected, "NonVeg"));

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

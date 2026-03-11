package com.hotel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import com.google.cloud.firestore.FieldValue;
import com.hotel.view.FirebaseIntializer;

public class MenuPage {

    static Map<String, Double> selectedItems = new HashMap<>();
    static Map<String, Integer> itemQuantities = new HashMap<>();
    private static Button currentSelectedButton;

    public static Scene getView(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f9f9f9;");

        Label title = new Label("\uD83C\uDF7D️ All Food Items");
        title.setFont(Font.font("Arial", 38));
        title.setTextFill(Color.web("#2c3e50"));
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(40, 0, 10, 0));

        HBox navBar = new HBox(15);
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(0, 0, 20, 30));
        navBar.setStyle("-fx-border-color: #dcdcdc; -fx-border-width: 0 0 2 0;");

        TextField searchField = new TextField();
        searchField.setPromptText("\uD83D\uDD0D Search...");
        searchField.setPrefWidth(250);
        searchField.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-padding: 8 15;");

        Button allBtn = createStyledButton("All Items", "#3498db", "#6dd5fa");
        Button vegBtn = createStyledButton("\uD83E\uDD6C Veg Menu", "#2ecc71", "#27ae60");
        Button nonVegBtn = createStyledButton("\uD83C\uDF57 Non-Veg Menu", "#e67e22", "#f39c12");
        Button offersBtn = createStyledButton("\uD83D\uDD25 Offers", "#9b59b6", "#8e44ad");

        allBtn.setOnAction(e -> setSelectedButton(allBtn));
        vegBtn.setOnAction(e -> {
            setSelectedButton(vegBtn);
            primaryStage.setScene(VegMenuPage.getView(primaryStage));
        });
        nonVegBtn.setOnAction(e -> {
            setSelectedButton(nonVegBtn);
            primaryStage.setScene(NonVegMenuPage.getView(primaryStage));
        });
        offersBtn.setOnAction(e -> {
            setSelectedButton(offersBtn);
            primaryStage.setScene(OffersPage.getView(primaryStage));
        });

        setSelectedButton(allBtn);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(allBtn, vegBtn, nonVegBtn, offersBtn, spacer, searchField);

        FlowPane itemPane = new FlowPane();
        itemPane.setPadding(new Insets(8));
        itemPane.setHgap(20);
        itemPane.setVgap(20);
        itemPane.setAlignment(Pos.TOP_CENTER);

        itemPane.getChildren().addAll(
                createMenuItem("Veg Biryani", 180, "/Assets/Images/veg_biryani.jpg"),
                createMenuItem("Paneer Masala", 220, "/Assets/Images/paneer.jpg"),
                createMenuItem("Aloo Paratha", 90, "/Assets/Images/aaloo_paratha.jpg"),
                createMenuItem("Chicken Curry", 280, "/Assets/Images/Instant-Pot-Chicken-Curry-Recipe.jpg"),
                createMenuItem("Egg Masala", 150, "/Assets/Images/egg_masala.jpg"),
                createMenuItem("Fish Fry", 250, "/Assets/Images/fish_fry.jpg"),
                createMenuItem("Chole Bhature", 150, "/Assets/Images/chole_bhature.jpeg"),
                createMenuItem("Butter Chicken", 320, "/Assets/Images/butter_chicken.jpg"),
                createMenuItem("Chicken Ran", 350, "/Assets/Images/Chiken_Ran.jpeg"),
                createMenuItem("Veg_Kolhapuri", 180, "/Assets/Images/veg_kolhapuri.jpeg"),
                createMenuItem("Misal Pav", 120, "/Assets/Images/misal_pav.jpeg"),
                createMenuItem("Mutton Korma", 450, "/Assets/Images/mutton_korma.jpg"),
                createMenuItem("Veg Pizza", 150, "/Assets/Images/veg pizza.jpg"),
                createMenuItem("Mutton Ran", 440, "/Assets/Images/mutton_ran.jpeg"),
                createMenuItem("Aloo Gobi", 150, "/Assets/Images/Aloo_gobi.jpg"),
                createMenuItem("Non veg Pizza", 200, "/Assets/Images/non_veg_pizza.jpg"),
                createMenuItem("Veg Pulao", 120, "/Assets/Images/veg_pullao.jpeg"),
                createMenuItem("Chana Masala", 170, "/Assets/Images/chana_masala.jpg"),
                createMenuItem("Chiken Biryani", 240, "/Assets/Images/Chicken_biryani2.jpeg"),
                createMenuItem("kaju kari", 140, "/Assets/Images/kaju_curry.jpg"),
                createMenuItem("Mutton Biryani", 340, "/Assets/Images/mutton_biryani.jpg")
        );

        ScrollPane scrollPane = new ScrollPane(itemPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        Button payButton = new Button("Proceed to Payment");
        payButton.setFont(Font.font(18));
        payButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 10 30; -fx-background-radius: 10;");
        payButton.setOnMouseEntered(e -> payButton.setStyle("-fx-background-color: #1c6690; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;"));
        payButton.setOnMouseExited(e -> payButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;"));

        // ✅ Save all order items to Firebase only once at payment click
        payButton.setOnAction(e -> {
    if (selectedItems.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "No items selected for order.", ButtonType.OK);
        alert.setHeaderText("Empty Order");
        alert.showAndWait();
        return;
    }

    Map<String, Object> orderData = new HashMap<>();
    orderData.put("tableNumber", TableSelectionPage.selectedTableNumber != null ? TableSelectionPage.selectedTableNumber : "N/A");
    orderData.put("orderTime", System.currentTimeMillis());
    orderData.put("orderType", "All");
    orderData.put("timestamp", FieldValue.serverTimestamp());


    Map<String, Object> items = new HashMap<>();
    for (Map.Entry<String, Double> entry : selectedItems.entrySet()) {
        String itemName = entry.getKey();
        double totalPrice = entry.getValue();
        int quantity = itemQuantities.getOrDefault(itemName, 0);
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
            .addListener(() -> System.out.println("Order saved to Firebase."), Runnable::run);

    primaryStage.setScene(PaymentPage.getView(primaryStage, selectedItems, "Veg"));
});


        HBox payBox = new HBox(payButton);
        payBox.setAlignment(Pos.CENTER);
        payBox.setPadding(new Insets(15, 0, 25, 0));

        VBox topSection = new VBox(titleBox, navBar);
        VBox centerSection = new VBox(scrollPane);
        centerSection.setAlignment(Pos.TOP_CENTER);

        root.setTop(topSection);
        root.setCenter(centerSection);
        root.setBottom(payBox);
        primaryStage.setMaximized(true);
        return new Scene(root, 1550, 800);
    }

    private static Button createStyledButton(String text, String colorStart, String colorEnd) {
        Button button = new Button(text);
        button.setStyle(getButtonStyle(colorStart, colorEnd, false));
        button.setFont(Font.font("Arial", 14));
        button.setOnMouseEntered(e -> button.setStyle(getButtonStyle(colorStart, colorEnd, true)));
        button.setOnMouseExited(e -> button.setStyle(getButtonStyle(colorStart, colorEnd, false)));
        return button;
    }

    private static void setSelectedButton(Button button) {
        if (currentSelectedButton != null) {
            currentSelectedButton.setStyle(currentSelectedButton.getStyle().replace("-fx-opacity: 1.0;", "-fx-opacity: 0.7;"));
        }
        currentSelectedButton = button;
        currentSelectedButton.setStyle(currentSelectedButton.getStyle().replace("-fx-opacity: 0.7;", "-fx-opacity: 1.0;"));
    }

    private static String getButtonStyle(String colorStart, String colorEnd, boolean selected) {
        return "-fx-background-color: linear-gradient(to right, " + colorStart + ", " + colorEnd + ");" +
                "-fx-text-fill: white; -fx-background-radius: 20; -fx-padding: 10 10;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: " + (selected ? "1.0" : "0.7") + ";";
    }

    public static VBox createMenuItem(String name, double basePrice, String imagePath) {
        VBox itemBox = new VBox(10);
        itemBox.setAlignment(Pos.CENTER);
        itemBox.setPadding(new Insets(15));
        itemBox.setPrefWidth(220);
        itemBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 8, 0, 4, 4);");

        ImageView imageView = new ImageView();
        try {
            Image image = new Image(MenuPage.class.getResourceAsStream(imagePath));
            imageView.setImage(image);
            imageView.setFitWidth(180);
            imageView.setFitHeight(130);
        } catch (Exception e) {
            Label error = new Label("Image not found");
            error.setStyle("-fx-text-fill: red;");
            itemBox.getChildren().add(error);
        }

        double gstPrice = basePrice * 1.18;
        Label nameLabel = new Label(name + "\n\u20B9 " + String.format("%.2f", gstPrice));
        nameLabel.setFont(Font.font("Arial", 15));
        nameLabel.setWrapText(true);
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setStyle("-fx-text-fill: #2d3436;");

        HBox quantityBox = new HBox(10);
        quantityBox.setAlignment(Pos.CENTER);

        Button minusBtn = new Button("➖");
        Label quantityLabel = new Label("0");
        Button plusBtn = new Button("➕");

        quantityLabel.setFont(Font.font(14));
        minusBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        plusBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");

        quantityBox.getChildren().addAll(minusBtn, quantityLabel, plusBtn);

        Button orderButton = new Button("\uD83D\uDED2 Order");
        orderButton.setFont(Font.font(13));
        orderButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 5 15;");

        minusBtn.setOnAction(e -> {
            int currentQty = itemQuantities.getOrDefault(name, 0);
            if (currentQty > 0) {
                currentQty--;
                quantityLabel.setText(String.valueOf(currentQty));
                itemQuantities.put(name, currentQty);
                if (currentQty == 0) {
                    selectedItems.remove(name);
                } else {
                    selectedItems.put(name, gstPrice * currentQty);
                }
            }
        });

        plusBtn.setOnAction(e -> {
            int currentQty = itemQuantities.getOrDefault(name, 0);
            currentQty++;
            quantityLabel.setText(String.valueOf(currentQty));
            itemQuantities.put(name, currentQty);
        });

        orderButton.setOnAction(e -> {
            int qty = itemQuantities.getOrDefault(name, 0);
            if (qty > 0) {
                selectedItems.put(name, gstPrice * qty);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, name + " added to order (" + qty + "x)", ButtonType.OK);
                alert.setHeaderText("Item Ordered");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please increase quantity before ordering.", ButtonType.OK);
                alert.setHeaderText("No Quantity Selected");
                alert.showAndWait();
            }
        });

        itemBox.getChildren().addAll(imageView, nameLabel, quantityBox, orderButton);
        return itemBox;
    }

    public static Map<String, Double> getSelectedItems() {
        return selectedItems;
    }
}

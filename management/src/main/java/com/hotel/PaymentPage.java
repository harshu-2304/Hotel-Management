
// package com.hotel;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.stage.Stage;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.Map;

// public class PaymentPage {

//     public static Scene getView(Stage primaryStage, Map<String, Double> selectedItems, String sourcePage) {
//         // ===== Root layout with background image =====
//         StackPane root = new StackPane();

//         Image bgImage = new Image("/Assets/Images/reception.jpg"); // ✅ Update the path if necessary
//         ImageView bgView = new ImageView(bgImage);
//         bgView.setFitWidth(1750);
//         bgView.setFitHeight(980);
//         bgView.setPreserveRatio(false);

//         BorderPane layout = new BorderPane();
//         layout.setPadding(new Insets(40));

//         VBox mainContainer = new VBox(25);
//         mainContainer.setAlignment(Pos.TOP_CENTER);
//         mainContainer.setPadding(new Insets(40));
//         mainContainer.setMaxWidth(850);
//         mainContainer.setStyle("""
//                 -fx-background-color: rgba(255, 255, 255, 0.56);
//                 -fx-background-radius: 20;
//                 -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 10);
//                 """);

//         // ===== Header =====
//         Label header = new Label("💳 Payment Summary");
//         header.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
//         header.setTextFill(Color.web("#222"));

//         VBox itemList = new VBox(10);
//         itemList.setPadding(new Insets(10));
//         itemList.setStyle("-fx-background-color: #f8f8f8; -fx-background-radius: 12;");
//         itemList.setMaxWidth(750);

//         final double[] total = {0.0};
//         StringBuilder orderSummary = new StringBuilder();

//         for (Map.Entry<String, Double> entry : selectedItems.entrySet()) {
//             Label item = new Label("• " + entry.getKey() + " — ₹" + String.format("%.2f", entry.getValue()));
//             item.setFont(Font.font("Segoe UI", 16));
//             item.setTextFill(Color.web("#333"));
//             itemList.getChildren().add(item);
//             total[0] += entry.getValue();
//             orderSummary.append(entry.getKey()).append(", ");
//         }

//         Label totalLabel = new Label("Total: ₹" + String.format("%.2f", total[0]));
//         totalLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
//         totalLabel.setTextFill(Color.web("#4a148c"));

//         // ===== Payment Method =====
//         VBox paymentBox = new VBox(20);
//         paymentBox.setAlignment(Pos.CENTER_LEFT);

//         Label paymentHeader = new Label("Select Payment Method");
//         paymentHeader.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 20));
//         paymentHeader.setTextFill(Color.web("#111"));

//         ToggleGroup group = new ToggleGroup();

//         HBox optionsRow = new HBox(20);
//         optionsRow.setAlignment(Pos.CENTER_LEFT);
//         optionsRow.getChildren().addAll(
//                 createStyledRadio("Credit/Debit Card", group, true),
//                 createStyledRadio("Cash", group, false),
//                 createStyledRadio("Apple Pay", group, false),
//                 createStyledRadio("Google Pay", group, false)
//         );

//         paymentBox.getChildren().addAll(paymentHeader, optionsRow);

//         // ===== Buttons =====
//         HBox buttonBox = new HBox(20);
//         buttonBox.setAlignment(Pos.CENTER);
//         buttonBox.setPadding(new Insets(20, 0, 0, 0));

//         Button backBtn = new Button("← Back to Menu");
//         backBtn.setStyle("""
//                 -fx-background-color: #d32f2f;
//                 -fx-text-fill: white;
//                 -fx-font-size: 14px;
//                 -fx-padding: 10 20;
//                 -fx-background-radius: 8;
//                 """);
//         backBtn.setOnAction(e -> {
//             switch (sourcePage) {
//                 case "Veg" -> primaryStage.setScene(VegMenuPage.getView(primaryStage));
//                 case "NonVeg" -> primaryStage.setScene(NonVegMenuPage.getView(primaryStage));
//                 default -> primaryStage.setScene(MenuPage.getView(primaryStage));
//             }
//         });

//         Button payBtn = new Button("Pay ₹" + String.format("%.2f", total[0]));
//         payBtn.setStyle("""
//                 -fx-background-color: linear-gradient(to right, #7b1fa2, #512da8);
//                 -fx-text-fill: white;
//                 -fx-font-size: 16px;
//                 -fx-padding: 12 30;
//                 -fx-background-radius: 10;
//                 """);
//         payBtn.setOnAction(e -> {
//             String method = group.getSelectedToggle() != null ? ((RadioButton) group.getSelectedToggle()).getText() : "Unknown";
//             Alert alert = new Alert(Alert.AlertType.INFORMATION);
//             alert.setTitle("Payment");
//             alert.setHeaderText(null);
//             alert.setContentText("Payment Successful via " + method);
//             alert.showAndWait();

//             // ====== STORE PAYMENT HISTORY =======
//             String orderItems = orderSummary.toString().replaceAll(", $", "");
//             String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//             String paymentRecord = "Order: " + orderItems + " | Amount: ₹" + String.format("%.2f", total[0]) + " | Method: " + method + " | Date: " + date;
//             Payment.addPayment(paymentRecord);
//             // =====================================

//             primaryStage.setScene(ThankyouPage.getView(primaryStage));
//         });

//         buttonBox.getChildren().addAll(backBtn, payBtn);

//         // ===== Assemble =====
//         mainContainer.getChildren().addAll(header, itemList, totalLabel, paymentBox, buttonBox);
//         layout.setCenter(mainContainer);

//         root.getChildren().addAll(bgView, layout);
//         primaryStage.setMaximized(true);
//         return new Scene(root, 1550, 800);
//     }

//     private static RadioButton createStyledRadio(String label, ToggleGroup group, boolean selected) {
//         RadioButton rb = new RadioButton(label);
//         rb.setToggleGroup(group);
//         rb.setSelected(selected);
//         rb.setFont(Font.font("Segoe UI", 15));
//         rb.setStyle("""
//                 -fx-background-color: #eeeeee;
//                 -fx-background-radius: 12;
//                 -fx-padding: 8 16;
//                 -fx-border-color: #ccc;
//                 -fx-border-radius: 12;
//                 """);
//         return rb;
//     }
// }
package com.hotel;

import com.hotel.view.BillGenerationPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PaymentPage {

    public static Scene getView(Stage primaryStage, Map<String, Double> selectedItems, String sourcePage) {
        StackPane root = new StackPane();

        Image bgImage = new Image("/Assets/Images/reception.jpg");
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(1750);
        bgView.setFitHeight(980);
        bgView.setPreserveRatio(false);

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(40));

        VBox mainContainer = new VBox(25);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setPadding(new Insets(40));
        mainContainer.setMaxWidth(850);
        mainContainer.setStyle("""
                -fx-background-color: rgba(255, 255, 255, 0.56);
                -fx-background-radius: 20;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 10);
                """);

        Label header = new Label("💳 Payment Summary");
        header.setFont(Font.font("Segoe UI", FontWeight.BOLD, 30));
        header.setTextFill(Color.web("#222"));

        VBox itemList = new VBox(10);
        itemList.setPadding(new Insets(10));
        itemList.setStyle("-fx-background-color: #f8f8f8; -fx-background-radius: 12;");
        itemList.setMaxWidth(750);

        final double[] total = {0.0};
        StringBuilder orderSummary = new StringBuilder();

        for (Map.Entry<String, Double> entry : selectedItems.entrySet()) {
            Label item = new Label("• " + entry.getKey() + " — ₹" + String.format("%.2f", entry.getValue()));
            item.setFont(Font.font("Segoe UI", 16));
            item.setTextFill(Color.web("#333"));
            itemList.getChildren().add(item);
            total[0] += entry.getValue();
            orderSummary.append(entry.getKey()).append(", ");
        }

        Label totalLabel = new Label("Total: ₹" + String.format("%.2f", total[0]));
        totalLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        totalLabel.setTextFill(Color.web("#4a148c"));

        VBox paymentBox = new VBox(20);
        paymentBox.setAlignment(Pos.CENTER_LEFT);

        Label paymentHeader = new Label("Select Payment Method");
        paymentHeader.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 20));
        paymentHeader.setTextFill(Color.web("#111"));

        ToggleGroup group = new ToggleGroup();

        HBox optionsRow = new HBox(20);
        optionsRow.setAlignment(Pos.CENTER_LEFT);
        optionsRow.getChildren().addAll(
                createStyledRadio("Credit/Debit Card", group, true),
                createStyledRadio("Cash", group, false),
                createStyledRadio("Apple Pay", group, false),
                createStyledRadio("Google Pay", group, false)
        );

        paymentBox.getChildren().addAll(paymentHeader, optionsRow);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        Button backBtn = new Button("← Back to Menu");
        backBtn.setStyle("""
                -fx-background-color: #d32f2f;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-padding: 10 20;
                -fx-background-radius: 8;
                """);

        backBtn.setOnAction(e -> {
            switch (sourcePage) {
                case "Veg" -> primaryStage.setScene(VegMenuPage.getView(primaryStage));
                case "NonVeg" -> primaryStage.setScene(NonVegMenuPage.getView(primaryStage));
                default -> primaryStage.setScene(MenuPage.getView(primaryStage));
            }
        });

        Button payBtn = new Button("Pay ₹" + String.format("%.2f", total[0]));
        payBtn.setStyle("""
                -fx-background-color: linear-gradient(to right, #7b1fa2, #512da8);
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-padding: 12 30;
                -fx-background-radius: 10;
                """);

        payBtn.setOnAction(e -> {
            String method = group.getSelectedToggle() != null ? ((RadioButton) group.getSelectedToggle()).getText() : "Unknown";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Payment");
            alert.setHeaderText(null);
            alert.setContentText("Payment Successful via " + method);
            alert.showAndWait();

            String orderItems = orderSummary.toString().replaceAll(", $", "");
            String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String paymentRecord = "Order: " + orderItems + " | Amount: ₹" + String.format("%.2f", total[0]) + " | Method: " + method + " | Date: " + date;

            // Store in Payment History
            Payment.addPayment(paymentRecord);


            // Send to BillGenerationPage
            // Send to BillGenerationPage
String userEmail = UserLoginSignupPage.getCurrentUserEmail();
BillGenerationPage.setBillData(userEmail, orderItems, total[0], method, date);


            // Navigate to Thank You
            primaryStage.setScene(ThankyouPage.getView(primaryStage));
        });

        buttonBox.getChildren().addAll(backBtn, payBtn);

        mainContainer.getChildren().addAll(header, itemList, totalLabel, paymentBox, buttonBox);
        layout.setCenter(mainContainer);

        root.getChildren().addAll(bgView, layout);
        primaryStage.setMaximized(true);
        return new Scene(root, 1550, 800);
    }

    private static RadioButton createStyledRadio(String label, ToggleGroup group, boolean selected) {
        RadioButton rb = new RadioButton(label);
        rb.setToggleGroup(group);
        rb.setSelected(selected);
        rb.setFont(Font.font("Segoe UI", 15));
        rb.setStyle("""
                -fx-background-color: #eeeeee;
                -fx-background-radius: 12;
                -fx-padding: 8 16;
                -fx-border-color: #ccc;
                -fx-border-radius: 12;
                """);
        return rb;
    }
}


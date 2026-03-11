// package com.hotel.view;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.scene.effect.DropShadow;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.stage.Stage;

// public class OrderHistoryPage {

//     public static Scene getView(Stage primaryStage) {
//         BorderPane root = new BorderPane();
//         root.setStyle("-fx-background-color: #f0f4f7;");

//         // ===== Top Header =====
//         HBox header = new HBox();
//         header.setPadding(new Insets(25));
//         header.setAlignment(Pos.CENTER);
//         header.setStyle("-fx-background-color: #6c5ce7;");

//         Label title = new Label("📜 Order History");
//         title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
//         title.setTextFill(Color.WHITE);

//         header.getChildren().add(title);
//         root.setTop(header);

//         // ===== Center Body =====
//         VBox centerBox = new VBox(30);
//         centerBox.setAlignment(Pos.CENTER);
//         centerBox.setPadding(new Insets(50));

//         VBox cardBox = new VBox(20);
//         cardBox.setPadding(new Insets(30));
//         cardBox.setAlignment(Pos.TOP_CENTER);
//         cardBox.setPrefWidth(700);
//         cardBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-radius: 15px;");
//         cardBox.setEffect(new DropShadow(10, Color.gray(0.4)));

//         Label listTitle = new Label("Recent Orders");
//         listTitle.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 22));
//         listTitle.setTextFill(Color.web("#2d3436"));

//         ListView<String> historyList = new ListView<>();
//         historyList.getItems().addAll("Order 1 - Completed", "Order 2 - Cancelled", "Order 3 - Completed");
//         historyList.setPrefSize(600, 300);
//         historyList.setStyle("-fx-font-size: 16px; -fx-background-radius: 8px;");

//         Button backBtn = new Button("← Back to Dashboard");
//         backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
//         backBtn.setStyle("-fx-background-color: #d63031; -fx-text-fill: white; -fx-background-radius: 8px;");
//         backBtn.setPrefWidth(220);
//         backBtn.setOnAction(e -> primaryStage.setScene(ReceptionDashboard.getView(primaryStage)));

//         cardBox.getChildren().addAll(listTitle, historyList, backBtn);
//         centerBox.getChildren().add(cardBox);
//         root.setCenter(centerBox);
//         primaryStage.setMaximized(true);

//         return new Scene(root, 1550, 800);
//     }
//  public static Pane getContent(Stage primaryStage) {
//     BorderPane root = new BorderPane();
//     root.setStyle("-fx-background-color: white; -fx-padding: 20;");
    
//     Label title = new Label("📜 Order History");
//     title.setFont(Font.font("Arial", FontWeight.BOLD, 26));
//     root.setTop(title);
//     BorderPane.setAlignment(title, Pos.CENTER);

//     // Example content placeholder
//     VBox historyBox = new VBox(15);
//     historyBox.setAlignment(Pos.CENTER_LEFT);
//     historyBox.setPadding(new Insets(20));

//     // Sample history
//     historyBox.getChildren().add(new Label("🕒 Table 3 - 2x Biryani - ₹400 - Paid"));
//     historyBox.getChildren().add(new Label("🕒 Table 1 - 1x Pizza - ₹250 - Paid"));

//     root.setCenter(historyBox);
//     return root;
// }

// }

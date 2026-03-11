package com.hotel;

import com.hotel.view.TableControlPage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;

public class TableSelectionPage {

    private final Stage primaryStage;
    public static String selectedTableNumber = null;


    public TableSelectionPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene getView() {
        VBox mainRoot = new VBox(20);
        mainRoot.setPadding(new Insets(25));
        mainRoot.setStyle("-fx-background-color: linear-gradient(to bottom, #f0f4f8, #d9e2ec);");
        mainRoot.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Select Your Table");
        title.setFont(Font.font("Verdana", 42));
        title.setTextFill(Color.DARKSLATEBLUE);

        Image normalTableImage = loadImage("/Assets/Images/ta.jpg");
        Image vipTableImage = loadImage("/Assets/Images/table image.jpg");

        HBox splitContainer = new HBox(40);
        splitContainer.setAlignment(Pos.TOP_CENTER);
        splitContainer.setPadding(new Insets(10));

        // Normal Table Grid
        GridPane normalTableGrid = createTableGrid(normalTableImage, 1, 6, "Table ");

        VBox normalSection = new VBox(20);
        normalSection.setAlignment(Pos.TOP_CENTER);
        normalSection.setPrefWidth(850);
        Label normalLabel = new Label("Normal Tables");
        normalLabel.setFont(Font.font("Arial", 28));
        normalLabel.setTextFill(Color.DARKGREEN);
        normalSection.getChildren().addAll(normalLabel, normalTableGrid);

        // VIP Table Grid
        GridPane vipTableGrid = createTableGrid(vipTableImage, 7, 12, "VIP Table ");

        VBox vipSection = new VBox(20);
        vipSection.setAlignment(Pos.TOP_CENTER);
        vipSection.setPrefWidth(850);
        Label vipLabel = new Label("VIP Tables");
        vipLabel.setFont(Font.font("Arial", 28));
        vipLabel.setTextFill(Color.DARKRED);
        vipSection.getChildren().addAll(vipLabel, vipTableGrid);

        splitContainer.getChildren().addAll(normalSection, vipSection);

        ScrollPane scrollPane = new ScrollPane(splitContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Button backButton = new Button("Back to Welcome");
        backButton.setFont(Font.font("Arial", 18));
        backButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white; -fx-background-radius: 10;");
        backButton.setOnAction(e -> {
            WelcomePage welcomePage = new WelcomePage(primaryStage);
            welcomePage.show();
            
        });

        mainRoot.getChildren().addAll(title, scrollPane, backButton);
        primaryStage.setMaximized(true);
        return new Scene(mainRoot, 1550, 800);
    }

    private GridPane createTableGrid(Image image, int start, int end, String prefix) {
        GridPane grid = new GridPane();
        grid.setHgap(40);
        grid.setVgap(40);
        grid.setAlignment(Pos.TOP_CENTER);

        for (int i = start; i <= end; i++) {
            String status = TableControlPage.tableStatusMap.getOrDefault(i, "Available");
            VBox tableBox = createTableBox(prefix + (i - start + 1), status, image);
            int col = (i - start) % 3;
            int row = (i - start) / 3;
           if (status.equals("Available")) {
              String tableLabel = prefix + (i - start + 1);
              tableBox.setOnMouseClicked(e -> {
              selectedTableNumber = tableLabel; // ✅ Store selected table
             goToMenuPage();
           });
        }

            grid.add(tableBox, col, row);
        }
        return grid;
    }

    private VBox createTableBox(String tableNumber, String status, Image image) {
        Label label = new Label(tableNumber);
        label.setFont(Font.font("Arial", 20));
        label.setTextFill(Color.DARKBLUE);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(178);
        imageView.setFitHeight(132);
        imageView.setPreserveRatio(false);

        Label statusLabel = new Label(status);
        statusLabel.setFont(Font.font("Arial", 18));
        statusLabel.setTextFill(status.equals("Booked") ? Color.RED : Color.GREEN);

        VBox box = new VBox(10, label, imageView, statusLabel);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #B0C4DE;" +
                        "-fx-border-radius: 12;" +
                        "-fx-background-radius: 12;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0.1, 2, 2);"
        );

        if (status.equals("Available")) {
            box.setOnMouseEntered(e -> {
                box.setStyle(
                        "-fx-background-color: #f0f8ff;" +
                                "-fx-border-color: #4682B4;" +
                                "-fx-border-radius: 12;" +
                                "-fx-background-radius: 12;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(30,144,255,0.4), 10, 0, 0, 5);"
                );
                box.setCursor(javafx.scene.Cursor.HAND);
            });
            box.setOnMouseExited(e -> {
                box.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-border-color: #B0C4DE;" +
                                "-fx-border-radius: 12;" +
                                "-fx-background-radius: 12;" +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 6, 0.1, 2, 2);"
                );
            });
        }

        return box;
    }

    private void goToMenuPage() {
        // UserLoginSignupPage loginPage = new UserLoginSignupPage();
        MenuPage menuPage =new MenuPage();
        Scene menuScene = menuPage.getView(primaryStage);
        primaryStage.setScene(menuScene);
        primaryStage.setMaximized(true);
    }

    private Image loadImage(String path) {
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.out.println("Image not found: " + path);
            return new Image("https://via.placeholder.com/198x132.png?text=Missing+Image");
        }
        return new Image(imageUrl.toExternalForm());
    }
        public static Scene getView(Stage primaryStage) {
    return new TableSelectionPage(primaryStage).getView();
}


}

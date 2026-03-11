package com.hotel.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class BillGenerationPage {

    private static String customerEmail = "";
    private static String orderItems = "";
    private static double totalAmount = 0.0;
    private static String paymentMethod = "";
    private static String orderDate = "";

    // Set data from PaymentPage before calling getView()
    public static void setBillData(String email, String items, double amount, String method, String date) {
        customerEmail = email != null ? email : "N/A";
        orderItems = items != null ? items : "N/A";
        totalAmount = amount;
        paymentMethod = method != null ? method : "N/A";
        orderDate = date != null ? date : "N/A";
    }

    public static Scene getView(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fdfefe;");

        // Header
        HBox header = new HBox();
        header.setPadding(new Insets(25));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #00b894;");
        Label title = new Label("🧾 Bill Generation");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);
        header.getChildren().add(title);
        root.setTop(header);

        // Main Bill Card
        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(50));

        VBox billCard = new VBox(20);
        billCard.setAlignment(Pos.CENTER);
        billCard.setPadding(new Insets(30));
        billCard.setPrefWidth(800);
        billCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-radius: 15px;");
        billCard.setEffect(new DropShadow(10, Color.gray(0.5)));

        // Compute GST & Net Total
        double gst = totalAmount * 0.12;
        double net = totalAmount + gst;

        // Bill Text Area
        TextArea billArea = new TextArea();
        billArea.setPrefSize(700, 350);
        billArea.setFont(Font.font("Consolas", 18));
        billArea.setWrapText(true);
        billArea.setEditable(false);
        billArea.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");
        billArea.setText(
                "Customer Email: " + customerEmail + "\n" +
                "Order: " + orderItems + "\n" +
                "Total: ₹" + String.format("%.2f", totalAmount) + "\n" +
                "Payment Method: " + paymentMethod + "\n" +
                "Date: " + orderDate + "\n" +
                "GST (12%): ₹" + String.format("%.2f", gst) + "\n" +
                "Net Payable: ₹" + String.format("%.2f", net)
        );

        // Buttons
        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.CENTER);

        Button generateBtn = new Button("🖨 Print / Generate PDF");
        generateBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        generateBtn.setPrefWidth(220);
        generateBtn.setStyle("-fx-background-color: #0984e3; -fx-text-fill: white; -fx-background-radius: 8px;");

        generateBtn.setOnAction(e -> {
            try {
                generatePDF(customerEmail, orderItems, totalAmount, gst, net, paymentMethod, orderDate);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "PDF Generated Successfully!", ButtonType.OK);
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "PDF Generation Failed!", ButtonType.OK);
                alert.showAndWait();
            }
        });

        Button backBtn = new Button("← Back");
        backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        backBtn.setPrefWidth(150);
        backBtn.setStyle("-fx-background-color: #d63031; -fx-text-fill: white; -fx-background-radius: 8px;");
        backBtn.setOnAction(e -> primaryStage.setScene(ReceptionDashboard.getView(primaryStage)));

        buttonBox.getChildren().addAll(generateBtn, backBtn);
        billCard.getChildren().addAll(billArea, buttonBox);
        centerBox.getChildren().add(billCard);

        root.setCenter(centerBox);
        primaryStage.setMaximized(true);
        return new Scene(root, 1550, 800);
    }

    public static void generatePDF(String email, String items, double total, double gst, double net, String method, String date) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("Generated_Bill.pdf"));
        document.open();

        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
        com.itextpdf.text.Font bodyFont = FontFactory.getFont(FontFactory.COURIER, 14);

        document.add(new Paragraph("🧾 SMART DIEN BILL RECEIPT", titleFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Customer Email: " + email, bodyFont));
        document.add(new Paragraph("Order: " + items, bodyFont));
        document.add(new Paragraph("Total: ₹" + String.format("%.2f", total), bodyFont));
        document.add(new Paragraph("Payment Method: " + method, bodyFont));
        document.add(new Paragraph("Date: " + date, bodyFont));
        document.add(new Paragraph("GST (12%): ₹" + String.format("%.2f", gst), bodyFont));
        document.add(new Paragraph("Net Payable: ₹" + String.format("%.2f", net), bodyFont));
        document.close();
    }

    public static Pane getContent(Stage primaryStage) {
    VBox container = new VBox(30);
    container.setAlignment(Pos.CENTER);
    container.setPadding(new Insets(50));

    VBox billCard = new VBox(20);
    billCard.setAlignment(Pos.CENTER);
    billCard.setPadding(new Insets(30));
    billCard.setPrefWidth(800);
    billCard.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-border-radius: 15px;");
    billCard.setEffect(new DropShadow(10, Color.gray(0.5)));

    double gst = totalAmount * 0.12;
    double net = totalAmount + gst;

    TextArea billArea = new TextArea(
            "Customer Email: " + customerEmail + "\n" +
            "Order: " + orderItems + "\n" +
            "Total: ₹" + String.format("%.2f", totalAmount) + "\n" +
            "Payment Method: " + paymentMethod + "\n" +
            "Date: " + orderDate + "\n" +
            "GST (12%): ₹" + String.format("%.2f", gst) + "\n" +
            "Net Payable: ₹" + String.format("%.2f", net)
    );
    billArea.setPrefSize(700, 350);
    billArea.setFont(Font.font("Consolas", 18));
    billArea.setWrapText(true);
    billArea.setEditable(false);
    billArea.setStyle("-fx-background-radius: 10px; -fx-border-radius: 10px;");

    HBox buttonBox = new HBox(30);
    buttonBox.setAlignment(Pos.CENTER);

    Button generateBtn = new Button("🖨 Print / Generate PDF");
    generateBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    generateBtn.setPrefWidth(220);
    generateBtn.setStyle("-fx-background-color: #0984e3; -fx-text-fill: white; -fx-background-radius: 8px;");
    generateBtn.setOnAction(e -> {
        try {
            generatePDF(customerEmail, orderItems, totalAmount, gst, net, paymentMethod, orderDate);
            new Alert(Alert.AlertType.INFORMATION, "PDF Generated Successfully!").showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "PDF Generation Failed!").showAndWait();
        }
    });

    Button backBtn = new Button("← Back");
    backBtn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
    backBtn.setPrefWidth(150);
    backBtn.setStyle("-fx-background-color: #d63031; -fx-text-fill: white; -fx-background-radius: 8px;");
    backBtn.setOnAction(e -> primaryStage.setScene(ReceptionDashboard.getView(primaryStage)));

    buttonBox.getChildren().addAll(generateBtn, backBtn);
    billCard.getChildren().addAll(billArea, buttonBox);
    container.getChildren().add(billCard);

    return container;
}

}

package com.hotel;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.*;

public class Payment {
    private final StringProperty type;
    private final DoubleProperty amount;
    private final StringProperty date;

    public Payment(String type, double amount, String date) {
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleDoubleProperty(amount);
        this.date = new SimpleStringProperty(date);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public StringProperty dateProperty() {
        return date;
    }
    private static final List<String> paymentHistory = new ArrayList<>();

    public static void addPayment(String paymentDetails) {
        paymentHistory.add(paymentDetails);
    }
    

    public static List<String> getAllPayments() {
        return new ArrayList<>(paymentHistory);
    }

    public static void clear() {
        paymentHistory.clear();
    }
}


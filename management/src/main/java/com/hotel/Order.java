package com.hotel;

import javafx.beans.property.*;

public class Order {
    private final StringProperty item;
    private final IntegerProperty quantity;
    private final StringProperty date;

    public Order(String item, int quantity, String date) {
        this.item = new SimpleStringProperty(item);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.date = new SimpleStringProperty(date);
    }

    public StringProperty itemProperty() {
        return item;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public StringProperty dateProperty() {
        return date;
    }
    
}

package com.shop.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
    private int orderId;
    private String userId;
    private Date orderDate;
    private Map<Item, Integer> items;
    private String status;
    private double totalAmount;
    
    public Order(int orderId, String userId, Map<Item, Integer> items) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = new Date();
        this.items = new HashMap<>(items);
        this.status = "PENDING";
        calculateTotal();
    }
    
    private void calculateTotal() {
        this.totalAmount = 0;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            this.totalAmount += entry.getKey().getPrice() * entry.getValue();
        }
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public Date getOrderDate() {
        return orderDate;
    }
    
    public Map<Item, Integer> getItems() {
        return new HashMap<>(items);
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public int getTotalItems() {
        int count = 0;
        for (int quantity : items.values()) {
            count += quantity;
        }
        return count;
    }
} 
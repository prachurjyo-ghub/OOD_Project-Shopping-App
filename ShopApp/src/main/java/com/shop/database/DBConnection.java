package com.shop.database;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.shop.model.Item;
import com.shop.model.User;


public class DBConnection {
    private static Map<String, User> users = new HashMap<>();
    private static Map<Integer, Item> items = new HashMap<>();
    private static boolean initialized = false;
    
    static {
        initializeData();
    }
    
    public static Connection getConnection() {
        return null; // No actual connection needed
    }
    
    private static void initializeData() {
        if (!initialized) {
            try {
                // Add admin user
                User admin = new User("Admin", "admin", "1122", true);
                users.put(admin.getPhoneNumber(), admin);
                
                // Add sample grocery items
                addItem(new Item(1, "Rice", 56, 100, "Grocery"));
                addItem(new Item(2, "Milk", 45, 50, "Grocery"));
                addItem(new Item(3, "Bread", 30, 30, "Grocery"));
                addItem(new Item(4, "Eggs", 10, 40, "Grocery"));
                addItem(new Item(5, "Cereal", 300, 25, "Grocery"));
                
                // Add sample clothes items
                addItem(new Item(6, "T-Shirt", 350, 20, "Clothes"));
                addItem(new Item(7, "Jeans", 1200, 15, "Clothes"));
                addItem(new Item(8, "Dress", 2500, 10, "Clothes"));
                addItem(new Item(9, "Socks", 50, 30, "Clothes"));
                addItem(new Item(10, "Hat", 50, 15, "Clothes"));
                
                // Add sample electronics items
                addItem(new Item(11, "Smartphone", 25000, 10, "Electronics"));
                addItem(new Item(12, "Headphones", 700, 20, "Electronics"));
                addItem(new Item(13, "Laptop", 70000, 5, "Electronics"));
                addItem(new Item(14, "Mouse", 500, 15, "Electronics"));
                addItem(new Item(15, "Keyboard", 1000, 10, "Electronics"));
                
                // Add sample beauty products
                addItem(new Item(16, "Shampoo", 300, 25, "Beauty"));
                addItem(new Item(17, "Soap", 40, 40, "Beauty"));
                addItem(new Item(18, "Lotion", 500, 20, "Beauty"));
                addItem(new Item(19, "Makeup Kit", 10000, 10, "Beauty"));
                addItem(new Item(20, "Hair Gel", 300, 15, "Beauty"));
                
                // Add sample stationery items
                addItem(new Item(21, "Notebook", 50, 30, "Stationery"));
                addItem(new Item(22, "Pen Set", 60, 25, "Stationery"));
                addItem(new Item(23, "Pencil Box", 100, 20, "Stationery"));
                addItem(new Item(24, "Eraser", 20, 50, "Stationery"));
                addItem(new Item(25, "Ruler", 30, 40, "Stationery"));
                
                initialized = true;
                System.out.println("Database initialized with sample data");
            } catch (Exception e) {
                System.err.println("Error initializing database: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private static void addItem(Item item) {
        if (item != null) {
            items.put(item.getId(), item);
        }
    }
    
    // Access to our in-memory data
    public static Map<String, User> getUsers() {
        if (!initialized) {
            initializeData();
        }
        return users;
    }
    
    public static Map<Integer, Item> getItems() {
        if (!initialized) {
            initializeData();
        }
        return items;
    }
    
    public static User getUser(String phoneNumber) {
        if (!initialized) {
            initializeData();
        }
        return users.get(phoneNumber);
    }
    
    public static User loginUser(String phoneNumber, String password) {
        if (!initialized) {
            initializeData();
        }
        User user = users.get(phoneNumber);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public static boolean registerUser(User user) {
        if (!initialized) {
            initializeData();
        }
        if (users.containsKey(user.getPhoneNumber())) {
            return false;
        }
        users.put(user.getPhoneNumber(), user);
        return true;
    }
} 